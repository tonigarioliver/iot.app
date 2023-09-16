package com.ago.iotapp.web.mqtt;

import com.ago.iotapp.web.entity.Device;
import com.ago.iotapp.web.mqtt.models.TopicObject;
import com.ago.iotapp.web.service.DeviceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class BackgroundMqttService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BackgroundMqttService.class);

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private TopicObject topicObject;
    @Autowired
    private MqttTopicManager topicManager;
    @Autowired
    private MqttClient mqttClient;
    @Autowired
    private MqttConnectOptions mqttConnectOptions;

    private MqttMessageHandler listener;

    @PostConstruct
    public void initialize() {
        try {
            CompletableFuture<List<Device>> devicesFuture = deviceService.getAllAsync();
            CompletableFuture<Void> connectionFuture = mqttConnectAsync();

            connectionFuture.thenAccept(result -> {
                List<Device> devices = devicesFuture.join();
                if (!devices.isEmpty()) {
                    List<String> topics = devices.stream()
                            .map(device -> {
                                try {
                                    return topicObject.objectAsTopic(device);
                                } catch (JsonProcessingException e) {
                                    LOGGER.error("Error al convertir objeto en JSON: " + e.getMessage(), e);
                                    return null;
                                }
                            })
                            .filter(topic -> topic != null) // Filtra los temas nulos
                            .collect(Collectors.toList());

                    topics.forEach(this::addSubscription);
                }
                addSubscription("pipo");
            }).exceptionally(exception -> {
                LOGGER.error("Error en la conexión MQTT: " + exception.getMessage(), exception);
                return null;
            });
        } catch (Exception e) {
            LOGGER.error("Error en la inicialización: " + e.getMessage(), e);
        }
    }

    @Async
    public CompletableFuture<Void> mqttConnectAsync() {
        CompletableFuture<Void> future = new CompletableFuture<>();
        try {
            listener = new MqttMessageHandler();
            mqttClient.connect(mqttConnectOptions);
            future.complete(null); // Completa el CompletableFuture cuando la conexión tiene éxito
        } catch (MqttException e) {
            LOGGER.error("Error en la conexión MQTT: " + e.getMessage(), e);
            future.completeExceptionally(e); // Completa el CompletableFuture con una excepción si falla la conexión
        }
        return future;
    }

    // Define a message handler to process MQTT messages
    private class MqttMessageHandler implements IMqttMessageListener {
        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            // Process the MQTT message and save it to the database
            String messagePayload = new String(message.getPayload());
            LOGGER.info(messagePayload);
            saveToDatabase(topic, messagePayload);
        }
    }

    private void saveToDatabase(String topic, String message) {
        // Implement the logic to save data to the database
    }

    public synchronized void removeSubscription(String topic) throws MqttException {
        topicManager.remove(topic);
        mqttClient.unsubscribe(topic);
    }

    public synchronized void addSubscription(String topic) {
        LOGGER.info("New topic added: " + topic);
        topicManager.addTopic(topic);
        try {
            mqttClient.subscribe(topic, listener);
        } catch (MqttException e) {
            LOGGER.error("Error to subscribe new topic:" + topic + "error: " + e.getMessage(), e);
        }
    }
}

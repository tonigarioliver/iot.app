package com.ago.iotapp.websocket.service;

import com.ago.iotapp.web.mqtt.MqttTopicManager;
import com.ago.iotapp.web.mqtt.event.NewMqttMessageReceivedEvent;
import com.ago.iotapp.web.mqtt.models.DeviceTopic;
import com.ago.iotapp.web.mqtt.models.MappingJSON;
import com.ago.iotapp.web.service.IDeviceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class MqttListenerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MqttListenerService.class);
    @Autowired
    private MappingJSON mappingJSON;
    @Autowired
    private WebSocketTopicManager webSocketTopicManager;
    private MqttClient mqttClient;
    private MqttConnectOptions mqttConnectOptions;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private MqttMessageHandler listener;
    //@Autowired
    //private SimpMessagingTemplate messagingTemplate;
    @Autowired
    public MqttListenerService(@Qualifier("mqttClientListener") MqttClient mqttClientListener,
                                 @Qualifier("mqttClientListenerOptions") MqttConnectOptions mqttClientListenerOptions) {
        this.mqttClient = mqttClientListener;
        this.mqttConnectOptions=mqttClientListenerOptions;
    }
    @PostConstruct
    public void initialize() {
            CompletableFuture<Void> connectionFuture = mqttConnectAsync();
            connectionFuture.join();
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
            sendToSubscriber(topic, messagePayload);
        }
    }

    private void sendToSubscriber(String topic, String message) throws IOException {
        if(webSocketTopicManager.isTopicSubscribed(topic))
        {
            WebSocketSession session= webSocketTopicManager.getSessionameByTopic(topic);
            session.sendMessage(new TextMessage("Received " + message + " !"));
        }
    }

    public synchronized void removeSubscription(String topic) throws MqttException {
        if(webSocketTopicManager.isTopicSubscribed(topic))
        {
            mqttClient.unsubscribe(topic);
            webSocketTopicManager.deleteTopic(topic);
        }
        mqttClient.unsubscribe(topic);
    }

    public synchronized void addSubscription(WebSocketSession session,String topic) {
        LOGGER.info("New topic added: " + topic);
        webSocketTopicManager.addTopic(topic,session);
        try {
            mqttClient.subscribe(topic, listener);
        } catch (MqttException e) {
            LOGGER.error("Error to subscribe new topic:" + topic + "error: " + e.getMessage(), e);
        }
    }
}

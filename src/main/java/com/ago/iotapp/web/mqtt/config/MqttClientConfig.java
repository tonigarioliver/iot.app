package com.ago.iotapp.web.mqtt.config;

import com.ago.iotapp.web.mqtt.models.MappingJSON;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class MqttClientConfig {
    @Value("${mqtt.server.ip}")
    private String serverIp;
    @Value("${mqtt.server.port}")
    private String serverPort;


    @Bean(name = "mqttClientBackground")
    public MqttClient mqttClientBackground() throws Exception {
        String clientId = UUID.randomUUID().toString();
        String serverUrl = serverIp + ":" + serverPort;
        MqttClient mqttClient = new MqttClient(serverUrl, clientId, new MemoryPersistence());
        return mqttClient;
    }
    @Bean(name = "mqttClientListener")
    public MqttClient mqttClientListener() throws Exception {
        String clientId = UUID.randomUUID().toString();
        String serverUrl = serverIp + ":" + serverPort;
        MqttClient mqttClient = new MqttClient(serverUrl, clientId, new MemoryPersistence());
        return mqttClient;
    }
    @Bean(name="mqttClientBackgroundOptions")
    public MqttConnectOptions mqttClientBackgroundOptions() throws Exception {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        return options;
    }
    @Bean(name="mqttClientListenerOptions")
    public MqttConnectOptions mqttClientListenerOptions() throws Exception {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        return options;
    }
    @Bean
    public MappingJSON mappingJSON(){
        return new MappingJSON();
    }

}

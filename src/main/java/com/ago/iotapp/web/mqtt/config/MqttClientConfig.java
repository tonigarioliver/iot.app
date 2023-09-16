package com.ago.iotapp.web.mqtt.config;

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

    @Bean
    public MqttClient mqttClient() throws Exception {
        String clientId = UUID.randomUUID().toString();
        String serverUrl = serverIp + ":" + serverPort;
        MqttClient mqttClient = new MqttClient(serverUrl, clientId, new MemoryPersistence());
        return mqttClient;
    }
    @Bean
    public MqttConnectOptions mqttConnectOptions() throws Exception {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        return options;
    }

}

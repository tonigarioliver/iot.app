package com.ago.iotapp.websocket.event.listener;

import com.ago.iotapp.web.mqtt.BackgroundMqttService;
import com.ago.iotapp.web.mqtt.event.NewDeviceAddEvent;
import com.ago.iotapp.web.mqtt.models.MappingJSON;
import com.ago.iotapp.websocket.event.NewDeviceSubscriptionEvent;
import com.ago.iotapp.websocket.service.MqttListenerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class NewDeviceSubscriptionListener implements ApplicationListener<NewDeviceSubscriptionEvent> {
    @Autowired
    private MqttListenerService mqttListenerService;
    @Autowired
    private MappingJSON mappingJSON;
    @Override
    public void onApplicationEvent(NewDeviceSubscriptionEvent event) {
        try {
            mqttListenerService.addSubscription(event.getSession(), mappingJSON.objectAsJSON(event.getDeviceTopic()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
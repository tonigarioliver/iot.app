package com.ago.iotapp.web.mqtt.event.listener;

import com.ago.iotapp.web.mqtt.BackgroundMqttService;
import com.ago.iotapp.web.mqtt.event.NewDeviceAddEvent;
import com.ago.iotapp.web.mqtt.models.MappingJSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class NewDeviceAddEventListener implements ApplicationListener<NewDeviceAddEvent> {
    @Autowired
    private BackgroundMqttService backgroundMqttService;
    @Autowired
    private MappingJSON mappingJSON;
    @Override
    public void onApplicationEvent(NewDeviceAddEvent event) {
        try {
            backgroundMqttService.addSubscription(mappingJSON.objectAsJSON(event.getDeviceTopic()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

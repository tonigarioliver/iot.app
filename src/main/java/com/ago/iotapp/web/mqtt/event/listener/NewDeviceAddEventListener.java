package com.ago.iotapp.web.mqtt.event.listener;

import com.ago.iotapp.web.mqtt.BackgroundMqttService;
import com.ago.iotapp.web.mqtt.event.NewDeviceAddEvent;
import com.ago.iotapp.web.mqtt.models.TopicObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class NewDeviceAddEventListener implements ApplicationListener<NewDeviceAddEvent> {
    @Autowired
    private BackgroundMqttService backgroundMqttService;
    @Autowired
    private TopicObject topicObject;
    @Override
    public void onApplicationEvent(NewDeviceAddEvent event) {
        try {
            backgroundMqttService.addSubscription(topicObject.objectAsTopic(event.getDeviceTopic()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.ago.iotapp.web.mqtt.event;

import com.ago.iotapp.web.mqtt.models.DeviceTopic;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

@Getter
public class NewDeviceAddEvent extends ApplicationEvent {
    private DeviceTopic deviceTopic;
    public NewDeviceAddEvent(Object source, DeviceTopic deviceTopic) {
        super(source);
        this.deviceTopic=deviceTopic;
    }
}

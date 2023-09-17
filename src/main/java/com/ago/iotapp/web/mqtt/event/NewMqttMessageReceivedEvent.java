package com.ago.iotapp.web.mqtt.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class NewMqttMessageReceivedEvent extends ApplicationEvent {
    private String topic;
    private String payload;

    public NewMqttMessageReceivedEvent(Object source, String topic, String payload) {
        super(source);
        this.topic=topic;
        this.payload=payload;
    }
}

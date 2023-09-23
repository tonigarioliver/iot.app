package com.ago.iotapp.websocket.event;

import com.ago.iotapp.web.mqtt.models.DeviceTopic;
import com.ago.iotapp.websocket.models.WebSocketHandlerMessage;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.socket.WebSocketSession;

@Getter
public class NewDeviceSubscriptionEvent extends ApplicationEvent {
    private DeviceTopic deviceTopic;
    private WebSocketSession session;
    public NewDeviceSubscriptionEvent(Object source, DeviceTopic deviceTopic, WebSocketSession session) {
        super(source);
        this.deviceTopic=deviceTopic;
        this.session=session;
    }
}
package com.ago.iotapp.websocket.handler;

import com.ago.iotapp.Application;
import com.ago.iotapp.web.mqtt.models.DeviceTopic;
import com.ago.iotapp.web.mqtt.models.MappingJSON;
import com.ago.iotapp.websocket.event.NewDeviceSubscriptionEvent;
import com.ago.iotapp.websocket.models.WebSocketHandlerMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
@Component
public class SocketHandler extends TextWebSocketHandler {
    @Autowired
    private MappingJSON mapper;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    List<WebSocketSession> webSocketSessions = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        webSocketSessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        webSocketSessions.remove(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        super.handleMessage(session, message);
        System.out.println(message.getPayload().toString());
        WebSocketHandlerMessage webSocketHandlerMessage=mapper.JSONAsObject(message.getPayload().toString(), WebSocketHandlerMessage.class);
        handleEventMessage(webSocketHandlerMessage,session);
        for (WebSocketSession webSocketSession : webSocketSessions) {
            webSocketSession.sendMessage(message);
        }
    }

    private void handleEventMessage(WebSocketHandlerMessage webSocketHandlerMessage,
                                    WebSocketSession session) throws JsonProcessingException {
        if(webSocketHandlerMessage.getCommand().equals("subscribe"))
        {
            eventPublisher.publishEvent(new NewDeviceSubscriptionEvent(this,
                    mapper.JSONAsObject(webSocketHandlerMessage.getPayload(),DeviceTopic.class),session));
        }
    }

}
package com.ago.iotapp.websocket.models;

import lombok.Getter;
@Getter
public class WebSocketHandlerMessage {
    private String command;
    private String payload;
}

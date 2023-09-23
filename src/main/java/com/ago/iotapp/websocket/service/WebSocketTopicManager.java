package com.ago.iotapp.websocket.service;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;
@Component
public class WebSocketTopicManager {

    private  final ConcurrentHashMap<String, WebSocketSession> topicToUsernameMap = new ConcurrentHashMap<>();

    public  void addTopic(String topic,WebSocketSession session) {
        topicToUsernameMap.put(topic,session);
    }

    public  boolean isTopicSubscribed(String topic) {
        return topicToUsernameMap.containsKey(topic);
    }

    public  WebSocketSession getSessionameByTopic(String topic) {
        return topicToUsernameMap.get(topic);
    }
    public void deleteTopic(String topic){
        topicToUsernameMap.remove(topic);
    }
}


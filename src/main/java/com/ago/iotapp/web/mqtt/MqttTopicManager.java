package com.ago.iotapp.web.mqtt;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class MqttTopicManager {

    private final Set<String> subscribedTopics = new HashSet<>();

    public synchronized void addTopic(String topic) {
        subscribedTopics.add(topic);
        // Subscribe to the new topic here using your MQTT client
    }

    public synchronized Set<String> getSubscribedTopics() {
        return new HashSet<>(subscribedTopics);
    }
    public synchronized void remove(String topic)
    {
        if(subscribedTopics.contains(topic)) {
            subscribedTopics.remove(topic);
        }
    }
}

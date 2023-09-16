package com.ago.iotapp.web.mqtt.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class TopicObject {
    private ObjectMapper objectMapper = new ObjectMapper();
    public <T> String objectAsTopic(T obj) throws JsonProcessingException {
        // Convert the Java object to JSON
        return objectMapper.writeValueAsString(obj);
    }
    public <T> T topicAsObject(String topic, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(topic, clazz);
    }
}

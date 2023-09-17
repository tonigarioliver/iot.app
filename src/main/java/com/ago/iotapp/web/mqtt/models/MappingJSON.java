package com.ago.iotapp.web.mqtt.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class MappingJSON {
    private ObjectMapper objectMapper = new ObjectMapper();
    public <T> String objectAsJSON(T obj) throws JsonProcessingException {
        // Convert the Java object to JSON
        return objectMapper.writeValueAsString(obj);
    }
    public <T> T JSONAsObject(String topic, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(topic, clazz);
    }
}

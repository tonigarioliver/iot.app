package com.ago.iotapp.web.controller;

import com.ago.iotapp.web.dto.AddDeviceRequestDto;
import com.ago.iotapp.web.entity.Device;
import com.ago.iotapp.web.mqtt.BackgroundMqttService;
import com.ago.iotapp.web.mqtt.models.TopicObject;
import com.ago.iotapp.web.service.DeviceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class TestController {
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private BackgroundMqttService backgroundMqttService;
    @Autowired
    private TopicObject topicObject;
    @Autowired
    private ModelMapper mapper;
    @PostMapping("api/test/mqtt")
    public ResponseEntity<Device>addDevice(@RequestBody AddDeviceRequestDto request)
    {
        Device device=mapper.map(request,Device.class);
        deviceService.saveDevice(device);

        try {
            backgroundMqttService.addSubscription(topicObject.objectAsTopic(device));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(device);
    }
}

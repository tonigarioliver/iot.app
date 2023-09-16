package com.ago.iotapp.web.controller;

import com.ago.iotapp.web.dto.AddDeviceRequestDto;
import com.ago.iotapp.web.model.DeviceModel;
import com.ago.iotapp.web.mqtt.event.NewDeviceAddEvent;
import com.ago.iotapp.web.mqtt.models.DeviceTopic;
import com.ago.iotapp.web.mqtt.models.TopicObject;
import com.ago.iotapp.web.service.IDeviceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class TestController {
    @Autowired
    private IDeviceService deviceService;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private TopicObject topicObject;
    @Autowired
    private ModelMapper mapper;
    @PostMapping("api/test/mqtt")
    public ResponseEntity<DeviceModel>addDevice(@RequestBody AddDeviceRequestDto request)
    {
        System.out.println(request.toString());
        DeviceModel device=mapper.map(request, DeviceModel.class);
        device.setEnabled(true);//to be removed
        deviceService.saveDevice(device);
        if(device.isEnabled())
        {
            publisher.publishEvent(new NewDeviceAddEvent(this,mapper.map(device, DeviceTopic.class)));
        }
        return ResponseEntity.ok(device);
    }
}

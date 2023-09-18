package com.ago.iotapp.web.controller;

import com.ago.iotapp.web.dto.AddDeviceRequestDto;
import com.ago.iotapp.web.model.DeviceModel;
import com.ago.iotapp.web.mqtt.event.NewDeviceAddEvent;
import com.ago.iotapp.web.mqtt.models.DeviceTopic;
import com.ago.iotapp.web.service.IDeviceService;
import com.ago.iotapp.web.service.exception.ItemAlreadyExists;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    private IDeviceService deviceService;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private ModelMapper mapper;
    @PostMapping("/mqtt")
    public ResponseEntity<DeviceModel>addDevice(@RequestBody AddDeviceRequestDto request) throws ItemAlreadyExists {
        DeviceModel device=mapper.map(request, DeviceModel.class);
        device.setEnabled(true);//to be removed
        deviceService.saveNewDevice(device);
        if(device.isEnabled())
        {
            publisher.publishEvent(new NewDeviceAddEvent(this,mapper.map(device, DeviceTopic.class)));
        }
        return ResponseEntity.ok(device);
    }
}

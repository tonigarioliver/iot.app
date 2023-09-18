package com.ago.iotapp.web.controller;

import com.ago.iotapp.web.dto.AddDeviceRequestDto;
import com.ago.iotapp.web.dto.EnableDeviceDto;
import com.ago.iotapp.web.model.DeviceModel;
import com.ago.iotapp.web.mqtt.event.NewDeviceAddEvent;
import com.ago.iotapp.web.mqtt.models.DeviceTopic;
import com.ago.iotapp.web.service.IDeviceService;
import com.ago.iotapp.web.service.exception.ItemAlreadyExists;
import com.ago.iotapp.web.service.exception.ItemNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    private IDeviceService deviceService;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private ModelMapper mapper;
    @ApiResponses(value = { @ApiResponse(code = 409, message = "Item already exists"),
            @ApiResponse(code = 204, message = "Item Successfully Added") })
    @PostMapping("/mqtt")
    public ResponseEntity<DeviceModel>addDevice(@Valid @RequestBody AddDeviceRequestDto request) throws ItemAlreadyExists {
        DeviceModel device=mapper.map(request, DeviceModel.class);
        deviceService.saveNewDevice(device);
        if(device.isEnabled())
        {
            publisher.publishEvent(new NewDeviceAddEvent(this,mapper.map(device, DeviceTopic.class)));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(device);
    }
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Item Successfully Updated"),
            @ApiResponse(code = 404, message = "Item Not Found") // Add a response for ItemNotFoundException
    })
    @PutMapping("/mqtt/enableDevice")
    public ResponseEntity<Void>enableDevice(@Valid @RequestBody EnableDeviceDto request) throws ItemNotFoundException {
        DeviceModel device=deviceService.enableDevice(request);
        publisher.publishEvent(new NewDeviceAddEvent(this,mapper.map(device, DeviceTopic.class)));
        return ResponseEntity.noContent().build();
    }
}

package com.ago.iotapp.web.service.impl;

import com.ago.iotapp.web.entity.Device;
import com.ago.iotapp.web.model.DeviceModel;
import com.ago.iotapp.web.mqtt.models.DeviceTopic;
import com.ago.iotapp.web.repository.DeviceRepository;
import com.ago.iotapp.web.service.IDeviceService;
import com.ago.iotapp.web.service.exception.ItemAlreadyExists;
import com.ago.iotapp.web.service.exception.ItemNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class DeviceService implements IDeviceService {
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private ModelMapper mapper;

    @Async
    @Override
    public CompletableFuture<List<Device>> getAllAsync() {
        return CompletableFuture.completedFuture(deviceRepository.findAll());
    }

    @Async
    @Override
    public CompletableFuture<List<DeviceTopic>> getAllAsyncForTopic() {
        List<Device> devices = deviceRepository.findAll();
        List<DeviceTopic> deviceTopicDtos = devices.stream()
                .map(device -> mapper.map(device, DeviceTopic.class))
                .collect(Collectors.toList());
        return CompletableFuture.completedFuture(deviceTopicDtos);
    }

    @Override
    public void saveNewDevice(DeviceModel device) throws ItemAlreadyExists {
        try{
            deviceRepository.save(mapper.map(device,Device.class));
        }catch (DataIntegrityViolationException e)
        {
            throw new ItemAlreadyExists(e.getCause());
        }

    }

    @Override
    public Device findBySerialNumber(String serialNumber) throws ItemNotFoundException {

        Device device=deviceRepository.findBySerialNumber(serialNumber);
        if(device==null)
        {
            throw new ItemNotFoundException();
        }
        return device;
    }

    @Override
    public void save(Device existingDevice) {
        deviceRepository.save(existingDevice);
    }
}

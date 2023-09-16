package com.ago.iotapp.web.service.impl;

import com.ago.iotapp.web.entity.Device;
import com.ago.iotapp.web.repository.DeviceRepository;
import com.ago.iotapp.web.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;

    @Async
    @Override
    public CompletableFuture<List<Device>> getAllAsync() {
        return CompletableFuture.supplyAsync(() -> new ArrayList<Device>());
    }

    @Override
    public void saveDevice(Device device) {
        deviceRepository.save(device);
    }
}

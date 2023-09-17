package com.ago.iotapp.web.service;

import com.ago.iotapp.web.entity.Device;
import com.ago.iotapp.web.model.DeviceModel;
import com.ago.iotapp.web.mqtt.models.DeviceTopic;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;
public interface IDeviceService {
    @Async
    CompletableFuture<List<Device>> getAllAsync();
    @Async
    CompletableFuture<List<DeviceTopic>> getAllAsyncForTopic();
    void saveDevice(DeviceModel device);
    Device findBySerialNumber(String serialNumber);

    void save(Device existingDevice);
}

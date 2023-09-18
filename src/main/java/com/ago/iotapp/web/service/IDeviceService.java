package com.ago.iotapp.web.service;

import com.ago.iotapp.web.entity.Device;
import com.ago.iotapp.web.model.DeviceModel;
import com.ago.iotapp.web.mqtt.models.DeviceTopic;
import com.ago.iotapp.web.service.exception.ItemAlreadyExists;
import com.ago.iotapp.web.service.exception.ItemNotFoundException;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;
public interface IDeviceService {
    @Async
    CompletableFuture<List<Device>> getAllAsync();
    @Async
    CompletableFuture<List<DeviceTopic>> getAllAsyncForTopic();
    void saveNewDevice(DeviceModel device) throws ItemAlreadyExists;
    Device findBySerialNumber(String serialNumber) throws ItemNotFoundException;

    void save(Device existingDevice);
}

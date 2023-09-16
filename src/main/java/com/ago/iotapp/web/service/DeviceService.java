package com.ago.iotapp.web.service;

import com.ago.iotapp.web.entity.Device;
import org.springframework.scheduling.annotation.Async;
import java.util.List;
import java.util.concurrent.CompletableFuture;
public interface DeviceService {
    @Async
    CompletableFuture<List<Device>> getAllAsync();

    void saveDevice(Device device);
}

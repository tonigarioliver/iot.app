package com.ago.iotapp.web.repository;

import com.ago.iotapp.web.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device,Long> {
    @Async
    List<Device> findAll();
}

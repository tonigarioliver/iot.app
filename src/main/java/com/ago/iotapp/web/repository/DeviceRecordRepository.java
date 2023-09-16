package com.ago.iotapp.web.repository;

import com.ago.iotapp.web.entity.DeviceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRecordRepository extends JpaRepository<DeviceRecord,Long> {
}

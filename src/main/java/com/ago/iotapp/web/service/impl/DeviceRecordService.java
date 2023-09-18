package com.ago.iotapp.web.service.impl;

import com.ago.iotapp.web.entity.Device;
import com.ago.iotapp.web.entity.DeviceRecord;
import com.ago.iotapp.web.mqtt.models.DeviceRecordData;
import com.ago.iotapp.web.repository.DeviceRecordRepository;
import com.ago.iotapp.web.service.IDeviceRecordService;
import com.ago.iotapp.web.service.IDeviceService;
import com.ago.iotapp.web.service.exception.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DeviceRecordService implements IDeviceRecordService {
    @Autowired
    private DeviceRecordRepository deviceRecordRepository;
    @Autowired
    private IDeviceService deviceService;
    @Transactional
    public void saveRecordForDevice(String deviceSerialNumber, DeviceRecordData recordData) {
        // Step 1: Retrieve the existing Device from the database
        try {
            Device existingDevice = deviceService.findBySerialNumber(deviceSerialNumber);
            // Step 2: Create a new Record instance and set its properties
            DeviceRecord newRecord = new DeviceRecord();
            newRecord.setLastRecord(recordData.getLastRecord());

            // Step 3: Associate the Device with the Record
            newRecord.setDevice(existingDevice);

            // Step 4: Save the Record to the database
            deviceRecordRepository.save(newRecord);

            // Optionally, you can update the Device's records list
            existingDevice.addRecord(newRecord);
            deviceService.save(existingDevice);
        } catch (ItemNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

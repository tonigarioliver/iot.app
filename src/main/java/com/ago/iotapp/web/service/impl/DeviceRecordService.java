package com.ago.iotapp.web.service.impl;

import com.ago.iotapp.web.entity.Device;
import com.ago.iotapp.web.entity.DeviceRecord;
import com.ago.iotapp.web.mqtt.models.DeviceRecordData;
import com.ago.iotapp.web.repository.DeviceRecordRepository;
import com.ago.iotapp.web.service.IDeviceRecordService;
import com.ago.iotapp.web.service.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DeviceRecordService implements IDeviceRecordService {
    @Autowired
    private DeviceRecordRepository deviceRecordRepository;
    @Autowired
    private IDeviceService deviceService;
    public void saveRecordForDevice(String deviceSerialNumber, DeviceRecordData recordData) {
        // Step 1: Retrieve the existing Device from the database
        Device existingDevice = deviceService.findBySerialNumber(deviceSerialNumber);
        /*
        if (existingDevice == null) {
            // Handle the case where the Device with the given serialNumber doesn't exist
            throw new EntityNotFoundException("Device not found");
        }*/

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
    }
}

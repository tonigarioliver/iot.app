package com.ago.iotapp.web.service;

import com.ago.iotapp.web.mqtt.models.DeviceRecordData;

public interface IDeviceRecordService {
    void saveRecordForDevice(String deviceSerialNumber, DeviceRecordData recordData);
}

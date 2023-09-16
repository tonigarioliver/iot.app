package com.ago.iotapp.web.mqtt.models;

import com.ago.iotapp.web.entity.DeviceDataType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceTopic {
    private String name;
    private String serialNumber;
    public DeviceDataType dataType;
}

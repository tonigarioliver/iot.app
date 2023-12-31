package com.ago.iotapp.web.model;

import com.ago.iotapp.web.entity.DeviceDataType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceModel {

    private String name;
    private String serialNumber;

    public DeviceDataType dataType;
    private boolean enabled=false;
}

package com.ago.iotapp.web.dto;

import com.ago.iotapp.web.entity.DeviceDataType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddDeviceRequestDto {
    private String name;
    private String serialNumber;
    @Enumerated(EnumType.STRING)
    public DeviceDataType dataType;
}

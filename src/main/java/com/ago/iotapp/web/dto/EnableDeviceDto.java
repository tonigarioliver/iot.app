package com.ago.iotapp.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnableDeviceDto {
    private String serialNumber;
    private boolean toEnable;
}

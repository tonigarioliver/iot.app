package com.ago.iotapp.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "Devices",
        uniqueConstraints = @UniqueConstraint(
                name = "serial_number_unique",
                columnNames = "serial_number"
        )
)
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviceId;
    private String name;
    @Column(name = "serial_number",
    unique = true)
    private String serialNumber;
    @Enumerated(EnumType.STRING)
    public DeviceDataType dataType;
    private boolean enabled=false;
    @OneToMany(mappedBy = "device")
    List<DeviceRecord> deviceRecords;
    public void addRecord(DeviceRecord record){
        if(deviceRecords == null) deviceRecords = new ArrayList<>();
        deviceRecords.add(record);
    }
}

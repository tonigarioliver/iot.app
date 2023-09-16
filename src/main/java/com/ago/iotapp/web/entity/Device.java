package com.ago.iotapp.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    @Column(name = "serial_number")
    private String serialNumber;
    @Enumerated(EnumType.STRING)
    public DeviceDataType dataType;
    private boolean enabled=false;
    @OneToMany(mappedBy = "device")
    List<DeviceRecord> records;
}

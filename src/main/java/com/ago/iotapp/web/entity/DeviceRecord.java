package com.ago.iotapp.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DeviceRecord")
public class DeviceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long recordId;
    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name="device_id",
            referencedColumnName = "deviceId"
    )
    public Device device;
}

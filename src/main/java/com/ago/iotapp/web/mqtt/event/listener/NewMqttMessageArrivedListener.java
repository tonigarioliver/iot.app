package com.ago.iotapp.web.mqtt.event.listener;

import com.ago.iotapp.web.mqtt.event.NewMqttMessageReceivedEvent;
import com.ago.iotapp.web.mqtt.models.DeviceRecordData;
import com.ago.iotapp.web.mqtt.models.DeviceTopic;
import com.ago.iotapp.web.mqtt.models.MappingJSON;
import com.ago.iotapp.web.service.IDeviceRecordService;
import com.ago.iotapp.web.service.IDeviceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class NewMqttMessageArrivedListener implements
        ApplicationListener<NewMqttMessageReceivedEvent> {
    @Autowired
    private MappingJSON mappingJSON;
    @Autowired
    private IDeviceService deviceService;
    @Autowired
    private IDeviceRecordService deviceRecordService;

    @Override
    public void onApplicationEvent(NewMqttMessageReceivedEvent event) {
        try {
            DeviceTopic device=mappingJSON.JSONAsObject(event.getTopic(), DeviceTopic.class);
            DeviceRecordData payload=mappingJSON.JSONAsObject(event.getPayload(), DeviceRecordData.class);
            deviceRecordService.saveRecordForDevice(device.getSerialNumber(),payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

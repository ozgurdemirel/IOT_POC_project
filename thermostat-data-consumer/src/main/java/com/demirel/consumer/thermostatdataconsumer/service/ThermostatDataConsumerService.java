package com.demirel.consumer.thermostatdataconsumer.service;

import com.demirel.consumer.thermostatdataconsumer.repository.InfluxDBRepository;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.influxdb.dto.Point;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ThermostatDataConsumerService {

    private final IMqttAsyncClient client;
    private final InfluxDBRepository influxDBRepository;

    @Value("${mqtt.topic:''}")
    private String topic;

    @Value("${mqtt.measurement:''}")
    private String influxDBMeasurement;

    public void subscribeChannel() throws MqttException {
        if (!client.isConnected()) {
            return;
        }

        IMqttMessageListener iMqttMessageListener = (topic, msg) -> {
            byte[] payload = msg.getPayload();
            saveTemperatureData(new JSONObject(new String(payload)));
        };

        client.subscribe(topic, 0, iMqttMessageListener);
    }

    void saveTemperatureData(JSONObject jsonObject) {
        Point point = Point.measurement(influxDBMeasurement)
                .time(Long.valueOf(jsonObject.get("time").toString()), TimeUnit.MILLISECONDS)
                .addField("temperature", Double.valueOf(jsonObject.get("temperature").toString()))
                .addField("sensorName", jsonObject.get("sensorName").toString())
                .build();
        influxDBRepository.save(point);
    }

}

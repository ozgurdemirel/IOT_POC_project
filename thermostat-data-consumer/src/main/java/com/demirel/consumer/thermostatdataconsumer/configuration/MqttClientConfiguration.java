package com.demirel.consumer.thermostatdataconsumer.configuration;

import org.eclipse.paho.client.mqttv3.*;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class MqttClientConfiguration {

    private String MQTT_PUBLISHER_ID = "thermostat-data-consumer";

    @Value("${mqtt.connectionUrl:'tcp://127.0.0.1:1883'}")
    private String MQTT_SERVER_ADDRESS;

    @Bean
    public IMqttAsyncClient client() {
        try {
            IMqttAsyncClient instance = new MqttAsyncClient(MQTT_SERVER_ADDRESS, MQTT_PUBLISHER_ID);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);

            if (!instance.isConnected()) {
                instance.connect(options);
            }
            return instance;
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return null;
    }


}

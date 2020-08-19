package com.demirel.temperature.sensor;

import com.demirel.temperature.ApplicationConstants;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DataPublisherClientConfiguration {
    private static final Logger LOGGER = Logger.getLogger(DataPublisherClientConfiguration.class.getName());

    private static DataPublisherClientConfiguration instance;
    private MqttAsyncClient mqttAsyncClient;
    private ApplicationConstants constants = ApplicationConstants.getInstance();

    private DataPublisherClientConfiguration() {
    }

    public static synchronized DataPublisherClientConfiguration getInstance() {
        if (instance != null) {
            return instance;
        }
        return instance = new DataPublisherClientConfiguration();
    }

    public MqttAsyncClient connect() {
        String publisherId = UUID.randomUUID().toString();
        try {
            mqttAsyncClient = new MqttAsyncClient(constants.getMqttClientConnectionString(), publisherId);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(Boolean.TRUE);
            mqttAsyncClient.connect(options);
        } catch (MqttException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return mqttAsyncClient;
    }

}

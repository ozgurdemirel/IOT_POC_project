package com.demirel.temperature.sensor;

import com.demirel.temperature.ApplicationConstants;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class ThermostatDataManager {

    private static final Logger LOGGER = Logger.getLogger(ThermostatDataManager.class.getName());
    private static ThermostatDataManager instance;
    private ApplicationConstants constants = ApplicationConstants.getInstance();
    private MqttAsyncClient publisher = DataPublisherClientConfiguration.getInstance().connect();

    private ThermostatDataManager() {
    }

    public static synchronized ThermostatDataManager getInstance() {
        if (instance != null) {
            return instance;
        }
        return instance = new ThermostatDataManager();
    }

    public void publishTemperature() {
        if (publisher.isConnected()) {
            String jsonString = new JSONObject()
                    .put("time", System.currentTimeMillis())
                    .put("temperature", sensorValue())
                    .put("sensorName", constants.getApplicationId())
                    .toString();
            byte[] payload = jsonString.getBytes();
            MqttMessage mqttMessage = new MqttMessage(payload);
            LOGGER.info(String.format("message published : %s", jsonString));
            try {
                publisher.publish(constants.getPublisherTopicName(), mqttMessage);
            } catch (MqttException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    /***
     * its a fake implementation for sensor data
     * @return a double value  between 0 to 40
     */
    private double sensorValue() {
        // NOTE : faking the thermostat data ... it might be refactored as interface
        return Math.floor(40.0 * Math.random());
    }

}

package com.demirel.temperature;

import com.demirel.temperature.sensor.ThermostatDataManager;

import java.util.Timer;
import java.util.TimerTask;

public class Application {

    private ThermostatDataManager thermostatDataManager = ThermostatDataManager.getInstance();
    private ApplicationConstants constants = ApplicationConstants.getInstance();
    private Timer timer = new Timer(constants.getTimerName());

    void collectData() {
        TimerTask temperaturePublisher = new TimerTask() {
            @Override
            public void run() {
                thermostatDataManager.publishTemperature();
            }
        };
        long period = constants.getTimerPeriod();
        timer.schedule(temperaturePublisher, constants.getTimerDelay(), period);
    }

    public static void main(String[] args) {
        Application application = new Application();
        application.collectData();
    }

}

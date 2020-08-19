package com.demirel.temperature;

import java.util.UUID;

public final class ApplicationConstants {

    private static ApplicationConstants instance;

    private String applicationId = UUID.randomUUID().toString();
    private String timerName = "thermostat";
    private long timerDelay = 0;
    private long timerPeriod = 1000;
    private String publisherTopicName = "defaultTopicName";
    private String mqttClientConnectionString = "tcp://127.0.0.1:1883";

    private ApplicationConstants() {
    }

    public static synchronized ApplicationConstants getInstance() {
        if (instance != null) {
            return instance;
        }
        return instance = new ApplicationConstants();
    }

    public String getApplicationId() {
        String applicationId = env("applicationId");
        if (!applicationId.isEmpty()) {
            return applicationId;
        }
        return this.applicationId;
    }

    public String getTimerName() {
        String timerName = env("timerName");
        if (!timerName.isEmpty()) {
            return timerName;
        }
        return this.timerName;
    }

    public long getTimerDelay() {
        String timerDelay = env("timerDelay");
        if (!timerDelay.isEmpty()) {
            return Long.valueOf(timerDelay);
        }
        return this.timerDelay;
    }

    public long getTimerPeriod() {
        String period = env("timerPeriod");
        if (!period.isEmpty()) {
            return Long.valueOf(period);
        }
        return this.timerPeriod;
    }

    public String getPublisherTopicName() {
        String publisherTopicName = env("publisherTopicName");
        if (!publisherTopicName.isEmpty()) {
            return publisherTopicName;
        }
        return this.publisherTopicName;
    }

    public String getMqttClientConnectionString() {
        String mqttClientConnectionString = env("mqttClientConnectionString");
        if (!mqttClientConnectionString.isEmpty()) {
            return mqttClientConnectionString;
        }
        return this.mqttClientConnectionString;
    }

    private static String env(String name) {
        String env = System.getenv(name);
        if (env != null && !env.isEmpty()) {
            return env;
        }
        return "";
    }

}

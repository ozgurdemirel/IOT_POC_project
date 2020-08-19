package com.demirel.consumer.thermostatdataconsumer;

import com.demirel.consumer.thermostatdataconsumer.service.ThermostatDataConsumerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ThermostatDataConsumerApplication implements CommandLineRunner {

    private final ThermostatDataConsumerService thermostatDataConsumerService;

    public ThermostatDataConsumerApplication(ThermostatDataConsumerService thermostatDataConsumerService) {
        this.thermostatDataConsumerService = thermostatDataConsumerService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ThermostatDataConsumerApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        thermostatDataConsumerService.subscribeChannel();
    }
}

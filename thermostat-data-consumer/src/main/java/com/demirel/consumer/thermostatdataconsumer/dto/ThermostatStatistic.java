package com.demirel.consumer.thermostatdataconsumer.dto;

import lombok.Getter;
import lombok.Setter;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;

@Measurement(name = "thermostat")
@Getter
@Setter
public class ThermostatStatistic {

    @Column(name = "time")
    private Instant time;

    @Column(name = "value")
    private double value;
    
}

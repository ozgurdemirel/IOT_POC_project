package com.demirel.consumer.thermostatdataconsumer.service;

import com.demirel.consumer.thermostatdataconsumer.dto.ThermostatStatistic;
import com.demirel.consumer.thermostatdataconsumer.repository.InfluxDBRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThermostatDataQueryService {

    private final InfluxDBRepository influxDBRepository;

    public ThermostatStatistic averageTemperature(List<String> sensorNames, String timePeriod) {
        String sensorNamePipes = String.join("|", sensorNames);
        List<ThermostatStatistic> average = influxDBRepository.average(sensorNamePipes, timePeriod);
        return average.isEmpty() ? new ThermostatStatistic() : average.get(0);
    }

    public ThermostatStatistic medianTemperature(List<String> sensorNames, String timePeriod) {
        String sensorNamePipes = String.join("|", sensorNames);
        List<ThermostatStatistic> median = influxDBRepository.median(sensorNamePipes, timePeriod);
        return median.isEmpty() ? new ThermostatStatistic() : median.get(0);
    }

    public ThermostatStatistic maxTemperature(List<String> sensorNames, String timePeriod) {
        String sensorNamePipes = String.join("|", sensorNames);
        List<ThermostatStatistic> max = influxDBRepository.max(sensorNamePipes, timePeriod);
        return max.isEmpty() ? new ThermostatStatistic() : max.get(0);
    }

    public ThermostatStatistic minTemperature(List<String> sensorNames, String timePeriod) {
        String sensorNamePipes = String.join("|", sensorNames);
        List<ThermostatStatistic> min = influxDBRepository.min(sensorNamePipes, timePeriod);
        return min.isEmpty() ? new ThermostatStatistic() : min.get(0);
    }


}

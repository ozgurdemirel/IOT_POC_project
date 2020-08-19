package com.demirel.consumer.thermostatdataconsumer.service;

import com.demirel.consumer.thermostatdataconsumer.dto.ThermostatStatistic;
import com.demirel.consumer.thermostatdataconsumer.repository.InfluxDBRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ThermostatDataQueryServiceTest {

    @InjectMocks
    private ThermostatDataQueryService thermostatDataQueryService;

    @Mock
    private InfluxDBRepository influxDBRepository;

    @Captor
    private ArgumentCaptor<String> sensorsCaptor;

    @Captor
    private ArgumentCaptor<String> timePeriodCaptor;

    @Test
    public void averageTemperature() {
        String sensors = "1,2,3";
        String timePeriod = "1d";
        ThermostatStatistic thermostatStatistic1 = thermostatStatistic(10.0);
        when(influxDBRepository.average(any(), any()))
                .thenReturn(Arrays.asList(thermostatStatistic1));
        ThermostatStatistic thermostatStatistic = thermostatDataQueryService.averageTemperature(Arrays.asList(sensors.split(",")), timePeriod);

        verify(influxDBRepository, times(1))
                .average(sensorsCaptor.capture(), timePeriodCaptor.capture());
        assertThat(sensorsCaptor.getValue()).isEqualTo(sensors.replaceAll(",", "|"));
        assertThat(thermostatStatistic.getValue()).isEqualTo(thermostatStatistic1.getValue());
    }

    @Test
    public void minTemperature() {
        String sensors = "1,2,3";
        String timePeriod = "1d";
        ThermostatStatistic thermostatStatistic1 = thermostatStatistic(10.0);
        when(influxDBRepository.min(any(), any()))
                .thenReturn(Arrays.asList(thermostatStatistic1));
        ThermostatStatistic thermostatStatistic = thermostatDataQueryService.minTemperature(Arrays.asList(sensors.split(",")), timePeriod);

        verify(influxDBRepository, times(1))
                .min(sensorsCaptor.capture(), timePeriodCaptor.capture());
        assertThat(sensorsCaptor.getValue()).isEqualTo(sensors.replaceAll(",", "|"));
        assertThat(thermostatStatistic.getValue()).isEqualTo(thermostatStatistic1.getValue());
    }

    @Test
    public void maxTemperature() {
        String sensors = "1,2,3";
        String timePeriod = "1d";
        ThermostatStatistic thermostatStatistic1 = thermostatStatistic(10.0);
        when(influxDBRepository.max(any(), any()))
                .thenReturn(Arrays.asList(thermostatStatistic1));
        ThermostatStatistic thermostatStatistic = thermostatDataQueryService.maxTemperature(Arrays.asList(sensors.split(",")), timePeriod);

        verify(influxDBRepository, times(1))
                .max(sensorsCaptor.capture(), timePeriodCaptor.capture());
        assertThat(sensorsCaptor.getValue()).isEqualTo(sensors.replaceAll(",", "|"));
        assertThat(thermostatStatistic.getValue()).isEqualTo(thermostatStatistic1.getValue());
    }

    @Test
    public void medianTemperature() {
        String sensors = "1,2,3";
        String timePeriod = "1d";
        ThermostatStatistic thermostatStatistic1 = thermostatStatistic(10.0);
        when(influxDBRepository.median(any(), any()))
                .thenReturn(Arrays.asList(thermostatStatistic1));
        ThermostatStatistic thermostatStatistic = thermostatDataQueryService.medianTemperature(Arrays.asList(sensors.split(",")), timePeriod);

        verify(influxDBRepository, times(1))
                .median(sensorsCaptor.capture(), timePeriodCaptor.capture());
        assertThat(sensorsCaptor.getValue()).isEqualTo(sensors.replaceAll(",", "|"));
        assertThat(thermostatStatistic.getValue()).isEqualTo(thermostatStatistic1.getValue());
    }

    private ThermostatStatistic thermostatStatistic(double val) {
        ThermostatStatistic stat = new ThermostatStatistic();
        stat.setTime(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        stat.setValue(val);
        return stat;
    }

}

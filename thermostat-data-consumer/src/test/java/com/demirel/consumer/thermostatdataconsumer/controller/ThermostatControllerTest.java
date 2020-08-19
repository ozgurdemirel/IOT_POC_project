package com.demirel.consumer.thermostatdataconsumer.controller;

import com.demirel.consumer.thermostatdataconsumer.dto.ThermostatStatistic;
import com.demirel.consumer.thermostatdataconsumer.service.ThermostatDataConsumerService;
import com.demirel.consumer.thermostatdataconsumer.service.ThermostatDataQueryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(value = {ThermostatController.class})
class ThermostatControllerTest {

    @MockBean
    private ThermostatDataQueryService thermostatDataQueryService;

    @MockBean
    private ThermostatDataConsumerService thermostatDataConsumerService;

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(value = "test")
    @Test
    void shouldGetAverage() throws Exception {
        ThermostatStatistic thermostatStat = thermostatStatistic(10.0);
        String sensorNames = "1,2,3";
        String url = String.format("/temperature/average/%s/5m", sensorNames);
        when(thermostatDataQueryService
                .averageTemperature(Arrays.asList("1", "2", "3"), "5m"))
                .thenReturn(thermostatStat);

        mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(jsonPath("value").value(Double.valueOf(10.0)))
                .andExpect(status().isOk());

    }

    @WithMockUser(value = "test")
    @Test
    void shouldGetMedian() throws Exception {
        ThermostatStatistic thermostatStat = thermostatStatistic(10.0);
        String sensorNames = "1,2,3";
        String url = String.format("/temperature/median/%s/5m", sensorNames);
        when(thermostatDataQueryService
                .medianTemperature(Arrays.asList("1", "2", "3"), "5m"))
                .thenReturn(thermostatStat);

        mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(jsonPath("value").value(Double.valueOf(10.0)))
                .andExpect(status().isOk());

    }

    @WithMockUser(value = "test")
    @Test
    void shouldGetMax() throws Exception {
        ThermostatStatistic thermostatStat = thermostatStatistic(10.0);
        String sensorNames = "1,2,3";
        String url = String.format("/temperature/max/%s/5m", sensorNames);
        when(thermostatDataQueryService
                .maxTemperature(Arrays.asList("1", "2", "3"), "5m"))
                .thenReturn(thermostatStat);

        mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(jsonPath("value").value(Double.valueOf(10.0)))
                .andExpect(status().isOk());

    }

    @WithMockUser(value = "test")
    @Test
    void shouldGetMin() throws Exception {
        ThermostatStatistic thermostatStat = thermostatStatistic(10.0);
        String sensorNames = "1,2,3";
        String url = String.format("/temperature/min/%s/5m", sensorNames);
        when(thermostatDataQueryService
                .minTemperature(Arrays.asList("1", "2", "3"), "5m"))
                .thenReturn(thermostatStat);

        mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(jsonPath("value").value(Double.valueOf(10.0)))
                .andExpect(status().isOk());

    }


    private ThermostatStatistic thermostatStatistic(double val) {
        ThermostatStatistic stat = new ThermostatStatistic();
        stat.setTime(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        stat.setValue(val);
        return stat;
    }

}

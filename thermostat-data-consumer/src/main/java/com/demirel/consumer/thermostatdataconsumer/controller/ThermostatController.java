package com.demirel.consumer.thermostatdataconsumer.controller;

import com.demirel.consumer.thermostatdataconsumer.dto.ThermostatStatistic;
import com.demirel.consumer.thermostatdataconsumer.service.ThermostatDataQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/temperature")
@RequiredArgsConstructor
public class ThermostatController {

    private final ThermostatDataQueryService thermostatDataQueryService;

    @GetMapping("/average/{sensorNames}/{timePeriod}")
    @ResponseBody
    public ThermostatStatistic average(
            @PathVariable String[] sensorNames,
            @PathVariable String timePeriod
    ) {
        return thermostatDataQueryService.averageTemperature(Arrays.asList(sensorNames), timePeriod);
    }

    @GetMapping("/median/{sensorNames}/{timePeriod}")
    @ResponseBody
    public ThermostatStatistic median(
            @PathVariable String[] sensorNames,
            @PathVariable String timePeriod
    ) {
        return thermostatDataQueryService.medianTemperature(Arrays.asList(sensorNames), timePeriod);
    }

    @GetMapping("/max/{sensorNames}/{timePeriod}")
    @ResponseBody
    public ThermostatStatistic max(
            @PathVariable String[] sensorNames,
            @PathVariable String timePeriod
    ) {
        return thermostatDataQueryService.maxTemperature(Arrays.asList(sensorNames), timePeriod);
    }


    @GetMapping("/min/{sensorNames}/{timePeriod}")
    @ResponseBody
    public ThermostatStatistic min(
            @PathVariable String[] sensorNames,
            @PathVariable String timePeriod
    ) {
        return thermostatDataQueryService.minTemperature(Arrays.asList(sensorNames), timePeriod);
    }

}

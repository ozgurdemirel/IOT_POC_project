package com.demirel.consumer.thermostatdataconsumer.repository;

import com.demirel.consumer.thermostatdataconsumer.dto.ThermostatStatistic;
import lombok.RequiredArgsConstructor;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InfluxDBRepository {

    private final InfluxDB influxDB;

    @Value("${spring.influx.database:''}")
    private String database;

    public synchronized void save(Point point) {
        influxDB.write(database, "defaultPolicy", point);
    }

    public List<ThermostatStatistic> average(String sensorNames, String timePeriod) {
        String queryStr = String.format("SELECT MEAN(temperature) AS value  FROM thermostat where sensorName=~ /%s/ and time > now() - %s  ORDER BY time DESC", sensorNames, timePeriod);
        return query(queryStr, ThermostatStatistic.class);
    }

    public List<ThermostatStatistic> median(String sensorNames, String timePeriod) {
        String queryStr = String.format("SELECT MEDIAN(temperature)  AS value   FROM thermostat where  sensorName=~ /%s/  and time > now() - %s  ORDER BY time DESC", sensorNames, timePeriod);
        return query(queryStr, ThermostatStatistic.class);
    }

    public List<ThermostatStatistic> max(String sensorNames, String timePeriod) {
        String queryStr = String.format("SELECT MAX(temperature)  AS value   FROM thermostat where  sensorName=~ /%s/  and time > now() - %s   ORDER BY time DESC", sensorNames, timePeriod);
        return query(queryStr, ThermostatStatistic.class);
    }

    public List<ThermostatStatistic> min(String sensorNames, String timePeriod) {
        String queryStr = String.format("SELECT MIN(temperature)  AS value   FROM thermostat where  sensorName=~ /%s/  and time > now() - %s    ORDER BY time DESC", sensorNames, timePeriod);
        return query(queryStr, ThermostatStatistic.class);
    }


    private synchronized <T> List<T> query(String command, Class<T> clazz) {
        QueryResult queryResult = influxDB.query(new Query(command, database));
        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
        List<T> tList = resultMapper.toPOJO(queryResult, clazz);
        return tList;
    }

}

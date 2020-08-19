# IOT POC


# Architecture

## Tech Stack

``
JAVA 11 ``
``
Spring boot 2.X ``
``
Eclipse Mosquitto(MQTT protocol) ``
``
InfluxDB ``
----------------------

CI with .gitlab.yml  

##### Improvement areas: 
  - Test Code coverage 
  - Documentation

## thermostat-data-consumer 
``
Simple Spring boot application, which reads measurments from Eclipse Mosquitto(MQTT protocol), and puts measurment into InfluxDb. This application expects specific json format message. For example: ``


```json
{
    "sensorName":"sensor3", 
    "temperature":29,
    "time":1592528347307
}
```

End-points served by this API can be easily watched by navigating following url: http://localhost:8080/swagger-ui.html

##### Security basic authentication user name :  user1 , password : user1Pass

You can compile it via:

```bash
./mvnw -U clean compile package
```


## thermostat-front
``
Java Application for sending IOT Fake Thermostat data to the Eclipse Mosquitto ``


You can compile it via:

```bash
./mvnw -U clean compile package
```
it takes sensor name as environment variable :  - applicationId=sensor3, check docker-compose.yml


# IOT DATA API


#### /sensorsNames/timePeriod
- sensor names with ,
- timePeriod like 5m, 5d etc...
----------------------------------------------
  - curl -X GET http://localhost:8080/temperature/average/sensor1,sensor2,sensor3/5m -H 'Authorization: Basic dXNlcjE6dXNlcjFQYXNz' 

  - curl -X GET http://localhost:8080/temperature/min/sensor1,sensor2,sensor3/5m -H 'Authorization: Basic dXNlcjE6dXNlcjFQYXNz' 

  - curl -X GET http://localhost:8080/temperature/max/sensor1,sensor2,sensor3/5m -H 'Authorization: Basic dXNlcjE6dXNlcjFQYXNz' 

  - curl -X GET http://localhost:8080/temperature/median/sensor1,sensor2,sensor3/5m -H 'Authorization: Basic dXNlcjE6dXNlcjFQYXNz' 



# RUN 3 IOT with docker compose at the same time

```bash
docker-compose up
```

#### docker-compose.yml

```yml
version: "3"
services:
  data-consumer:
    image: ozgurclub/thermostat-data-consumer
    ports:
      - 8080:8076
    networks:
      - backend
    environment:
      - INFLUX_URL=http://influxdb:8086
      - MQTT_URL=tcp://mosquitto:1883
    links:
      - "mosquitto"
      - "influxdb"
  sensor1:
    image: ozgurclub/thermostat
    networks:
      - backend
    environment:
      - mqttClientConnectionString=tcp://mosquitto:1883
      - applicationId=sensor1
    links:
      - "mosquitto"
  sensor2:
    image: ozgurclub/thermostat
    networks:
      - backend
    environment:
      - mqttClientConnectionString=tcp://mosquitto:1883
      - applicationId=sensor2
    links:
      - "mosquitto"
  sensor3:
    image: ozgurclub/thermostat
    networks:
      - backend
    environment:
      - mqttClientConnectionString=tcp://mosquitto:1883
      - applicationId=sensor3
    links:
      - "mosquitto"
  mosquitto:
    image: eclipse-mosquitto:1.4.12
    container_name: mosquitto
    ports:
      - 1883:1883 # MQTT port
      - 9001:9001 # MQTT websocket port
    networks:
      - backend
    volumes:
      - $PWD/conf/mosquitto/config/mosquitto.conf:/mosquitto/config/mosquitto.conf
      - $PWD/conf/mosquitto/data:/mosquitto/data

  influxdb:
    image: influxdb:1.5.2
    container_name: influxdb
    environment:
      - INFLUXDB_REPORTING_DISABLED=true
      - INFLUXDB_HTTP_AUTH_ENABLED=true
      - INFLUXDB_ADMIN_USER=root
      - INFLUXDB_ADMIN_PASSWORD=root
      - INFLUXDB_DB=iotdata
    networks:
      - backend
    ports:
      - 8086:8086
    volumes:
      - $PWD/conf/influxdb/data:/var/lib/influxdb
networks:
  backend:

```









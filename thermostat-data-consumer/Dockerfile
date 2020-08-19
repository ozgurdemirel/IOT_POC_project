FROM adoptopenjdk:11-jre-openj9
RUN mkdir /opt/app
COPY target/thermostat-data-consumer.jar /opt/app
CMD ["java", "-jar", "/opt/app/thermostat-data-consumer.jar"]

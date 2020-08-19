FROM adoptopenjdk:11-jre-openj9
RUN mkdir /opt/app
COPY target/thermostat-jar-with-dependencies.jar /opt/app
CMD ["java", "-jar", "/opt/app/thermostat-jar-with-dependencies.jar"]

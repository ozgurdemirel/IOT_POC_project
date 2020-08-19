package com.demirel.temperature.sensor;

import com.demirel.temperature.ApplicationConstants;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ThermostatDataManagerTest {

    @Mock
    private MqttAsyncClient publisher;

    @InjectMocks
    private ThermostatDataManager thermostatDataManager;

    private ApplicationConstants constants = ApplicationConstants.getInstance();

    @Test
    public void shouldPublishTemperature() throws Exception {
        when(publisher.isConnected()).thenReturn(Boolean.TRUE);

        thermostatDataManager.publishTemperature();

        verify(publisher, times(1)).publish(eq(constants.getPublisherTopicName()), Mockito.any());
    }

    @Test
    public void shouldPublishTemperatureNotConnected() throws Exception {
        when(publisher.isConnected()).thenReturn(Boolean.FALSE);

        thermostatDataManager.publishTemperature();

        verify(publisher, times(0)).publish(eq(constants.getPublisherTopicName()), Mockito.any());
    }


}

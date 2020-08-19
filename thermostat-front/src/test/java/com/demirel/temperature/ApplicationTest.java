package com.demirel.temperature;

import com.demirel.temperature.sensor.ThermostatDataManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Timer;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationTest {

    @Mock
    private ThermostatDataManager thermostatDataManager;

    @Mock
    private Timer timer;

    @Mock
    private ApplicationConstants applicationConstants;

    @InjectMocks
    private Application application;


    @Test
    public void shouldCollectData()  {
        when(applicationConstants.getTimerPeriod()).thenReturn(100L);
        when(applicationConstants.getTimerDelay()).thenReturn(0L);

        application.collectData();

        verify(timer).schedule(any(), eq(0l), eq(100l));
    }

}

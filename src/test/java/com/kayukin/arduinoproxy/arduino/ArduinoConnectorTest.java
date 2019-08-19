package com.kayukin.arduinoproxy.arduino;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArduinoConnectorTest {
    private ArduinoConnector underTest;

    @Mock
    private Arduino arduino;

    @Before
    public void setUp() {
        underTest = new ArduinoConnector(arduino);
    }

    @Test
    public void testCorrectResponse() {
        when(arduino.serialRead()).thenReturn("{\"humidity\": 55.5,\"temperature\": 25.2}");

        underTest.pollSensors();

        verify(arduino).serialWrite(any());
    }
}
package com.kayukin.arduinoproxy.arduino;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

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
        underTest.pollSensors();

        verify(arduino).serialWrite(any());
    }
}
package com.kayukin.arduinoproxy.task;

import com.kayukin.arduinoproxy.arduino.ArduinoConnector;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PollSensorsTaskTest {
    private PollSensorsTask underTest;

    @Mock
    private ArduinoConnector arduinoConnector;

    @Before
    public void setUp() {
        underTest = new PollSensorsTask(arduinoConnector);
    }

    @Test
    public void testRun() {
        underTest.run();

        verify(arduinoConnector).pollSensors();
    }
}
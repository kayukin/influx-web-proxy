package com.kayukin.arduinoproxy.arduino;

import com.fazecast.jSerialComm.SerialPort;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.OutputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArduinoConnectorTest {
    private ArduinoConnector underTest;

    @Mock
    private SerialPort serialPort;
    @Mock
    private OutputStream outputStream;

    @Before
    public void setUp() {
        underTest = new ArduinoConnector(serialPort);
    }

    @Test
    public void testCorrectResponse() throws IOException {
        when(serialPort.getOutputStream()).thenReturn(outputStream);
        underTest.pollSensors();

        verify(outputStream).write(any(byte[].class), anyInt(), anyInt());
    }
}
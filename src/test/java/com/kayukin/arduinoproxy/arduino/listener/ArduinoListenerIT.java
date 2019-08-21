package com.kayukin.arduinoproxy.arduino.listener;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.kayukin.arduinoproxy.connection.InfluxConnection;
import com.kayukin.arduinoproxy.connection.ThingSpeakConnection;
import com.kayukin.arduinoproxy.model.SensorsData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ArduinoListenerIT {
    @Autowired
    private ArduinoListener underTest;

    @MockBean
    private SerialPort serialPort;
    @MockBean
    private ThingSpeakConnection thingSpeakConnection;
    @MockBean
    private InfluxConnection influxConnection;
    @Mock
    private SerialPortEvent event;

    @Test
    public void testEvent() {
        String response = "{\"humidity\":36.5,\"temperature\":28.2}";
        SensorsData expected = new SensorsData(36.5, 28.2);
        when(event.getSerialPort()).thenReturn(serialPort);
        when(serialPort.bytesAvailable()).thenReturn(response.getBytes().length);
        when(serialPort.readBytes(any(), anyLong())).then(invocationOnMock -> {
            byte[] dest = invocationOnMock.getArgument(0);
            System.arraycopy(response.getBytes(), 0, dest, 0, response.getBytes().length);
            return response.getBytes().length;
        });

        underTest.serialEvent(event);

        verify(thingSpeakConnection).accept(eq(expected));
        verify(influxConnection).accept(eq(expected));
    }
}
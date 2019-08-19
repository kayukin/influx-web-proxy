package com.kayukin.arduinoproxy.arduino.listener;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.kayukin.arduinoproxy.arduino.parser.SensorsDataParser;
import com.kayukin.arduinoproxy.connection.SensorsDataConsumer;
import com.kayukin.arduinoproxy.model.SensorsData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArduinoListenerTest {
    private ArduinoListener underTest;

    @Mock
    private SensorsDataParser sensorsDataParser;
    @Mock
    private SensorsDataConsumer sensorsDataConsumer;
    @Mock
    private SerialPortEvent event;
    @Mock
    private SerialPort port;

    @Before
    public void setUp() {
        underTest = new ArduinoListener(sensorsDataParser, List.of(sensorsDataConsumer));
        when(event.getSerialPort()).thenReturn(port);
    }

    @Test
    public void testEventType() {
        int listeningEvents = underTest.getListeningEvents();
        assertThat(listeningEvents).isEqualTo(SerialPort.LISTENING_EVENT_DATA_AVAILABLE);
    }

    @Test
    public void testEvent() {
        String response = "{15,20}";
        when(port.bytesAvailable()).thenReturn(response.getBytes().length);
        when(port.readBytes(any(), anyLong())).then(invocationOnMock -> {
            byte[] dest = invocationOnMock.getArgument(0);
            System.arraycopy(response.getBytes(), 0, dest, 0, response.getBytes().length);
            return response.getBytes().length;
        });
        SensorsData sensorsData = new SensorsData();
        sensorsData.setHumidity(35.);
        sensorsData.setTemperature(50.);
        when(sensorsDataParser.parse(any())).thenReturn(sensorsData);

        underTest.serialEvent(event);

        verify(sensorsDataConsumer).accept(sensorsData);
    }
}
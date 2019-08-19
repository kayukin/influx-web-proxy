package com.kayukin.arduinoproxy.arduino;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kayukin.arduinoproxy.model.SensorsData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArduinoConnectorTest {
    private ArduinoConnector underTest;

    @Mock
    private Arduino arduino;

    @Before
    public void setUp() {
        underTest = new ArduinoConnector(arduino, new ObjectMapper());
    }

    @Test
    public void testCorrectResponse() {
        when(arduino.serialRead()).thenReturn("{\"humidity\": 55.5,\"temperature\": 25.2}");

        Optional<SensorsData> result = underTest.readSensorsData();

        assertThat(result).isPresent()
                .get().extracting(SensorsData::getHumidity, SensorsData::getTemperature).containsExactly(55.5, 25.2);
    }
}
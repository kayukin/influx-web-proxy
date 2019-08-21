package com.kayukin.arduinoproxy.arduino.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kayukin.arduinoproxy.exception.ParseSensorsException;
import com.kayukin.arduinoproxy.model.SensorsData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JsonSensorsDataParserTest {
    private JsonSensorsDataParser underTest;

    @Mock
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        underTest = new JsonSensorsDataParser(objectMapper);
    }

    @Test
    public void testParse() throws IOException {
        when(objectMapper.readValue(anyString(), any(Class.class)))
                .thenReturn(new SensorsData(40., 25.));

        SensorsData result = underTest.parse("{temp:25, hum:40}");

        assertThat(result.getHumidity()).isEqualTo(40);
        assertThat(result.getTemperature()).isEqualTo(25);
    }

    @Test
    public void testException() throws IOException {
        when(objectMapper.readValue(anyString(), any(Class.class))).thenThrow(new IOException());

        assertThatThrownBy(() -> underTest.parse("----"))
                .isInstanceOf(ParseSensorsException.class);
    }
}
package com.kayukin.arduinoproxy.arduino;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kayukin.arduinoproxy.exception.ReadSensorsException;
import com.kayukin.arduinoproxy.model.SensorsData;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;

@Slf4j
public class ArduinoConnector {
    private static final String READ_COMMAND = "read";
    private final Arduino arduino;
    private final ObjectMapper objectMapper;

    public ArduinoConnector(Arduino arduino, ObjectMapper objectMapper) {
        this.arduino = arduino;
        this.objectMapper = objectMapper;
        arduino.openConnection();
    }

    public Optional<SensorsData> readSensorsData() {
        arduino.serialWrite(READ_COMMAND);
        String rawData = arduino.serialRead();
        return Optional.ofNullable(rawData)
                .map(this::parseRawData);
    }

    private SensorsData parseRawData(String rawData) {
        try {
            return objectMapper.readValue(rawData, SensorsData.class);
        } catch (IOException e) {
            throw new ReadSensorsException(e);
        }
    }
}

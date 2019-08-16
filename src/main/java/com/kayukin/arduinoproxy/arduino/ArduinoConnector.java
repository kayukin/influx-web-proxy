package com.kayukin.arduinoproxy.arduino;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kayukin.arduinoproxy.exception.ReadSensorsException;
import com.kayukin.arduinoproxy.model.SensorsData;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ArduinoConnector {
    private static final String READ_COMMAND = "read";
    private final Arduino arduino;
    private final ObjectMapper objectMapper;

    public ArduinoConnector(String portDescriptor, ObjectMapper objectMapper) {
        arduino = new Arduino(portDescriptor, 9600);
        this.objectMapper = objectMapper;
        arduino.openConnection();
    }

    public void close() {
        log.info("Closing arduino connection");
        arduino.closeConnection();
    }

    public SensorsData readSensorsData() {
        arduino.serialWrite(READ_COMMAND);
        String rawData = arduino.serialRead();
        try {
            return objectMapper.readValue(rawData, SensorsData.class);
        } catch (IOException e) {
            throw new ReadSensorsException(e);
        }
    }
}

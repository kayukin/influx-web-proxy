package com.kayukin.arduinoproxy.arduino.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kayukin.arduinoproxy.exception.ParseSensorsException;
import com.kayukin.arduinoproxy.model.SensorsData;

import java.io.IOException;

public class JsonSensorsDataParser implements SensorsDataParser {
    private final ObjectMapper objectMapper;

    public JsonSensorsDataParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public SensorsData parse(String rawData) {
        try {
            return objectMapper.readValue(rawData, SensorsData.class);
        } catch (IOException e) {
            throw new ParseSensorsException(e);
        }
    }
}

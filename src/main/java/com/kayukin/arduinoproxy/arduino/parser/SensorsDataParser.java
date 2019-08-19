package com.kayukin.arduinoproxy.arduino.parser;

import com.kayukin.arduinoproxy.model.SensorsData;

public interface SensorsDataParser {
    SensorsData parse(String rawData);
}

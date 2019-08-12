package com.kayukin.arduinoproxy.arduino;

import com.kayukin.arduinoproxy.model.SensorsData;

public class ArduinoConnector {
    public ArduinoConnector() {

    }

    public SensorsData readSensorsData() {
        return new SensorsData();
    }
}

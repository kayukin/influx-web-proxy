package com.kayukin.arduinoproxy.arduino;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ArduinoConnector {
    private static final String READ_COMMAND = "read";
    private final Arduino arduino;

    public ArduinoConnector(Arduino arduino) {
        this.arduino = arduino;
    }

    public void pollSensors() {
        arduino.serialWrite(READ_COMMAND);
    }
}

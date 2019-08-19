package com.kayukin.arduinoproxy.task;

import com.kayukin.arduinoproxy.arduino.ArduinoConnector;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PollSensorsTask {
    private final ArduinoConnector arduinoConnector;

    public PollSensorsTask(ArduinoConnector arduinoConnector) {
        this.arduinoConnector = arduinoConnector;
    }

    public void run() {
        arduinoConnector.pollSensors();
    }
}

package com.kayukin.arduinoproxy.task;

import com.kayukin.arduinoproxy.arduino.ArduinoConnector;
import com.kayukin.arduinoproxy.connection.ThingSpeakConnection;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PollSensorsTask {
    private final ThingSpeakConnection thingSpeakConnection;
    private final ArduinoConnector arduinoConnector;

    public PollSensorsTask(ThingSpeakConnection thingSpeakConnection, ArduinoConnector arduinoConnector) {
        this.thingSpeakConnection = thingSpeakConnection;
        this.arduinoConnector = arduinoConnector;
    }

    public void run() {
        arduinoConnector.readSensorsData()
                .ifPresentOrElse(thingSpeakConnection::sendSensorsData, () -> log.warn("Empty arduino response"));
    }
}

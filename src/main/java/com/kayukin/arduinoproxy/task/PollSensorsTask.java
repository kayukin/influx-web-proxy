package com.kayukin.arduinoproxy.task;

import com.kayukin.arduinoproxy.arduino.ArduinoConnector;
import com.kayukin.arduinoproxy.connection.ThingSpeakConnection;
import com.kayukin.arduinoproxy.model.SensorsData;

public class PollSensorsTask {
    private final ThingSpeakConnection thingSpeakConnection;
    private final ArduinoConnector arduinoConnector;

    public PollSensorsTask(ThingSpeakConnection thingSpeakConnection, ArduinoConnector arduinoConnector) {
        this.thingSpeakConnection = thingSpeakConnection;
        this.arduinoConnector = arduinoConnector;
    }

    public void run() {
        SensorsData sensorsData = arduinoConnector.readSensorsData();
        thingSpeakConnection.sendSensorsData(sensorsData);
    }
}

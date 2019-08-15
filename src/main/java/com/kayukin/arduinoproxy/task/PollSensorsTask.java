package com.kayukin.arduinoproxy.task;

import com.kayukin.arduinoproxy.arduino.ArduinoConnector;
import com.kayukin.arduinoproxy.connection.ThingSpeakConnection;
import com.kayukin.arduinoproxy.model.SensorsData;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PollSensorsTask {
    private static final int POLL_RATE = 3000;

    private final ThingSpeakConnection thingSpeakConnection;
    private final ArduinoConnector arduinoConnector;

    public PollSensorsTask(ThingSpeakConnection thingSpeakConnection, ArduinoConnector arduinoConnector) {
        this.thingSpeakConnection = thingSpeakConnection;
        this.arduinoConnector = arduinoConnector;
    }

    @Scheduled(fixedRate = POLL_RATE)
    public void run() {
        SensorsData sensorsData = arduinoConnector.readSensorsData();
        thingSpeakConnection.sendSensorsData(sensorsData);
    }
}

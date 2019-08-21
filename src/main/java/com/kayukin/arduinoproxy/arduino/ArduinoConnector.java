package com.kayukin.arduinoproxy.arduino;

import com.fazecast.jSerialComm.SerialPort;
import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;

@Slf4j
public class ArduinoConnector {
    private static final String READ_COMMAND = "read";
    private final SerialPort serialPort;

    public ArduinoConnector(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    public void pollSensors() {
        try (PrintWriter writer = new PrintWriter(serialPort.getOutputStream())) {
            writer.print(READ_COMMAND);
            writer.flush();
        }
    }
}

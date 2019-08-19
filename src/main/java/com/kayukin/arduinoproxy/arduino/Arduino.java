package com.kayukin.arduinoproxy.arduino;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

@Slf4j
public class Arduino {
    private final SerialPort serialPort;

    public Arduino(String portDescription, int baudRate) {
        serialPort = SerialPort.getCommPort(portDescription);
        serialPort.setBaudRate(baudRate);
    }

    public boolean openConnection() {
        return serialPort.openPort();
    }

    public void closeConnection() {
        serialPort.closePort();
    }

    public String serialRead() {
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(serialPort.getInputStream()))) {
            return in.readLine();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public void serialWrite(String s) {
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        try (PrintWriter writer = new PrintWriter(serialPort.getOutputStream())) {
            writer.print(s);
        }
    }

    public void registerEventListener(SerialPortDataListener listener) {
        serialPort.addDataListener(listener);
    }
}

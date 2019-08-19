package com.kayukin.arduinoproxy.arduino.listener;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.kayukin.arduinoproxy.arduino.parser.SensorsDataParser;
import com.kayukin.arduinoproxy.connection.SensorsDataConsumer;
import com.kayukin.arduinoproxy.model.SensorsData;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
public class ArduinoListener implements SerialPortDataListener {
    private final SensorsDataParser sensorsDataParser;
    private final List<SensorsDataConsumer> sensorsDataConsumers;

    public ArduinoListener(SensorsDataParser sensorsDataParser, List<SensorsDataConsumer> sensorsDataConsumers) {
        this.sensorsDataParser = sensorsDataParser;
        this.sensorsDataConsumers = sensorsDataConsumers;
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        log.debug("Received data available event");
        SerialPort serialPort = event.getSerialPort();
        int bytesAvailable = serialPort.bytesAvailable();
        byte[] buffer = new byte[bytesAvailable];
        serialPort.readBytes(buffer, bytesAvailable);
        String rawData = new String(buffer, StandardCharsets.UTF_8);
        log.debug("Raw data: {}", rawData);
        SensorsData sensorsData = sensorsDataParser.parse(rawData);
        sensorsDataConsumers.forEach(consumer -> consumer.accept(sensorsData));
    }
}

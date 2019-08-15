package com.kayukin.arduinoproxy.arduino;

import com.fazecast.jSerialComm.SerialPort;
import com.kayukin.arduinoproxy.exception.ReadSensorsException;
import com.kayukin.arduinoproxy.model.SensorsData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class ArduinoConnector {
    private final SerialPort commPort;

    public ArduinoConnector(@Value("${serialPort}") String portDescriptor) {
        this.commPort = SerialPort.getCommPort(portDescriptor);
        commPort.openPort();
        if (!commPort.isOpen()) {
            throw new IllegalStateException("Can't open port " + portDescriptor);
        }
    }

    public SensorsData readSensorsData() {
        try {
            String rawData = IOUtils.toString(commPort.getInputStream(), StandardCharsets.UTF_8);
            return parseSensorsData(rawData);
        } catch (IOException e) {
            log.error("Failed to read sensors data", e);
            throw new ReadSensorsException(e);
        }
    }

    private SensorsData parseSensorsData(String rawData) {
        String[] s = rawData.split(" ");
        return SensorsData.builder()
                .withHumidity(Integer.valueOf(s[0]))
                .withTemperature(Integer.valueOf(s[1]))
                .build();
    }
}

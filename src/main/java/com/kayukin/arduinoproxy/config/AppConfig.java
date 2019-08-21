package com.kayukin.arduinoproxy.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fazecast.jSerialComm.SerialPort;
import com.kayukin.arduinoproxy.arduino.ArduinoConnector;
import com.kayukin.arduinoproxy.arduino.listener.ArduinoListener;
import com.kayukin.arduinoproxy.arduino.parser.JsonSensorsDataParser;
import com.kayukin.arduinoproxy.arduino.parser.SensorsDataParser;
import com.kayukin.arduinoproxy.connection.InfluxConnection;
import com.kayukin.arduinoproxy.connection.SensorsDataConsumer;
import com.kayukin.arduinoproxy.connection.ThingSpeakConnection;
import com.kayukin.arduinoproxy.property.ArduinoProperties;
import com.kayukin.arduinoproxy.property.ThingSpeakProperties;
import com.kayukin.arduinoproxy.task.PollSensorsTask;
import org.influxdb.InfluxDB;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;

import java.util.List;

@Configuration
public class AppConfig {
    @Bean
    public PollSensorsTask pollSensorsTask(ArduinoConnector arduinoConnector,
                                           TaskScheduler taskScheduler,
                                           ArduinoProperties arduinoProperties) {
        PollSensorsTask pollSensorsTask = new PollSensorsTask(arduinoConnector);
        taskScheduler.scheduleWithFixedDelay(pollSensorsTask::run, arduinoProperties.getPollRate());
        return pollSensorsTask;
    }

    @Bean
    public ArduinoConnector arduinoConnector(SerialPort serialPort) {
        return new ArduinoConnector(serialPort);
    }

    @Bean
    public ArduinoListener arduinoListener(SensorsDataParser sensorsDataParser, List<SensorsDataConsumer> consumers) {
        return new ArduinoListener(sensorsDataParser, consumers);
    }

    @Bean
    public SensorsDataParser sensorsDataParser(ObjectMapper objectMapper) {
        return new JsonSensorsDataParser(objectMapper);
    }

    @Bean(destroyMethod = "closePort")
    public SerialPort serialPort(ArduinoProperties arduinoProperties, ArduinoListener arduinoListener) {
        SerialPort serialPort = SerialPort.getCommPort(arduinoProperties.getSerialPort());
        serialPort.setBaudRate(arduinoProperties.getBaudRate());
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        if (!serialPort.openPort()) {
            throw new BeanInitializationException("Can't connect to " + arduinoProperties.getSerialPort());
        }
        serialPort.addDataListener(arduinoListener);
        return serialPort;
    }

    @Bean
    @ConditionalOnProperty("thing-speak")
    public ThingSpeakConnection thingSpeakConnection(ThingSpeakProperties thingSpeakProperties) {
        return new ThingSpeakConnection(thingSpeakProperties.getApiUrl(), thingSpeakProperties.getKey());
    }

    @Bean
    @ConditionalOnBean(InfluxDB.class)
    public InfluxConnection influxConnection(InfluxDB influxDB) {
        influxDB.setDatabase("sensors");
        return new InfluxConnection(influxDB);
    }
}

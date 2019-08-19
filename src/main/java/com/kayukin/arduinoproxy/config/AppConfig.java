package com.kayukin.arduinoproxy.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kayukin.arduinoproxy.arduino.Arduino;
import com.kayukin.arduinoproxy.arduino.ArduinoConnector;
import com.kayukin.arduinoproxy.arduino.listener.ArduinoListener;
import com.kayukin.arduinoproxy.arduino.parser.JsonSensorsDataParser;
import com.kayukin.arduinoproxy.arduino.parser.SensorsDataParser;
import com.kayukin.arduinoproxy.connection.SensorsDataConsumer;
import com.kayukin.arduinoproxy.connection.ThingSpeakConnection;
import com.kayukin.arduinoproxy.property.ArduinoProperties;
import com.kayukin.arduinoproxy.property.ThingSpeakProperties;
import com.kayukin.arduinoproxy.task.PollSensorsTask;
import org.springframework.beans.factory.BeanInitializationException;
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
    public ArduinoConnector arduinoConnector(Arduino arduino) {
        return new ArduinoConnector(arduino);
    }

    @Bean
    public ArduinoListener arduinoListener(SensorsDataParser sensorsDataParser, List<SensorsDataConsumer> consumers) {
        return new ArduinoListener(sensorsDataParser, consumers);
    }

    @Bean
    public SensorsDataParser sensorsDataParser(ObjectMapper objectMapper) {
        return new JsonSensorsDataParser(objectMapper);
    }

    @Bean(destroyMethod = "closeConnection")
    public Arduino arduino(ArduinoProperties arduinoProperties, ArduinoListener arduinoListener) {
        Arduino arduino = new Arduino(arduinoProperties.getSerialPort(), arduinoProperties.getBaudRate());
        if (!arduino.openConnection()) {
            throw new BeanInitializationException("Can't connect to " + arduinoProperties.getSerialPort());
        }
        arduino.registerEventListener(arduinoListener);
        return arduino;
    }

    @Bean
    public ThingSpeakConnection thingSpeakConnection(ThingSpeakProperties thingSpeakProperties) {
        return new ThingSpeakConnection(thingSpeakProperties.getApiUrl(), thingSpeakProperties.getKey());
    }
}

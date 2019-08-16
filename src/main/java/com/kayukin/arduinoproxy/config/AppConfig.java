package com.kayukin.arduinoproxy.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kayukin.arduinoproxy.arduino.ArduinoConnector;
import com.kayukin.arduinoproxy.connection.ThingSpeakConnection;
import com.kayukin.arduinoproxy.task.PollSensorsTask;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;

import java.time.Duration;

@Configuration
public class AppConfig {
    @Bean
    public PollSensorsTask pollSensorsTask(ArduinoConnector arduinoConnector,
                                           ThingSpeakConnection thingSpeakConnection,
                                           TaskScheduler taskScheduler,
                                           @Value("${pollRate}") Duration duration) {
        PollSensorsTask pollSensorsTask = new PollSensorsTask(thingSpeakConnection, arduinoConnector);
        taskScheduler.scheduleWithFixedDelay(pollSensorsTask::run, duration);
        return pollSensorsTask;
    }

    @Bean(destroyMethod = "close")
    public ArduinoConnector arduinoConnector(@Value("${serialPort}") String portDescriptor, ObjectMapper objectMapper) {
        return new ArduinoConnector(portDescriptor, objectMapper);
    }

    @Bean
    public ThingSpeakConnection thingSpeakConnection() {
        return new ThingSpeakConnection();
    }
}

package com.kayukin.arduinoproxy.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kayukin.arduinoproxy.arduino.Arduino;
import com.kayukin.arduinoproxy.arduino.ArduinoConnector;
import com.kayukin.arduinoproxy.connection.ThingSpeakConnection;
import com.kayukin.arduinoproxy.property.ArduinoProperties;
import com.kayukin.arduinoproxy.property.ThingSpeakProperties;
import com.kayukin.arduinoproxy.task.PollSensorsTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;

@Configuration
public class AppConfig {
    @Bean
    public PollSensorsTask pollSensorsTask(ArduinoConnector arduinoConnector,
                                           ThingSpeakConnection thingSpeakConnection,
                                           TaskScheduler taskScheduler,
                                           ArduinoProperties arduinoProperties) {
        PollSensorsTask pollSensorsTask = new PollSensorsTask(thingSpeakConnection, arduinoConnector);
        taskScheduler.scheduleWithFixedDelay(pollSensorsTask::run, arduinoProperties.getPollRate());
        return pollSensorsTask;
    }

    @Bean
    public ArduinoConnector arduinoConnector(Arduino arduino, ObjectMapper objectMapper) {
        return new ArduinoConnector(arduino, objectMapper);
    }

    @Bean(destroyMethod = "closeConnection")
    public Arduino arduino(ArduinoProperties arduinoProperties) {
        return new Arduino(arduinoProperties.getSerialPort(), arduinoProperties.getBaudRate());
    }

    @Bean
    public ThingSpeakConnection thingSpeakConnection(ThingSpeakProperties thingSpeakProperties) {
        return new ThingSpeakConnection(thingSpeakProperties.getApiUrl(), thingSpeakProperties.getKey());
    }
}

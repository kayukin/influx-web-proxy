package com.kayukin.arduinoproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ArduinoProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArduinoProxyApplication.class, args);
    }

}

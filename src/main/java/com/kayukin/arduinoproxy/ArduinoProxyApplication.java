package com.kayukin.arduinoproxy;

import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class ArduinoProxyApplication {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("JVM shutting down, closing Unirest");
            Unirest.shutDown();
        }));
        SpringApplication.run(ArduinoProxyApplication.class, args);
    }

}

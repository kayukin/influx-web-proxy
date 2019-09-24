package com.kayukin.influxproxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class InfluxProxyApplication {
    public static void main(String[] args) {
        SpringApplication.run(InfluxProxyApplication.class, args);
    }
}

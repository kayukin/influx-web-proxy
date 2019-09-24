package com.kayukin.influxproxy.config;

import com.kayukin.influxproxy.connection.InfluxConnection;
import org.influxdb.InfluxDB;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public InfluxConnection influxConnection(InfluxDB influxDB) {
        influxDB.setDatabase("sensors");
        return new InfluxConnection(influxDB);
    }
}

package com.kayukin.influxproxy.controller;

import com.kayukin.influxproxy.connection.InfluxConnection;
import com.kayukin.influxproxy.model.SensorsData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sensors")
public class DataController {
    private final InfluxConnection influxConnection;

    public DataController(InfluxConnection influxConnection) {
        this.influxConnection = influxConnection;
    }

    @PostMapping
    public ResponseEntity readSensorsData(@RequestBody SensorsData sensorsData) {
        influxConnection.accept(sensorsData);
        return ResponseEntity.ok().build();
    }
}

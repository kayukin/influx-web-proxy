package com.kayukin.influxproxy.connection;

import com.kayukin.influxproxy.model.SensorsData;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;

import java.util.concurrent.TimeUnit;

public class InfluxConnection {
    private final InfluxDB influxDB;

    public InfluxConnection(InfluxDB influxDB) {
        this.influxDB = influxDB;
    }

    public void accept(SensorsData sensorsData) {
        influxDB.write(Point.measurement("home")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("temperature", sensorsData.getTemperature())
                .addField("humidity", sensorsData.getHumidity())
                .build());
    }
}

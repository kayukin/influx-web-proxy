package com.kayukin.arduinoproxy.connection;

import com.kayukin.arduinoproxy.model.SensorsData;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;

import java.util.concurrent.TimeUnit;

public class InfluxConnection implements SensorsDataConsumer {
    private final InfluxDB influxDB;

    public InfluxConnection(InfluxDB influxDB) {
        this.influxDB = influxDB;
    }

    @Override
    public void accept(SensorsData sensorsData) {
        influxDB.write(Point.measurement("home")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("temperature", sensorsData.getTemperature())
                .addField("humidity", sensorsData.getHumidity())
                .build());
    }
}

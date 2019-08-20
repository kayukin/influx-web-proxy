package com.kayukin.arduinoproxy.connection;

import com.kayukin.arduinoproxy.model.SensorsData;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class InfluxConnectionTest {
    private InfluxConnection underTest;

    @Mock
    private InfluxDB influxDB;

    @Before
    public void setUp() {
        underTest = new InfluxConnection(influxDB);
    }

    @Test
    public void testWriteToDb() {
        underTest.accept(new SensorsData());

        verify(influxDB).write(any(Point.class));
    }
}
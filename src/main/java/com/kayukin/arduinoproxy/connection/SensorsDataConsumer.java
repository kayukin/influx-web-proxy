package com.kayukin.arduinoproxy.connection;

import com.kayukin.arduinoproxy.model.SensorsData;

public interface SensorsDataConsumer {
    void accept(SensorsData sensorsData);
}

package com.kayukin.arduinoproxy.connection;

import com.kayukin.arduinoproxy.model.SensorsData;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ThingSpeakConnection {
    private static final Logger log = LoggerFactory.getLogger(ThingSpeakConnection.class);
    private static final String API_URL = "https://api.thingspeak.com/update";

    public void sendSensorsData(SensorsData sensorsData) {
        HttpResponse<String> response = Unirest.get(API_URL)
                .queryString("api_key", "EQEGDGU0V5OB5IAY")
                .queryString("field1", sensorsData.getTemperature())
                .queryString("field2", sensorsData.getHumidity())
                .asString();
        if (!response.isSuccess()) {
            log.error("Failed to send sensors data: {}", response.getStatusText());
        }
    }
}

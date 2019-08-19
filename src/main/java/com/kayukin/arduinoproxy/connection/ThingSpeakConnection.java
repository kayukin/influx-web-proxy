package com.kayukin.arduinoproxy.connection;

import com.kayukin.arduinoproxy.model.SensorsData;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThingSpeakConnection implements SensorsDataConsumer {
    private static final Logger log = LoggerFactory.getLogger(ThingSpeakConnection.class);
    private final String apiUrl;
    private final String key;

    public ThingSpeakConnection(String apiUrl, String key) {
        this.apiUrl = apiUrl;
        this.key = key;
    }

    @Override
    public void accept(SensorsData sensorsData) {
        HttpResponse<String> response = Unirest.get(apiUrl)
                .queryString("api_key", key)
                .queryString("field1", sensorsData.getTemperature())
                .queryString("field2", sensorsData.getHumidity())
                .asString();
        if (!response.isSuccess()) {
            log.error("Failed to send sensors data: {}", response.getStatusText());
        }
    }
}

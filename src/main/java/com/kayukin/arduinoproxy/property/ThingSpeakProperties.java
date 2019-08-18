package com.kayukin.arduinoproxy.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("thing-speak")
@Data
public class ThingSpeakProperties {
    private String apiUrl;
    private String key;
}

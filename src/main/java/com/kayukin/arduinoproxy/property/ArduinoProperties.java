package com.kayukin.arduinoproxy.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConfigurationProperties("arduino")
@Data
public class ArduinoProperties {
    private String serialPort;
    private Duration pollRate;
    private Integer baudRate;
}

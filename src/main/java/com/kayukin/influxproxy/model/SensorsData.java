package com.kayukin.influxproxy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorsData {
    private Double humidity;
    private Double temperature;
}

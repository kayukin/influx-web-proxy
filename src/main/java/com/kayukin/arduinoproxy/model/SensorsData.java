package com.kayukin.arduinoproxy.model;

public class SensorsData {
    private Integer humidity;
    private Integer temperature;

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Integer humidity;
        private Integer temperature;

        private Builder() {
        }

        public Builder withHumidity(Integer humidity) {
            this.humidity = humidity;
            return this;
        }

        public Builder withTemperature(Integer temperature) {
            this.temperature = temperature;
            return this;
        }

        public SensorsData build() {
            SensorsData sensorsData = new SensorsData();
            sensorsData.setHumidity(humidity);
            sensorsData.setTemperature(temperature);
            return sensorsData;
        }
    }
}

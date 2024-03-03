package org.example;

public class WeatherData {
    private double tempC;
    private int humidity;
    private double windspeedKmph;
    private int uvIndex;
    private String weatherDesc;
    private String city;

    public WeatherData() {
    }

    public double getTempC() {
        return tempC;
    }

    public void setTempC(double tempC) {
        this.tempC = tempC;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getWindspeedKmph() {
        return windspeedKmph;
    }

    public void setWindspeedKmph(double windspeedKmph) {
        this.windspeedKmph = windspeedKmph;
    }

    public int getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(int uvIndex) {
        this.uvIndex = uvIndex;
    }

    public String getWeatherDesc() {
        return weatherDesc;
    }

    public void setWeatherDesc(String weatherDesc) {
        this.weatherDesc = weatherDesc;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}


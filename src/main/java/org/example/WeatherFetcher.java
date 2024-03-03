package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherFetcher {
    public WeatherData fetchWeather(String city) {
        try {
            String urlString = "https://wttr.in/" + city + "?format=j1";
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();
            if (status == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                con.disconnect();

                JSONObject obj = new JSONObject(content.toString());
                return parseWeatherData(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private WeatherData parseWeatherData(JSONObject obj) {
        WeatherData data = new WeatherData();
        JSONObject currentCondition = obj.getJSONArray("current_condition").getJSONObject(0);
        data.setTempC(currentCondition.getDouble("temp_C"));
        data.setHumidity(currentCondition.getInt("humidity"));
        data.setWindspeedKmph(currentCondition.getDouble("windspeedKmph"));
        data.setUvIndex(currentCondition.getInt("uvIndex"));
        data.setWeatherDesc(currentCondition.getJSONArray("weatherDesc").getJSONObject(0).getString("value"));
        return data;
    }
}


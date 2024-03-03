package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
public class DBConnection {
    public void insertWeatherData(WeatherData data){
        try {
            Connection connection = connect();
            String insertQuery =  "INSERT INTO" +
                    " weather_data (tempC, humidity, windspeedKmph, " +
                    "uvIndex, weatherDesc, searchTimestamp,city) " +
                    "VALUES (?, ?, ?, ?, ?, ?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setDouble(1, data.getTempC());
            preparedStatement.setInt(2, data.getHumidity());
            preparedStatement.setDouble(3, data.getWindspeedKmph());
            preparedStatement.setInt(4, data.getUvIndex());
            preparedStatement.setString(5, data.getWeatherDesc());
            preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(7, data.getCity());


            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void createTableAndData(){
        try {
            Connection connection = connect();
            String createTableQuery = "CREATE TABLE IF NOT EXISTS weather_data (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "tempC REAL, " +
                    "humidity INTEGER, " +
                    "windspeedKmph REAL, " +
                    "uvIndex INTEGER, " +
                    "weatherDesc TEXT, " +
                    "searchTimestamp DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                    "city TEXT)";
            Statement statement = connection.createStatement();
            statement.executeUpdate(createTableQuery);
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public List<WeatherData> getAllWeatherData(String city) {
        List<WeatherData> dataList = new ArrayList<>();
        String query = "SELECT tempC, humidity,windspeedKmph,uvIndex FROM weather_data WHERE city = '" + city + "'";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                double tempC = rs.getDouble("tempC");
                int humidity = rs.getInt("humidity");
                int windspeedKmph = rs.getInt("windspeedKmph");
                int uvIndex = rs.getInt("uvIndex");
                WeatherData data = new WeatherData();
                data.setTempC(tempC);
                data.setHumidity(humidity);
                data.setWindspeedKmph(windspeedKmph);
                data.setUvIndex(uvIndex);
                dataList.add(data);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dataList;
    }

    public String computeAndPrintStatistics(String city) {
        List<WeatherData> dataList = getAllWeatherData(city);
        if (dataList.isEmpty()) {
            return "No weather data available.";
        }

        double totalTemp = 0;
        int totalHumidity = 0;
        int totalWindspeed = 0;
        int totalUvIndex = 0;
        for (WeatherData data : dataList) {
            totalTemp += data.getTempC();
            totalHumidity += data.getHumidity();
            totalWindspeed += data.getWindspeedKmph();
            totalUvIndex += data.getUvIndex();
        }

        double avgTemp = totalTemp / dataList.size();
        double avgHumidity = (double) totalHumidity / dataList.size();
        double avgWindspeed = (double) totalWindspeed / dataList.size();
        double avgUvIndex = (double) totalUvIndex / dataList.size();

        System.out.println("Average Temperature: " + avgTemp + "°C \n");
        System.out.println("Average Humidity: " + avgHumidity + "%");
        System.out.println("Average Windspeed: " + avgWindspeed + " km/h");
        System.out.println("Average UV Index: " + avgUvIndex);
        return "Average Temperature: " + avgTemp + "°C \n"
                + "Average Humidity: " + avgHumidity + "% \n"
                + "Average Windspeed: " + avgWindspeed + " km/h \n"
                + "Average UV Index: " + avgUvIndex;
    }
    private Connection connect(){
        String connectionString = "jdbc:sqlite:weatherDB.db";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }
}

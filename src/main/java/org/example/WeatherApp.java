package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class WeatherApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("WeatherApp");
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        JTextField cityField = new JTextField();
        JButton fetchButton = new JButton("Fetch Weather");
        JTextArea resultArea = new JTextArea(10, 20);
        resultArea.setEditable(false);

        frame.add(new JLabel("Enter City:"));
        frame.add(cityField);
        frame.add(fetchButton);
        frame.add(new JScrollPane(resultArea));
        JTextField statsCityField = new JTextField();
        JButton showStatsButton = new JButton("Show Statistics");


        frame.add(new JLabel("Enter City for Statistics:"));
        frame.add(statsCityField);
        frame.add(showStatsButton);
        JTextArea resultArea2 = new JTextArea(10, 20);
        resultArea2.setEditable(false);
        frame.add(new JScrollPane(resultArea2));

        DBConnection db = new DBConnection();
        db.createTableAndData();

        fetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city = cityField.getText();

                WeatherFetcher fetcher = new WeatherFetcher();
                WeatherData data = fetcher.fetchWeather(city);
                data.setCity(city);

                if (data != null) {
                    db.insertWeatherData(data);
                    StringBuilder resultText = new StringBuilder();
                    resultText.append("Fetching and saving data for: ").append(city).append("\n");
                    resultText.append("Weather Data Fetched Successfully\n");
                    resultText.append("Temperature: ").append(data.getTempC()).append(" C\n");
                    resultText.append("Humidity: ").append(data.getHumidity()).append("%\n");
                    resultText.append("Wind Speed: ").append(data.getWindspeedKmph()).append(" km/h\n");
                    resultText.append("UV Index: ").append(data.getUvIndex()).append("\n");
                    resultText.append("Weather Description: ").append(data.getWeatherDesc()).append("\n");

                    resultArea.setText(resultText.toString());
                } else {
                    resultArea.setText("Failed to fetch weather data.");
                }
            }
        });
        showStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city = statsCityField.getText();
                String statistics = db.computeAndPrintStatistics(city);
                resultArea2.setText("Statistics for " + city + ":\n" + statistics);
            }
        });

        frame.setVisible(true);

    }
}
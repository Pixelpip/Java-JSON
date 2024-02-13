package org.example;

import static spark.Spark.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Weather {
    private static double temperature; // Declared temperature to be used as a class field
    private  static String formattedTemperature; // class field to limit decimal places of temperature
    public static void main(String[] args) {
        port(8080);

        // Define a route to render the form
        get("/", (req, res) -> {
            return "<form action='/' method='post'>" +
                    "Enter your city name: <input type='text' name='username'>" +
                    "<input type='submit' value='Submit'>" +
                    "</form>";
        });

        // Define a route to handle form submission
        post("/", (req, res) -> {
            String city = req.queryParams("username");
            String apiKey = "d94a1a5aa3c285a26a3d45c0e357d771";
            String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;

            try {
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                connection.disconnect();

                // Parse the JSON response to get the temperature
                String jsonResponse = response.toString();
                temperature = parseTemperature(jsonResponse) - 273.15;
                formattedTemperature = String.format("%.2f", temperature);

                // Return the result
                return "Temperature of " + city + " is " + formattedTemperature + " degrees Celsius";

            } catch (IOException e) {
                e.printStackTrace();
                return "Error fetching temperature information";
            }
        });
    }

    private static double parseTemperature(String jsonResponse) {
        int startIndex = jsonResponse.indexOf("\"temp\":") + 7;
        int endIndex = jsonResponse.indexOf(",", startIndex);
        String temperatureString = jsonResponse.substring(startIndex, endIndex);
        return Double.parseDouble(temperatureString);
    }
}

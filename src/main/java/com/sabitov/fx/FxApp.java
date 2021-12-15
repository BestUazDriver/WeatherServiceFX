package com.sabitov.fx;

import com.sabitov.repos.JsonWeatherParserService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Map;

public class FxApp {
    private final JsonWeatherParserService weatherService = new JsonWeatherParserService();
        @FXML
        private TextField city;
        @FXML
        private Label name;
        @FXML
        private Label temp;
        @FXML
        private Label max_temperature;
        @FXML
        private Label min_temperature;
        @FXML
        private Label feelsLike;
        @FXML
        private Label description;


        @FXML
        protected void onHelloButtonClick() throws IOException {
            Map<String, String> result = weatherService.get(city.getText());
            name.setText("City: " + result.get("name"));
            temp.setText("Temp: " + result.get("temp"));
            max_temperature.setText("Temp max: " + result.get("temp_max"));
            min_temperature.setText("Temp min: " + result.get("temp_min"));
            feelsLike.setText("Feels like: " + result.get("feels_like"));
            description.setText("Description: " + result.get("description"));
        }
}

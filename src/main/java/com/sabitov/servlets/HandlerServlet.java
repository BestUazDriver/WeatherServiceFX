package com.sabitov.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sabitov.repos.JsonWeatherParserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@WebServlet(urlPatterns = "/weatherInfo")
public class HandlerServlet extends HttpServlet {

    private final String keyApi = "61c4a7fe2f5767b8c857034ee7ab4c50";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder buffer = new StringBuilder();

        String line;

        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        String data = buffer.toString();

        Map<String, String> weatherAttributes = null;

        if (!data.isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            weatherAttributes = mapper.readValue(data, Map.class);
        }

        URL getUrl = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + weatherAttributes.get("city") + "&appid=" + keyApi);

        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();

        connection.setRequestMethod("GET");

        StringBuilder content;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            content = new StringBuilder();
            String input;
            while ((input = bufferedReader.readLine()) != null) {
                content.append(input);
            }
        }

        connection.disconnect();

        Map<String, String> jsonApplication = JsonWeatherParserService.parseJson(content.toString());

        String json = new ObjectMapper().writeValueAsString(jsonApplication);

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }
}

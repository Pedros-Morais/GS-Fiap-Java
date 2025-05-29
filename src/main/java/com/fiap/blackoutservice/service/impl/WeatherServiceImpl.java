package com.fiap.blackoutservice.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fiap.blackoutservice.dto.WeatherDTO;
import com.fiap.blackoutservice.service.WeatherService;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final WebClient webClient;
    
    // INMET API Base URL - Note: This is a simulated API endpoint as the actual INMET API details may vary
    private static final String INMET_API_BASE_URL = "https://api.inmet.gov.br/api/v1";
    
    @Autowired
    public WeatherServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(INMET_API_BASE_URL).build();
    }
    
    @Override
    public WeatherDTO getCurrentWeatherByLocation(String location) {
        // In a real implementation, you would call the actual INMET API here
        // This is a simulated implementation for demonstration
        System.out.println("Calling INMET API for current weather in " + location);
        
        // Simulate API call
        try {
            Thread.sleep(500); // Simulate network delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // For demonstration purposes, returning mock data
        return createMockWeatherData(location);
    }
    
    @Override
    public List<WeatherDTO> getWeatherForecast(String location, int days) {
        // Simulate API call to get forecast for multiple days
        System.out.println("Calling INMET API for " + days + " day forecast in " + location);
        
        List<WeatherDTO> forecast = new ArrayList<>();
        for (int i = 0; i < days; i++) {
            WeatherDTO weatherDay = createMockWeatherData(location);
            weatherDay.setTimestamp(LocalDateTime.now().plusDays(i));
            forecast.add(weatherDay);
        }
        
        return forecast;
    }
    
    @Override
    public WeatherDTO getWeatherByCoordinates(Double latitude, Double longitude) {
        // Simulate API call using coordinates
        System.out.println("Calling INMET API for weather at coordinates: " + latitude + ", " + longitude);
        
        // For demonstration purposes, creating mock data
        WeatherDTO weather = new WeatherDTO();
        weather.setLocation("Location at " + latitude + ", " + longitude);
        weather.setTemperature(Math.random() * 10 + 20); // Random temp between 20-30
        weather.setHumidity(Math.random() * 50 + 30); // Random humidity between 30-80%
        weather.setRainfall(Math.random() * 10); // Random rainfall between 0-10mm
        weather.setCondition(getRandomWeatherCondition());
        weather.setTimestamp(LocalDateTime.now());
        weather.setWindSpeed(Math.random() * 20); // Random wind speed between 0-20 km/h
        weather.setWindDirection(getRandomWindDirection());
        
        return weather;
    }
    
    // Helper method to create mock weather data
    private WeatherDTO createMockWeatherData(String location) {
        WeatherDTO weather = new WeatherDTO();
        weather.setLocation(location);
        weather.setTemperature(Math.random() * 10 + 20); // Random temp between 20-30
        weather.setHumidity(Math.random() * 50 + 30); // Random humidity between 30-80%
        weather.setRainfall(Math.random() * 10); // Random rainfall between 0-10mm
        weather.setCondition(getRandomWeatherCondition());
        weather.setTimestamp(LocalDateTime.now());
        weather.setWindSpeed(Math.random() * 20); // Random wind speed between 0-20 km/h
        weather.setWindDirection(getRandomWindDirection());
        
        return weather;
    }
    
    private String getRandomWeatherCondition() {
        String[] conditions = {"Sunny", "Partly Cloudy", "Cloudy", "Rainy", "Thunderstorm", "Clear"};
        return conditions[(int) (Math.random() * conditions.length)];
    }
    
    private String getRandomWindDirection() {
        String[] directions = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
        return directions[(int) (Math.random() * directions.length)];
    }
}

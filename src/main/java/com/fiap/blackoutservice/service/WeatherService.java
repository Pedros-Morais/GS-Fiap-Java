package com.fiap.blackoutservice.service;

import com.fiap.blackoutservice.dto.WeatherDTO;
import java.util.List;

public interface WeatherService {
    WeatherDTO getCurrentWeatherByLocation(String location);
    List<WeatherDTO> getWeatherForecast(String location, int days);
    WeatherDTO getWeatherByCoordinates(Double latitude, Double longitude);
}

package com.fiap.blackoutservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.blackoutservice.dto.WeatherDTO;
import com.fiap.blackoutservice.service.WeatherService;


import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/weather")
@Tag(name = "Weather", description = "Weather data API")
@SecurityRequirement(name = "bearer-jwt")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;
    
    @GetMapping("/{location}")
    public ResponseEntity<WeatherDTO> getCurrentWeatherByLocation(@PathVariable String location) {
        return ResponseEntity.ok(weatherService.getCurrentWeatherByLocation(location));
    }
    
    @GetMapping("/{location}/forecast")
    public ResponseEntity<List<WeatherDTO>> getWeatherForecast(
            @PathVariable String location,
            @RequestParam(defaultValue = "5") int days) {
        return ResponseEntity.ok(weatherService.getWeatherForecast(location, days));
    }
    
    @GetMapping("/coordinates")
    public ResponseEntity<WeatherDTO> getWeatherByCoordinates(
            @RequestParam Double latitude,
            @RequestParam Double longitude) {
        return ResponseEntity.ok(weatherService.getWeatherByCoordinates(latitude, longitude));
    }
}

package com.fiap.blackoutservice.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fiap.blackoutservice.dto.WeatherDTO;
import com.fiap.blackoutservice.service.WeatherService;

@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private WeatherService weatherService;
    
    private WeatherDTO weatherDTO;
    private List<WeatherDTO> forecastList;
    
    @BeforeEach
    public void setup() {
        // Setup test data
        weatherDTO = new WeatherDTO();
        weatherDTO.setLocation("São Paulo");
        weatherDTO.setTemperature(25.5);
        weatherDTO.setHumidity(70.0);
        weatherDTO.setRainfall(0.0);
        weatherDTO.setCondition("Sunny");
        weatherDTO.setTimestamp(LocalDateTime.now());
        weatherDTO.setWindSpeed(10.0);
        weatherDTO.setWindDirection("N");
        
        forecastList = Arrays.asList(weatherDTO);
    }
    
    @Test
    public void testGetCurrentWeatherByLocation() throws Exception {
        // Arrange
        when(weatherService.getCurrentWeatherByLocation("São Paulo")).thenReturn(weatherDTO);
        
        // Act & Assert
        mockMvc.perform(get("/weather/São Paulo")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location", is("São Paulo")))
                .andExpect(jsonPath("$.condition", is("Sunny")));
    }
    
    @Test
    public void testGetWeatherForecast() throws Exception {
        // Arrange
        when(weatherService.getWeatherForecast("São Paulo", 5)).thenReturn(forecastList);
        
        // Act & Assert
        mockMvc.perform(get("/weather/São Paulo/forecast?days=5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].location", is("São Paulo")))
                .andExpect(jsonPath("$[0].condition", is("Sunny")));
    }
    
    @Test
    public void testGetWeatherByCoordinates() throws Exception {
        // Arrange
        when(weatherService.getWeatherByCoordinates(-23.5505, -46.6333)).thenReturn(weatherDTO);
        
        // Act & Assert
        mockMvc.perform(get("/weather/coordinates?latitude=-23.5505&longitude=-46.6333")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location", is("São Paulo")))
                .andExpect(jsonPath("$.condition", is("Sunny")));
    }
}

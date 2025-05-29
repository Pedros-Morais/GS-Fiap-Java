package com.fiap.blackoutservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.fiap.blackoutservice.dto.WeatherDTO;
import com.fiap.blackoutservice.service.impl.WeatherServiceImpl;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

    @Mock
    private WebClient.Builder webClientBuilder;
    
    @Mock
    private WebClient webClient;
    
    @InjectMocks
    private WeatherServiceImpl weatherService;
    
    @BeforeEach
    public void setup() {
        // Mock the WebClient.Builder and WebClient behavior
        org.mockito.Mockito.when(webClientBuilder.baseUrl(org.mockito.ArgumentMatchers.anyString()))
            .thenReturn(webClientBuilder);
        org.mockito.Mockito.when(webClientBuilder.build()).thenReturn(webClient);
    }
    
    @Test
    public void testGetCurrentWeatherByLocation() {
        // Act
        WeatherDTO result = weatherService.getCurrentWeatherByLocation("São Paulo");
        
        // Assert - using the mock implementation in our service
        assertNotNull(result);
        assertEquals("São Paulo", result.getLocation());
        assertNotNull(result.getTemperature());
        assertNotNull(result.getHumidity());
        assertNotNull(result.getRainfall());
        assertNotNull(result.getCondition());
        assertNotNull(result.getTimestamp());
    }
    
    @Test
    public void testGetWeatherForecast() {
        // Act
        List<WeatherDTO> result = weatherService.getWeatherForecast("São Paulo", 3);
        
        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        
        // Verify each forecast day
        for (WeatherDTO weather : result) {
            assertEquals("São Paulo", weather.getLocation());
            assertNotNull(weather.getTemperature());
            assertNotNull(weather.getHumidity());
            assertNotNull(weather.getRainfall());
            assertNotNull(weather.getCondition());
            assertNotNull(weather.getTimestamp());
        }
    }
    
    @Test
    public void testGetWeatherByCoordinates() {
        // Act
        WeatherDTO result = weatherService.getWeatherByCoordinates(-23.5505, -46.6333);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.getLocation().contains("-23.5505"));
        assertTrue(result.getLocation().contains("-46.6333"));
        assertNotNull(result.getTemperature());
        assertNotNull(result.getHumidity());
        assertNotNull(result.getRainfall());
        assertNotNull(result.getCondition());
        assertNotNull(result.getTimestamp());
    }
}

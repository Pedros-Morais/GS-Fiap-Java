package com.fiap.blackoutservice.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDTO {
    private String location;
    private Double temperature;
    private Double humidity;
    private Double rainfall;
    private String condition;
    private LocalDateTime timestamp;
    private Double windSpeed;
    private String windDirection;
}

package com.fiap.blackoutservice.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlackoutDTO {
    
    @Schema(description = "Unique identifier of the blackout", example = "1")
    private Long id;
    
    @NotBlank(message = "Location is required")
    @Size(min = 3, max = 100, message = "Location must be between 3 and 100 characters")
    @Schema(description = "Location of the blackout", example = "São Paulo Downtown")
    private String location;
    
    @NotNull(message = "Start time is required")
    @Schema(description = "Start time of the blackout", example = "2025-05-29T15:00:00")
    private LocalDateTime startTime;
    
    @Schema(description = "End time of the blackout (null if still active)", example = "2025-05-29T18:00:00")
    private LocalDateTime endTime;
    
    @NotBlank(message = "Status is required")
    @Pattern(regexp = "ACTIVE|RESOLVED|SCHEDULED", message = "Status must be ACTIVE, RESOLVED, or SCHEDULED")
    @Schema(description = "Status of the blackout", example = "ACTIVE", allowableValues = {"ACTIVE", "RESOLVED", "SCHEDULED"})
    private String status;
    
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @Schema(description = "Description of the blackout", example = "Major power outage affecting downtown area")
    private String description;
    
    @Schema(description = "ID of the user who reported the blackout", example = "1")
    private Long reportedById;
    
    @Schema(description = "Latitude coordinate of the blackout location", example = "-23.5505")
    private Double latitude;
    
    @Schema(description = "Longitude coordinate of the blackout location", example = "-46.6333")
    private Double longitude;
    
    @Schema(description = "Whether the blackout has been verified by authorities", example = "false")
    private Boolean verified;
}

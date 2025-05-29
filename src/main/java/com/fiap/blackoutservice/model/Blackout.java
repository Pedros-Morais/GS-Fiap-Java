package com.fiap.blackoutservice.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blackout {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status; // ACTIVE, RESOLVED, SCHEDULED
    private String description;
    
    @ManyToOne
    private User reportedBy;
    
    private Double latitude;
    private Double longitude;
    private Boolean verified = false;
}

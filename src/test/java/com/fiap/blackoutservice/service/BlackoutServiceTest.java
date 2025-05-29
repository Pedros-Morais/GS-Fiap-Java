package com.fiap.blackoutservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fiap.blackoutservice.dto.BlackoutDTO;
import com.fiap.blackoutservice.exception.ResourceNotFoundException;
import com.fiap.blackoutservice.model.Blackout;
import com.fiap.blackoutservice.model.User;
import com.fiap.blackoutservice.repository.BlackoutRepository;
import com.fiap.blackoutservice.repository.UserRepository;
import com.fiap.blackoutservice.service.impl.BlackoutServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BlackoutServiceTest {

    @Mock
    private BlackoutRepository blackoutRepository;
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private BlackoutServiceImpl blackoutService;
    
    private Blackout blackout;
    private User user;
    private BlackoutDTO blackoutDTO;
    
    @BeforeEach
    public void setup() {
        // Setup test data
        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setRewardPoints(0);
        
        blackout = new Blackout();
        blackout.setId(1L);
        blackout.setLocation("São Paulo");
        blackout.setStartTime(LocalDateTime.now());
        blackout.setStatus("ACTIVE");
        blackout.setDescription("Major power outage");
        blackout.setReportedBy(user);
        blackout.setLatitude(-23.5505);
        blackout.setLongitude(-46.6333);
        blackout.setVerified(false);
        
        blackoutDTO = new BlackoutDTO();
        blackoutDTO.setId(1L);
        blackoutDTO.setLocation("São Paulo");
        blackoutDTO.setStartTime(LocalDateTime.now());
        blackoutDTO.setStatus("ACTIVE");
        blackoutDTO.setDescription("Major power outage");
        blackoutDTO.setReportedById(1L);
        blackoutDTO.setLatitude(-23.5505);
        blackoutDTO.setLongitude(-46.6333);
        blackoutDTO.setVerified(false);
    }
    
    @Test
    public void testGetAllBlackouts() {
        // Arrange
        when(blackoutRepository.findAll()).thenReturn(Arrays.asList(blackout));
        
        // Act
        List<BlackoutDTO> result = blackoutService.getAllBlackouts();
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(blackout.getId(), result.get(0).getId());
        assertEquals(blackout.getLocation(), result.get(0).getLocation());
    }
    
    @Test
    public void testGetBlackoutById_Success() {
        // Arrange
        when(blackoutRepository.findById(1L)).thenReturn(Optional.of(blackout));
        
        // Act
        BlackoutDTO result = blackoutService.getBlackoutById(1L);
        
        // Assert
        assertNotNull(result);
        assertEquals(blackout.getId(), result.getId());
        assertEquals(blackout.getLocation(), result.getLocation());
    }
    
    @Test
    public void testGetBlackoutById_NotFound() {
        // Arrange
        when(blackoutRepository.findById(99L)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            blackoutService.getBlackoutById(99L);
        });
    }
    
    @Test
    public void testCreateBlackout() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(blackoutRepository.save(any(Blackout.class))).thenReturn(blackout);
        
        // Act
        BlackoutDTO result = blackoutService.createBlackout(blackoutDTO);
        
        // Assert
        assertNotNull(result);
        assertEquals(blackout.getId(), result.getId());
        assertEquals(blackout.getLocation(), result.getLocation());
        
        // Verify user reward points update
        verify(userRepository, times(1)).save(any(User.class));
    }
    
    @Test
    public void testUpdateBlackout() {
        // Arrange
        when(blackoutRepository.findById(1L)).thenReturn(Optional.of(blackout));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(blackoutRepository.save(any(Blackout.class))).thenReturn(blackout);
        
        blackoutDTO.setStatus("RESOLVED");
        blackoutDTO.setEndTime(LocalDateTime.now());
        
        // Act
        BlackoutDTO result = blackoutService.updateBlackout(1L, blackoutDTO);
        
        // Assert
        assertNotNull(result);
        assertEquals(blackoutDTO.getStatus(), result.getStatus());
    }
    
    @Test
    public void testDeleteBlackout() {
        // Arrange
        when(blackoutRepository.existsById(1L)).thenReturn(true);
        
        // Act
        blackoutService.deleteBlackout(1L);
        
        // Assert
        verify(blackoutRepository, times(1)).deleteById(1L);
    }
    
    @Test
    public void testDeleteBlackout_NotFound() {
        // Arrange
        when(blackoutRepository.existsById(99L)).thenReturn(false);
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            blackoutService.deleteBlackout(99L);
        });
    }
}

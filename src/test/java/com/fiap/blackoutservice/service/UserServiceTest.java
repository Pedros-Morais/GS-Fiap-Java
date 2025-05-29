package com.fiap.blackoutservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fiap.blackoutservice.dto.UserDTO;
import com.fiap.blackoutservice.exception.ResourceNotFoundException;
import com.fiap.blackoutservice.model.User;
import com.fiap.blackoutservice.repository.UserRepository;
import com.fiap.blackoutservice.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserServiceImpl userService;
    
    private User user;
    private UserDTO userDTO;
    
    @BeforeEach
    public void setup() {
        // Setup test data
        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setRewardPoints(50);
        
        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("Test User");
        userDTO.setEmail("test@example.com");
        userDTO.setRewardPoints(50);
    }
    
    @Test
    public void testGetAllUsers() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        
        // Act
        List<UserDTO> result = userService.getAllUsers();
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(user.getId(), result.get(0).getId());
        assertEquals(user.getName(), result.get(0).getName());
        assertEquals(user.getEmail(), result.get(0).getEmail());
    }
    
    @Test
    public void testGetUserById_Success() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        
        // Act
        UserDTO result = userService.getUserById(1L);
        
        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
    }
    
    @Test
    public void testGetUserById_NotFound() {
        // Arrange
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserById(99L);
        });
    }
    
    @Test
    public void testCreateUser() {
        // Arrange
        when(userRepository.save(any(User.class))).thenReturn(user);
        
        // Act
        UserDTO result = userService.createUser(userDTO);
        
        // Assert
        assertNotNull(result);
        assertEquals(userDTO.getId(), result.getId());
        assertEquals(userDTO.getName(), result.getName());
        assertEquals(userDTO.getEmail(), result.getEmail());
        assertEquals(userDTO.getRewardPoints(), result.getRewardPoints());
    }
    
    @Test
    public void testUpdateUser() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        
        userDTO.setName("Updated Name");
        userDTO.setRewardPoints(100);
        
        // Act
        UserDTO result = userService.updateUser(1L, userDTO);
        
        // Assert
        assertNotNull(result);
        assertEquals(userDTO.getName(), result.getName());
        assertEquals(userDTO.getRewardPoints(), result.getRewardPoints());
    }
    
    @Test
    public void testDeleteUser() {
        // Arrange
        when(userRepository.existsById(1L)).thenReturn(true);
        
        // Act
        userService.deleteUser(1L);
        
        // Assert
        verify(userRepository, times(1)).deleteById(1L);
    }
    
    @Test
    public void testDeleteUser_NotFound() {
        // Arrange
        when(userRepository.existsById(99L)).thenReturn(false);
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.deleteUser(99L);
        });
    }
    
    @Test
    public void testGetUserRewards() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        
        // Act
        UserDTO result = userService.getUserRewards(1L);
        
        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getRewardPoints(), result.getRewardPoints());
    }
}

package com.fiap.blackoutservice.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.blackoutservice.dto.UserDTO;
import com.fiap.blackoutservice.service.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;
    
    private UserDTO userDTO;
    private List<UserDTO> userList;
    private ObjectMapper objectMapper;
    
    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        
        // Setup test data
        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("Test User");
        userDTO.setEmail("test@example.com");
        userDTO.setRewardPoints(50);
        
        userList = Arrays.asList(userDTO);
    }
    
    @Test
    public void testGetAllUsers() throws Exception {
        // Arrange
        when(userService.getAllUsers()).thenReturn(userList);
        
        // Act & Assert
        mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Test User")))
                .andExpect(jsonPath("$[0].email", is("test@example.com")))
                .andExpect(jsonPath("$[0].rewardPoints", is(50)));
    }
    
    @Test
    public void testGetUserById() throws Exception {
        // Arrange
        when(userService.getUserById(1L)).thenReturn(userDTO);
        
        // Act & Assert
        mockMvc.perform(get("/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Test User")))
                .andExpect(jsonPath("$.email", is("test@example.com")))
                .andExpect(jsonPath("$.rewardPoints", is(50)));
    }
    
    @Test
    public void testGetUserRewards() throws Exception {
        // Arrange
        when(userService.getUserRewards(1L)).thenReturn(userDTO);
        
        // Act & Assert
        mockMvc.perform(get("/users/1/rewards")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Test User")))
                .andExpect(jsonPath("$.rewardPoints", is(50)));
    }
    
    @Test
    public void testCreateUser() throws Exception {
        // Arrange
        when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);
        
        // Act & Assert
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Test User")))
                .andExpect(jsonPath("$.email", is("test@example.com")))
                .andExpect(jsonPath("$.rewardPoints", is(50)));
    }
    
    @Test
    public void testUpdateUser() throws Exception {
        // Arrange
        UserDTO updatedUser = userDTO;
        updatedUser.setName("Updated Name");
        updatedUser.setRewardPoints(100);
        
        when(userService.updateUser(eq(1L), any(UserDTO.class))).thenReturn(updatedUser);
        
        // Act & Assert
        mockMvc.perform(put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Updated Name")))
                .andExpect(jsonPath("$.rewardPoints", is(100)));
    }
    
    @Test
    public void testDeleteUser() throws Exception {
        // Arrange
        doNothing().when(userService).deleteUser(1L);
        
        // Act & Assert
        mockMvc.perform(delete("/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}

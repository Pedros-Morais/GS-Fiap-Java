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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fiap.blackoutservice.dto.BlackoutDTO;
import com.fiap.blackoutservice.service.BlackoutService;

@WebMvcTest(BlackoutController.class)
public class BlackoutControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private BlackoutService blackoutService;
    
    private BlackoutDTO blackoutDTO;
    private List<BlackoutDTO> blackoutList;
    private ObjectMapper objectMapper;
    
    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        
        LocalDateTime now = LocalDateTime.now();
        
        // Setup test data
        blackoutDTO = new BlackoutDTO();
        blackoutDTO.setId(1L);
        blackoutDTO.setLocation("São Paulo");
        blackoutDTO.setStartTime(now);
        blackoutDTO.setStatus("ACTIVE");
        blackoutDTO.setDescription("Major power outage");
        blackoutDTO.setReportedById(1L);
        blackoutDTO.setLatitude(-23.5505);
        blackoutDTO.setLongitude(-46.6333);
        blackoutDTO.setVerified(false);
        
        blackoutList = Arrays.asList(blackoutDTO);
    }
    
    @Test
    public void testGetAllBlackouts() throws Exception {
        // Arrange
        when(blackoutService.getAllBlackouts()).thenReturn(blackoutList);
        
        // Act & Assert
        mockMvc.perform(get("/blackouts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].location", is("São Paulo")))
                .andExpect(jsonPath("$[0].status", is("ACTIVE")));
    }
    
    @Test
    public void testGetBlackoutById() throws Exception {
        // Arrange
        when(blackoutService.getBlackoutById(1L)).thenReturn(blackoutDTO);
        
        // Act & Assert
        mockMvc.perform(get("/blackouts/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.location", is("São Paulo")))
                .andExpect(jsonPath("$.status", is("ACTIVE")));
    }
    
    @Test
    public void testCreateBlackout() throws Exception {
        // Arrange
        when(blackoutService.createBlackout(any(BlackoutDTO.class))).thenReturn(blackoutDTO);
        
        // Act & Assert
        mockMvc.perform(post("/blackouts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(blackoutDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.location", is("São Paulo")))
                .andExpect(jsonPath("$.status", is("ACTIVE")));
    }
    
    @Test
    public void testUpdateBlackout() throws Exception {
        // Arrange
        BlackoutDTO updatedBlackout = blackoutDTO;
        updatedBlackout.setStatus("RESOLVED");
        
        when(blackoutService.updateBlackout(eq(1L), any(BlackoutDTO.class))).thenReturn(updatedBlackout);
        
        // Act & Assert
        mockMvc.perform(put("/blackouts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBlackout)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.location", is("São Paulo")))
                .andExpect(jsonPath("$.status", is("RESOLVED")));
    }
    
    @Test
    public void testDeleteBlackout() throws Exception {
        // Arrange
        doNothing().when(blackoutService).deleteBlackout(1L);
        
        // Act & Assert
        mockMvc.perform(delete("/blackouts/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}

package com.fiap.blackoutservice.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fiap.blackoutservice.soap.client.PowerOutageSOAPClient;

@WebMvcTest(SOAPClientController.class)
public class SOAPClientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private PowerOutageSOAPClient soapClient;
    
    @Test
    public void testGetPowerOutageStatus() throws Exception {
        // Arrange
        when(soapClient.getPowerOutageStatus("SP001")).thenReturn("ACTIVE");
        
        // Act & Assert
        mockMvc.perform(get("/soap-client/outage-status/SP001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("ACTIVE"));
    }
    
    @Test
    public void testReportPowerOutage() throws Exception {
        // Arrange
        when(soapClient.reportPowerOutage("SP003", "New outage", 1000)).thenReturn(true);
        
        // Act & Assert
        mockMvc.perform(post("/soap-client/report-outage")
                .param("locationCode", "SP003")
                .param("description", "New outage")
                .param("affectedCustomers", "1000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
    
    @Test
    public void testGetEstimatedResolutionTime() throws Exception {
        // Arrange
        when(soapClient.getEstimatedResolutionTime("OUT123")).thenReturn("2025-05-29 21:00:00");
        
        // Act & Assert
        mockMvc.perform(get("/soap-client/resolution-time/OUT123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("2025-05-29 21:00:00"));
    }
}

package com.fiap.blackoutservice.soap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PowerOutageSOAPServiceTest {

    private PowerOutageSOAPService soapService;
    
    @BeforeEach
    public void setup() {
        soapService = new PowerOutageSOAPServiceImpl();
    }
    
    @Test
    public void testGetPowerOutageStatus_Active() {
        // Act
        String status = soapService.getPowerOutageStatus("SP001");
        
        // Assert
        assertEquals("ACTIVE", status);
    }
    
    @Test
    public void testGetPowerOutageStatus_Resolved() {
        // Act
        String status = soapService.getPowerOutageStatus("SP002");
        
        // Assert
        assertEquals("RESOLVED", status);
    }
    
    @Test
    public void testGetPowerOutageStatus_NoOutage() {
        // Act
        String status = soapService.getPowerOutageStatus("UNKNOWN");
        
        // Assert
        assertEquals("NO_OUTAGE", status);
    }
    
    @Test
    public void testReportPowerOutage_Success() {
        // Act
        boolean result = soapService.reportPowerOutage("RJ002", "New power outage", 5000);
        
        // Assert
        assertTrue(result);
        
        // Verify the outage was recorded
        String status = soapService.getPowerOutageStatus("RJ002");
        assertEquals("ACTIVE", status);
    }
    
    @Test
    public void testReportPowerOutage_Failure() {
        // Act
        boolean result = soapService.reportPowerOutage("", "Invalid location", 1000);
        
        // Assert
        assertFalse(result);
    }
    
    @Test
    public void testGetEstimatedResolutionTime_Known() {
        // Act
        String time = soapService.getEstimatedResolutionTime("OUT123");
        
        // Assert
        assertNotEquals("UNKNOWN", time);
        assertTrue(time.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"));
    }
    
    @Test
    public void testGetEstimatedResolutionTime_Unknown() {
        // Act
        String time = soapService.getEstimatedResolutionTime("UNKNOWN_ID");
        
        // Assert
        assertEquals("UNKNOWN", time);
    }
}

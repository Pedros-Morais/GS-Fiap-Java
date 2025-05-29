package com.fiap.blackoutservice.soap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.jws.WebService;

@Service
@WebService(
    serviceName = "PowerOutageService",
    portName = "PowerOutagePort",
    targetNamespace = "http://soap.blackoutservice.fiap.com/",
    endpointInterface = "com.fiap.blackoutservice.soap.PowerOutageSOAPService"
)
public class PowerOutageSOAPServiceImpl implements PowerOutageSOAPService {

    private static final Map<String, String> OUTAGE_STATUS = new HashMap<>();
    private static final Map<String, LocalDateTime> RESOLUTION_TIMES = new HashMap<>();
    
    static {
        // Initialize with some mock data
        OUTAGE_STATUS.put("SP001", "ACTIVE");
        OUTAGE_STATUS.put("SP002", "RESOLVED");
        OUTAGE_STATUS.put("RJ001", "SCHEDULED");
        
        RESOLUTION_TIMES.put("OUT123", LocalDateTime.now().plusHours(3));
        RESOLUTION_TIMES.put("OUT456", LocalDateTime.now().plusHours(5));
    }

    @Override
    public String getPowerOutageStatus(String locationCode) {
        // Simulate looking up power outage status for a location
        if (OUTAGE_STATUS.containsKey(locationCode)) {
            return OUTAGE_STATUS.get(locationCode);
        }
        return "NO_OUTAGE";
    }

    @Override
    public boolean reportPowerOutage(String locationCode, String description, int affectedCustomers) {
        // Simulate reporting a power outage
        if (locationCode == null || locationCode.isEmpty()) {
            return false;
        }
        
        // Add the outage to our mock database
        OUTAGE_STATUS.put(locationCode, "ACTIVE");
        
        // Generate a random resolution time between 1 and 8 hours from now
        LocalDateTime estimatedResolution = LocalDateTime.now().plusHours((long) (1 + Math.random() * 7));
        String outageId = "OUT" + UUID.randomUUID().toString().substring(0, 6);
        RESOLUTION_TIMES.put(outageId, estimatedResolution);
        
        System.out.println("SOAP Service: Reported outage at " + locationCode + 
                           " affecting " + affectedCustomers + " customers. " +
                           "Outage ID: " + outageId);
        
        return true;
    }

    @Override
    public String getEstimatedResolutionTime(String outageId) {
        // Simulate getting the estimated resolution time for an outage
        if (RESOLUTION_TIMES.containsKey(outageId)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return RESOLUTION_TIMES.get(outageId).format(formatter);
        }
        return "UNKNOWN";
    }
}

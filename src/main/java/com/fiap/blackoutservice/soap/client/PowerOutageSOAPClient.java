package com.fiap.blackoutservice.soap.client;

import org.springframework.stereotype.Service;

import jakarta.xml.ws.WebServiceRef;

import com.fiap.blackoutservice.soap.PowerOutageSOAPService;

/**
 * This class demonstrates how to consume the SOAP service.
 * In a real application, this would be in a separate project.
 */
@Service
public class PowerOutageSOAPClient {

    // In a real application, you would inject the service using JAXWS
    // @WebServiceRef(wsdlLocation = "http://localhost:8080/ws/poweroutage?wsdl")
    // private PowerOutageSOAPService service;
    
    /**
     * Simulates getting the power outage status from the SOAP service
     */
    public String getPowerOutageStatus(String locationCode) {
        System.out.println("SOAP Client: Getting power outage status for location " + locationCode);
        
        // In a real client, you would use the injected service
        // return service.getPowerOutageStatus(locationCode);
        
        // For demonstration, we're simulating the service call
        if ("SP001".equals(locationCode)) {
            return "ACTIVE";
        } else if ("SP002".equals(locationCode)) {
            return "RESOLVED";
        }
        return "NO_OUTAGE";
    }
    
    /**
     * Simulates reporting a power outage via the SOAP service
     */
    public boolean reportPowerOutage(String locationCode, String description, int affectedCustomers) {
        System.out.println("SOAP Client: Reporting power outage at " + locationCode + 
                           " affecting " + affectedCustomers + " customers");
        
        // In a real client, you would use the injected service
        // return service.reportPowerOutage(locationCode, description, affectedCustomers);
        
        // For demonstration, we're simulating the service call
        return locationCode != null && !locationCode.isEmpty();
    }
    
    /**
     * Simulates getting the estimated resolution time for an outage
     */
    public String getEstimatedResolutionTime(String outageId) {
        System.out.println("SOAP Client: Getting estimated resolution time for outage " + outageId);
        
        // In a real client, you would use the injected service
        // return service.getEstimatedResolutionTime(outageId);
        
        // For demonstration, we're simulating the service call
        if ("OUT123".equals(outageId)) {
            return "2025-05-29 21:00:00";
        } else if ("OUT456".equals(outageId)) {
            return "2025-05-29 23:00:00";
        }
        return "UNKNOWN";
    }
}

package com.fiap.blackoutservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.blackoutservice.soap.client.PowerOutageSOAPClient;

/**
 * Controller that demonstrates how to expose SOAP service functionality through a REST API
 */
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/soap-client")
@Tag(name = "SOAP Client", description = "SOAP client integration API")
@SecurityRequirement(name = "bearer-jwt")
public class SOAPClientController {

    @Autowired
    private PowerOutageSOAPClient soapClient;
    
    @GetMapping("/outage-status/{locationCode}")
    public ResponseEntity<String> getPowerOutageStatus(@PathVariable String locationCode) {
        String status = soapClient.getPowerOutageStatus(locationCode);
        return ResponseEntity.ok(status);
    }
    
    @PostMapping("/report-outage")
    public ResponseEntity<Boolean> reportPowerOutage(
            @RequestParam String locationCode,
            @RequestParam String description,
            @RequestParam int affectedCustomers) {
        boolean result = soapClient.reportPowerOutage(locationCode, description, affectedCustomers);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/resolution-time/{outageId}")
    public ResponseEntity<String> getEstimatedResolutionTime(@PathVariable String outageId) {
        String resolutionTime = soapClient.getEstimatedResolutionTime(outageId);
        return ResponseEntity.ok(resolutionTime);
    }
}

package com.fiap.blackoutservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.blackoutservice.dto.BlackoutDTO;
import com.fiap.blackoutservice.service.BlackoutService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/blackouts")
@Tag(name = "Blackouts", description = "Blackout management API")
public class BlackoutController {

    @Autowired
    private BlackoutService blackoutService;
    
    @Operation(summary = "Get all blackouts", description = "Returns a list of all blackouts in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = BlackoutDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<BlackoutDTO>> getAllBlackouts() {
        return ResponseEntity.ok(blackoutService.getAllBlackouts());
    }
    
    @Operation(summary = "Get a blackout by ID", description = "Returns a blackout as per the id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved blackout",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = BlackoutDTO.class))),
        @ApiResponse(responseCode = "404", description = "Blackout not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BlackoutDTO> getBlackoutById(
            @Parameter(description = "ID of the blackout to be obtained") @PathVariable Long id) {
        return ResponseEntity.ok(blackoutService.getBlackoutById(id));
    }
    
    @Operation(summary = "Create a new blackout", description = "Creates a new blackout and returns the created entity")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Blackout created successfully",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = BlackoutDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<BlackoutDTO> createBlackout(
            @Parameter(description = "Blackout to add") @RequestBody BlackoutDTO blackoutDTO) {
        return new ResponseEntity<>(blackoutService.createBlackout(blackoutDTO), HttpStatus.CREATED);
    }
    
    @Operation(summary = "Update a blackout", description = "Updates an existing blackout by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Blackout updated successfully",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = BlackoutDTO.class))),
        @ApiResponse(responseCode = "404", description = "Blackout not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BlackoutDTO> updateBlackout(
            @Parameter(description = "ID of the blackout to be updated") @PathVariable Long id, 
            @Parameter(description = "Updated blackout information") @RequestBody BlackoutDTO blackoutDTO) {
        return ResponseEntity.ok(blackoutService.updateBlackout(id, blackoutDTO));
    }
    
    @Operation(summary = "Delete a blackout", description = "Deletes a blackout by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Blackout deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Blackout not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlackout(
            @Parameter(description = "ID of the blackout to be deleted") @PathVariable Long id) {
        blackoutService.deleteBlackout(id);
        return ResponseEntity.noContent().build();
    }
}

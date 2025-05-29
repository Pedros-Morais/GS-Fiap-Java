package com.fiap.blackoutservice.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.fiap.blackoutservice.dto.BlackoutDTO;
import com.fiap.blackoutservice.dto.UserDTO;
import com.fiap.blackoutservice.model.Blackout;
import com.fiap.blackoutservice.model.User;
import com.fiap.blackoutservice.repository.BlackoutRepository;
import com.fiap.blackoutservice.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BlackoutServiceIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BlackoutRepository blackoutRepository;
    
    @Test
    public void testEndToEndBlackoutReporting() {
        // 1. Create a test user
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Integration Test User");
        userDTO.setEmail("integration@test.com");
        userDTO.setRewardPoints(0);
        
        ResponseEntity<UserDTO> createUserResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/users", 
                userDTO, 
                UserDTO.class);
        
        assertEquals(HttpStatus.CREATED, createUserResponse.getStatusCode());
        UserDTO createdUser = createUserResponse.getBody();
        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        
        // 2. Report a blackout using the created user
        BlackoutDTO blackoutDTO = new BlackoutDTO();
        blackoutDTO.setLocation("Integration Test Location");
        blackoutDTO.setStartTime(LocalDateTime.now());
        blackoutDTO.setStatus("ACTIVE");
        blackoutDTO.setDescription("Integration test blackout");
        blackoutDTO.setReportedById(createdUser.getId());
        blackoutDTO.setLatitude(-23.5505);
        blackoutDTO.setLongitude(-46.6333);
        blackoutDTO.setVerified(false);
        
        ResponseEntity<BlackoutDTO> createBlackoutResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/blackouts", 
                blackoutDTO, 
                BlackoutDTO.class);
        
        assertEquals(HttpStatus.CREATED, createBlackoutResponse.getStatusCode());
        BlackoutDTO createdBlackout = createBlackoutResponse.getBody();
        assertNotNull(createdBlackout);
        assertNotNull(createdBlackout.getId());
        
        // 3. Check that the user's reward points were updated
        ResponseEntity<UserDTO> getUserResponse = restTemplate.getForEntity(
                "http://localhost:" + port + "/users/" + createdUser.getId() + "/rewards", 
                UserDTO.class);
        
        assertEquals(HttpStatus.OK, getUserResponse.getStatusCode());
        UserDTO updatedUser = getUserResponse.getBody();
        assertNotNull(updatedUser);
        assertTrue(updatedUser.getRewardPoints() > 0, "User should receive reward points for reporting a blackout");
        
        // 4. Update the blackout status to RESOLVED
        createdBlackout.setStatus("RESOLVED");
        createdBlackout.setEndTime(LocalDateTime.now());
        
        HttpEntity<BlackoutDTO> requestEntity = new HttpEntity<>(createdBlackout);
        ResponseEntity<BlackoutDTO> updateResponse = restTemplate.exchange(
                "http://localhost:" + port + "/blackouts/" + createdBlackout.getId(),
                HttpMethod.PUT,
                requestEntity,
                BlackoutDTO.class);
        
        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        BlackoutDTO updatedBlackout = updateResponse.getBody();
        assertNotNull(updatedBlackout);
        assertEquals("RESOLVED", updatedBlackout.getStatus());
        
        // 5. Clean up test data
        // Note: In a real scenario, you'd typically use @Transactional or clean up in @After,
        // but for this example we're doing it explicitly
        restTemplate.delete("http://localhost:" + port + "/blackouts/" + createdBlackout.getId());
        restTemplate.delete("http://localhost:" + port + "/users/" + createdUser.getId());
    }
}

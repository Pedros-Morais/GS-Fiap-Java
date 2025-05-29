package com.fiap.blackoutservice;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.fiap.blackoutservice.controller.BlackoutController;
import com.fiap.blackoutservice.controller.UserController;
import com.fiap.blackoutservice.controller.WeatherController;
import com.fiap.blackoutservice.repository.BlackoutRepository;
import com.fiap.blackoutservice.repository.UserRepository;
import com.fiap.blackoutservice.service.BlackoutService;
import com.fiap.blackoutservice.service.UserService;
import com.fiap.blackoutservice.service.WeatherService;
import com.fiap.blackoutservice.soap.PowerOutageSOAPService;
import com.fiap.blackoutservice.soap.client.PowerOutageSOAPClient;

@SpringBootTest
class BlackoutserviceApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;
    
    @Autowired
    private BlackoutController blackoutController;
    
    @Autowired
    private UserController userController;
    
    @Autowired
    private WeatherController weatherController;
    
    @Autowired
    private BlackoutService blackoutService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private WeatherService weatherService;
    
    @Autowired
    private BlackoutRepository blackoutRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PowerOutageSOAPClient soapClient;
    
    @Test
    void contextLoads() {
        assertNotNull(applicationContext);
    }
    
    @Test
    void controllersLoad() {
        assertNotNull(blackoutController);
        assertNotNull(userController);
        assertNotNull(weatherController);
    }
    
    @Test
    void servicesLoad() {
        assertNotNull(blackoutService);
        assertNotNull(userService);
        assertNotNull(weatherService);
    }
    
    @Test
    void repositoriesLoad() {
        assertNotNull(blackoutRepository);
        assertNotNull(userRepository);
    }
    
    @Test
    void soapComponentsLoad() {
        assertNotNull(soapClient);
        // The PowerOutageSOAPService bean is created dynamically at runtime
        // so we don't inject and test it directly here
    }
}

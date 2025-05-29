package com.fiap.blackoutservice.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Test configuration class that provides test-specific beans
 */
@TestConfiguration
@Profile("test")
public class TestConfig {

    /**
     * Provides a WebClient builder for tests
     * This bean will be used in test environments instead of the real one
     */
    @Bean
    @Primary
    public WebClient.Builder testWebClientBuilder() {
        return WebClient.builder();
    }
}

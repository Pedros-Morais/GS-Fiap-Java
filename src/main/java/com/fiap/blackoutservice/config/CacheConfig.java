package com.fiap.blackoutservice.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
    // Cache configuration is managed through application.properties
    // Additional cache customization can be added here if needed
}

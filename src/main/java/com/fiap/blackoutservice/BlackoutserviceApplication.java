package com.fiap.blackoutservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableCaching
@OpenAPIDefinition(info = @Info(
    title = "Blackout Service API",
    version = "1.0",
    description = "API for managing power outages and related services"
))
public class BlackoutserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlackoutserviceApplication.class, args);
	}

}

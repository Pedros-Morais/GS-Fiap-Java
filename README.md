# Blackout Service - Project Luzia

A comprehensive Spring Boot application that provides REST and SOAP APIs for tracking and managing power outages with advanced enterprise features.

## Key Features

### Core Functionality
- REST APIs for blackout management
- User reward system for reporting blackouts
- Integration with external weather API (INMET)
- SOAP service for power outage management

### Advanced Enterprise Features
- **Security**: JWT authentication and authorization
- **API Documentation**: Swagger/OpenAPI with detailed descriptions
- **Data Validation**: Bean Validation for request validation
- **Caching**: Performance optimization with Ehcache
- **Rate Limiting**: API protection using Bucket4j
- **Logging**: AOP-based request/response logging
- **Monitoring**: Spring Boot Actuator endpoints
- **Error Handling**: Global exception handling with consistent responses
- **Comprehensive Testing**: Unit, integration, and controller tests

## API Endpoints

### Blackout REST APIs

- `GET /blackouts` - Get all blackouts
- `GET /blackouts/{id}` - Get a specific blackout
- `POST /blackouts` - Report a new blackout
- `PUT /blackouts/{id}` - Update a blackout
- `DELETE /blackouts/{id}` - Delete a blackout

### User REST APIs

- `GET /users` - Get all users
- `GET /users/{id}` - Get a specific user
- `GET /users/{id}/rewards` - Get user rewards
- `POST /users` - Create a new user
- `PUT /users/{id}` - Update a user
- `DELETE /users/{id}` - Delete a user

### Weather REST APIs

- `GET /weather/{location}` - Get current weather for a location
- `GET /weather/{location}/forecast?days=5` - Get weather forecast for a location
- `GET /weather/coordinates?latitude=x&longitude=y` - Get weather by coordinates

### SOAP Client Demo APIs

- `GET /soap-client/outage-status/{locationCode}` - Get power outage status
- `POST /soap-client/report-outage` - Report a power outage
- `GET /soap-client/resolution-time/{outageId}` - Get estimated resolution time

## SOAP Service

The SOAP service is exposed at `/ws/poweroutage` and provides the following operations:

- `getPowerOutageStatus` - Get power outage status for a location
- `reportPowerOutage` - Report a new power outage
- `getEstimatedResolutionTime` - Get estimated resolution time for an outage

## Technologies Used

### Core Technologies
- Spring Boot 3.5.0
- Spring Data JPA
- H2 Database
- Spring WebFlux (for reactive HTTP client)
- JAX-WS (for SOAP services)
- Lombok

### Advanced Technologies
- Spring Security with JWT
- Swagger/OpenAPI for API documentation
- Spring Cache with Ehcache
- Bucket4j for rate limiting
- Spring AOP for cross-cutting concerns
- Spring Boot Actuator for monitoring
- Spring Validation for input validation
- JUnit 5 and Mockito for testing

## Getting Started

### Prerequisites

- Java 17
- Maven

### Running the Application

```bash
# Clone the repository
git clone [repository-url]
cd blackoutservice

# Build the application
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

The application will be available at http://localhost:8080

H2 Database console is available at http://localhost:8080/h2-console

## Project Structure

The project follows a layered architecture with:

- Controllers - For handling HTTP requests
- Services - Business logic layer
- Repositories - Data access layer
- Models - Domain entities
- DTOs - Data Transfer Objects

## External API Integration

The application demonstrates integration with the INMET weather API (simulated) and includes a SOAP service component that can be consumed by other applications.

# Blackout Service - Project Luzia

This is a FIAP Project for Global Solution. Who did this?
Pedro Morais - RM98804
Gustavo Vegi - RM550188
A comprehensive Spring Boot application that provides REST and SOAP APIs for tracking and managing power outages with advanced enterprise features.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-brightgreen)
![JWT](https://img.shields.io/badge/Security-JWT-blue)
![Testing](https://img.shields.io/badge/Testing-JUnit%2FMockito-green)

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

# Access the H2 Console
open http://localhost:8080/h2-console
# JDBC URL: jdbc:h2:mem:blackoutdb
# Username: sa
# Password: (leave empty)
```

The application will be available at http://localhost:8080

## Architecture

### Layered Architecture
The application follows a traditional layered architecture:
- **Controller Layer**: Handles HTTP requests and responses
- **Service Layer**: Contains business logic and transaction management
- **Repository Layer**: Manages data access and persistence
- **Model Layer**: Defines entity classes and data structures

### API Design
- RESTful API design principles with proper HTTP methods and status codes
- DTO pattern for data transfer between layers
- Validation at the DTO level using Bean Validation

### Security Architecture
- Stateless authentication using JWT tokens
- Role-based access control
- Password encryption using BCrypt
- Request filtering for token validation and rate limiting

## Testing Strategy

The project includes comprehensive testing at multiple levels:

### Unit Testing
- Service layer testing with mocked dependencies
- Controller testing using MockMvc
- Utility class testing for core functionality

### Integration Testing
- Repository layer tests with H2 database
- End-to-end API tests with TestRestTemplate
- SOAP service integration tests

### Testing Tools
- JUnit 5 for test execution
- Mockito for mocking dependencies
- Spring Boot Test for integration testing
- H2 in-memory database for data layer testing

## API Usage Examples

### Authentication

```bash
# Register a new user
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name": "Test User", "email": "test@example.com", "password": "password123"}'

# Login and get JWT token
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "test@example.com", "password": "password123"}'
```

### Using JWT Token

```bash
# Get all blackouts (authenticated request)
export TOKEN="your-jwt-token"
curl -X GET http://localhost:8080/blackouts \
  -H "Authorization: Bearer $TOKEN"

# Report a new blackout
curl -X POST http://localhost:8080/blackouts \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "location": "Downtown Area",
    "startTime": "2025-05-30T12:00:00",
    "status": "ACTIVE",
    "description": "Major power outage",
    "latitude": -23.5505,
    "longitude": -46.6333,
    "reportedById": 1
  }'
```

## Advanced Features Deep Dive

### Rate Limiting
The application implements rate limiting using Bucket4j with the following rules:
- 20 requests per minute per IP address
- Configurable rate limits for different API endpoints
- Custom rate limit headers in responses

### Caching Strategy
Caching is implemented using Spring Cache with Ehcache:
- Caching is configured for frequently accessed data like blackouts and weather information
- Cache eviction on data modifications
- Configurable TTL for cache entries

### Monitoring
The application provides monitoring via Spring Boot Actuator:
- Health endpoint: `/actuator/health`
- Metrics endpoint: `/actuator/metrics`
- Information endpoint: `/actuator/info`


### Environment Variables
The following environment variables can be configured:

| Variable | Description | Default |
|----------|-------------|--------|
| `SERVER_PORT` | Application port | 8080 |
| `JWT_SECRET` | Secret key for JWT signing | Configured in application.properties |
| `JWT_EXPIRATION` | Token expiration time in seconds | 86400 (24 hours) |

### Production Deployment
For production deployment, consider:

1. Using a production-grade database like PostgreSQL or MySQL
2. Configuring proper logging with logback.xml
3. Setting up HTTPS with a valid SSL certificate
4. Configuring container orchestration with Docker and Kubernetes



## License

This project is licensed under the [MIT License](LICENSE).

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

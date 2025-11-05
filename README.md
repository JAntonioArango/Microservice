# <span style="color: #4169E1;">Trainer Workload Microservice</span>

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.6-brightgreen)
![Java](https://img.shields.io/badge/Java-21-blue)
![Docker](https://img.shields.io/badge/Docker-Ready-blue)
![Eureka](https://img.shields.io/badge/Eureka-Client-green)
![OpenAPI](https://img.shields.io/badge/OpenAPI-3.0-orange)

A Spring Boot microservice for managing trainer workloads with MongoDB NoSQL database and Eureka service discovery integration.

## <span style="color: #2E8B57;">Overview</span>

This microservice provides REST APIs to manage trainer workload data, including saving workload information, retrieving workload summaries, and deleting workload records.  <br>
It's designed to work as part of a microservices architecture with MongoDB NoSQL database and Eureka service discovery.

## <span style="color: #2E8B57;">Features</span>

- **Workload Management**: Save, retrieve, and delete trainer workload records
- **Workload Summary**: Get comprehensive workload summaries by trainer username
- **Asynchronous Processing**: JMS message consumer for workload data processing
- **Service Discovery**: Eureka client integration for microservices architecture
- **NoSQL Database**: MongoDB for scalable document-based storage
- **Database Indexing**: Compound indexes for optimized query performance
- **Health Monitoring**: Spring Boot Actuator for health checks
- **OpenFeign Support**: Ready for inter-service communication
- **API Documentation**: Swagger/OpenAPI 3.0 integration
- **Input Validation**: Spring Boot Validation for request validation
- **Exception Handling**: Custom exception handling for workload operations

## <span style="color: #2E8B57;">Technology Stack</span>

- **<span style="color: #4169E1;">Java 21</span>**
- **<span style="color: #4169E1;">Spring Boot 3.3.6</span>**
- **<span style="color: #4169E1;">Spring Cloud 2023.0.6</span>**
- **<span style="color: #4169E1;">Spring Data MongoDB</span>**
- **<span style="color: #4169E1;">MongoDB NoSQL Database</span>**
- **<span style="color: #4169E1;">Spring Session Data MongoDB</span>**
- **<span style="color: #4169E1;">Apache Artemis JMS</span>**
- **<span style="color: #4169E1;">Eureka Client</span>**
- **<span style="color: #4169E1;">OpenFeign</span>**
- **<span style="color: #4169E1;">Swagger/OpenAPI 3.0</span>**
- **<span style="color: #4169E1;">Spring Boot Validation</span>**
- **<span style="color: #4169E1;">Lombok</span>**
- **<span style="color: #4169E1;">Maven</span>**
- **<span style="color: #4169E1;">Docker</span>**

## <span style="color: #2E8B57;">API Endpoints</span>

### Base URL: `/api/workload/v1`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/saveworkload` | Save trainer workload data |
| GET | `/summary/{username}` | Get workload summary for a trainer |
| DELETE | `/delete/{id}` | Delete workload record by ID |

### <span style="color: #2E8B57;">Request/Response Examples</span>

#### Save Workload
```json
POST /api/workload/v1/saveworkload
Content-Type: application/json

{
  "username": "trainer123",
  "firstName": "John",
  "lastName": "Doe",
  "active": true,
  "trainingDate": "2024-01-15T10:00:00.000Z",
  "trainingDuration": 60
}
```

#### Get Workload Summary
```http
GET /api/workload/v1/summary/trainer123
```

Response:
```json
{
  "username": "trainer123",
  "firstName": "John",
  "lastName": "Doe",
  "active": true,
  "years": [2024],
  "months": ["January"],
  "totalDuration": 180
}
```

## <span style="color: #2E8B57;">Asynchronous Processing</span>

The microservice includes a JMS consumer that processes workload messages from the `Asynchronous.Task` queue. Messages are expected in the format:

```
TrainerWorkload[username=john.trainer, firstName=John, lastName=Smith, active=true, trainingDate=2024-01-15T10:30:00Z, trainingDuration=60]
```

## <span style="color: #2E8B57;">Configuration</span>

### Application Properties (`application.yml`)

- **Service Name**: `microservice-task`
- **Database**: MongoDB NoSQL (`Microservice-NoSQL-basics`)
- **MongoDB URI**: `mongodb://localhost:27017/` with connection timeout
- **Message Queue**: `Asynchronous.Task`
- **Artemis Broker**: `tcp://localhost:61616` (configurable via `SPRING_ARTEMIS_BROKER_URL`)
- **Port**: Dynamic (0) - assigned by Eureka
- **Eureka Server**: `http://localhost:8762/eureka` (configurable via `EUREKA_URI`)
- **MongoDB Indexes**: Compound indexes on firstName and lastName for performance
- **Session Management**: MongoDB-based session storage

## <span style="color: #2E8B57;">Service Discovery</span>

This microservice registers with Eureka Server and can be discovered by other services using the service name `microservice-task`.

## <span style="color: #2E8B57;">API Documentation</span>

The microservice includes Swagger/OpenAPI 3.0 documentation available at:
- **Swagger UI**: `http://localhost:{port}/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:{port}/v3/api-docs`

## <span style="color: #2E8B57;">Docker Support</span>

### Build and Run
```bash
# Build the application
mvn clean package

# Build Docker image
docker build -t microservices-task .

# Run with Docker Compose
docker-compose up
```

### Environment Variables
- `EUREKA_URI`: Eureka server URL (default: `http://localhost:8762/eureka`)
- `SPRING_ARTEMIS_BROKER_URL`: Artemis broker URL (default: `tcp://localhost:61616`)
- `SPRING_ARTEMIS_USER`: Artemis username (default: `artemis`)
- `SPRING_ARTEMIS_PASSWORD`: Artemis password (default: `artemis`)
- `MONGODB_URI`: MongoDB connection string (default: `mongodb://localhost:27017/`)
- `MONGODB_DATABASE`: MongoDB database name (default: `Microservice-NoSQL-basics`)

## <span style="color: #2E8B57;">Testing Strategy</span>

### Unit Test Naming Convention
All unit tests follow the `MethodName_Scenario_ExpectedBehavior` naming pattern for clarity and maintainability:
- **MethodName**: The method being tested
- **Scenario**: The specific condition or input
- **ExpectedBehavior**: The expected outcome

Examples:
- `saveWorkload_validWorkload_callsRepository()`
- `validate_blankUsername_violationReturned()`
- `onMessage_nullMessage_handlesGracefully()`

## <span style="color: #2E8B57;">Development Notes</span>

- Uses Lombok for reducing boilerplate code
- MongoDB NoSQL database for scalable document storage
- Compound indexes automatically created for performance optimization
- Document-based data model with embedded year and month summaries
- JMS consumer processes workload messages asynchronously
- Dynamic port assignment for multiple instances
- Eureka client configuration for service discovery
- OpenFeign ready for inter-service communication
- Custom exception handling for workload operations
- Input validation using Spring Boot Validation
- MongoDB session management for distributed sessions
- Comprehensive API documentation with Swagger

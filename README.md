# <span style="color: #4169E1;">Trainer Workload Microservice</span>

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.6-brightgreen)
![Java](https://img.shields.io/badge/Java-21-blue)
![Docker](https://img.shields.io/badge/Docker-Ready-blue)
![Eureka](https://img.shields.io/badge/Eureka-Client-green)
![OpenAPI](https://img.shields.io/badge/OpenAPI-3.0-orange)
![Cucumber](https://img.shields.io/badge/Cucumber-BDD-green)

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
- **<span style="color: #4169E1;">Cucumber BDD Framework</span>**
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

### BDD with Cucumber Framework
The project implements comprehensive testing using Behavior-Driven Development (BDD) with Cucumber framework at multiple levels:

#### Test Structure

- **Unit Tests**: Traditional JUnit tests with `MethodName_Scenario_ExpectedBehavior` naming
- **Component Tests**: Cucumber BDD scenarios testing business logic with mocked dependencies
- **Integration Tests**: Full end-to-end Cucumber scenarios with real Spring Boot application context

#### Cucumber Test Execution
```bash
# Run all tests including Cucumber
mvn test

# Run only component tests (mocked dependencies)
mvn test -Dtest=ComponentTest

# Run only integration tests (full Spring Boot context)
mvn test -Dtest=IntegrationTest

# Run specific Cucumber test suite
mvn test -Dtest=CucumberTest

```

#### Component Tests

- **Location**: `src/test/resources/features/component/`
- **Execution**: Isolated testing with mocked dependencies
- **Focus**: Business logic validation without external systems
- **Reports**: `target/cucumber-reports/component/`


#### Integration Tests
- **Location**: `src/test/resources/features/integration/`
- **Execution**: Full Spring Boot application context with real dependencies
- **Focus**: End-to-end functionality including database operations, API endpoints, and message processing
- **Reports**: `target/cucumber-reports/integration/`

#### BDD Test Coverage
- **Message Consumer**: JMS message processing scenarios (both component and integration)
- **Workload Service**: Business logic operations with various data scenarios
- **REST API**: Full HTTP request/response cycles with validation
- **Database Operations**: MongoDB integration with actual persistence
- **Error Handling**: Exception scenarios and error response validation

#### Test Reports
Cucumber generates comprehensive HTML reports with detailed scenario results:
- Component test reports: `target/cucumber-reports/component/`
- Integration test reports: `target/cucumber-reports/integration/`

### Unit Test Naming Convention
Traditional unit tests follow the `MethodName_Scenario_ExpectedBehavior` pattern:
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
- **BDD Testing**: Multi-level Cucumber framework implementation
    - **Component Level**: Isolated business logic testing with mocked dependencies
    - **Integration Level**: Full end-to-end testing with real Spring Boot context
    - **Behavior Specification**: Business requirements expressed as executable Gherkin scenarios
    - **Test Coverage**: Both positive and negative test scenarios for comprehensive validation


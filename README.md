# <span style="color: #4169E1;">Trainer Workload Microservice</span>

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.6-brightgreen)
![Java](https://img.shields.io/badge/Java-21-blue)
![Docker](https://img.shields.io/badge/Docker-Ready-blue)
![Eureka](https://img.shields.io/badge/Eureka-Client-green)

A Spring Boot microservice for managing trainer workloads with Eureka service discovery integration.

## <span style="color: #2E8B57;">Overview</span>

This microservice provides REST APIs to manage trainer workload data, including saving workload information, retrieving workload summaries, and deleting workload records.  <br>
It's designed to work as part of a microservices architecture with Eureka service discovery.

## <span style="color: #2E8B57;">Features</span>

- **Workload Management**: Save, retrieve, and delete trainer workload records
- **Workload Summary**: Get comprehensive workload summaries by trainer username
- **Service Discovery**: Eureka client integration for microservices architecture
- **In-Memory Database**: H2 database for development and testing
- **Health Monitoring**: Spring Boot Actuator for health checks
- **OpenFeign Support**: Ready for inter-service communication

## <span style="color: #2E8B57;">Technology Stack</span>

- **<span style="color: #4169E1;">Java 21</span>**
- **<span style="color: #4169E1;">Spring Boot 3.3.6</span>**
- **<span style="color: #4169E1;">Spring Cloud 2023.0.6</span>**
- **<span style="color: #4169E1;">Spring Data JPA</span>**
- **<span style="color: #4169E1;">H2 Database</span>**
- **<span style="color: #4169E1;">Eureka Client</span>**
- **<span style="color: #4169E1;">OpenFeign</span>**
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

## <span style="color: #2E8B57;">Configuration</span>

### Application Properties (`application.yml`)

- **Service Name**: `microservice-task`
- **Database**: H2 in-memory database
- **Port**: Dynamic (0) - assigned by Eureka
- **Eureka Server**: `http://localhost:8762/eureka`

## <span style="color: #2E8B57;">Service Discovery</span>

This microservice registers with Eureka Server and can be discovered by other services using the service name `microservice-task`.

## <span style="color: #2E8B57;">Development Notes</span>

- Uses Lombok for reducing boilerplate code
- H2 database console available for debugging
- Dynamic port assignment for multiple instances
- Eureka client configuration for service discovery
- OpenFeign ready for inter-service communication

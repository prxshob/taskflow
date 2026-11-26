# TaskFlow

A task management application designed to help users organize, manage, and track their tasks efficiently.

## Features
- [x] Create tasks
- [x] Retrieve and view tasks
- [x] Update tasks
- [x] Delete tasks
- [ ] Task status management
- [ ] Task prioritization
- [x] Database persistence
- [ ] User registration and authentication

## Tech Stack

- Backend: Java 21
- Framework: Spring Boot 4.1.0
- Build Tool: Maven
- API: REST
- Storage: PostgreSQL 16

## Project Structure

```text
taskflow/
├── src/
│   ├── main/java/
│   │   │
│   │   com.prashobh.taskflow/
│   │   ├── controller/
│   │   │   └── TaskController.java
│   │   ├── service/
│   │   │   └── TaskService.java
│   │   ├── repository/
│   │   │   └── TaskRepository.java
│   │   ├── model/
│   │   │   └── Task.java
│   │   ├── dto/
│   │   │   ├── TaskCreateRequest.java
│   │   │   ├── TaskUpdateRequest.java
│   │   │   ├── TaskResponse.java
│   │   │   └── ErrorResponse.java
│   │   ├── exception/
│   │   │   ├── TaskNotFoundException.java
│   │   │   ├── DuplicateTaskException.java
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── mapper/
│   │   │   └── TaskMapper.java
│   │   └── TaskFlowApplication
│   │
│   ├── main/db/migration/
│   │   └── V1__create_tasks_table.sql
│   │
│   └── test/
│       │
│       com.prashobh.taskflow/
│       ├── controller/
│       │   └── TaskControllerTest.java
│       ├── service/
│       │   └── TaskServiceTest.java
│       ├── repository/
│       │   └── TaskRepository.java
│       └── mapper/
│           └── TaskMapperTest.java
├── pom.xml
├── .gitignore
└── .env.example
└── README.md
```

## Prerequisites

- Java 21
- Maven (if not using the Maven wrapper)
- Database and version: PostgreSQL 16
## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/prxshob/taskflow
cd taskflow
```

### 2. Configure the application

Configure the required database connection and environment variables.

### 3. Run the application

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

## API Documentation

API endpoints and request/response examples will be documented here.

## Future Improvements
- [x] Integrate PostgreSQL with Spring Data JPA 
- [x] Implement persistent database storage 
- Add task status and priority management 
- Implement pagination, sorting, and filtering 
- Add validation and comprehensive exception handling 
- Introduce transaction management and Flyway migrations 
- Implement user registration and authentication using Spring Security and JWT 
- Expand unit and integration test coverage

## Milestones Tracker

Track the development progress of TaskFlow.

- [x] 01\. PostgreSQL + Spring Data JPA
- [x] 02\. Repository and service refactoring
- [x] 03\. JPA integration tests
- [x] 04\. README and API documentation
- [ ] 05\. Task status and priority
- [ ] 06\. Pagination and sorting
- [ ] 07\. Task filtering
- [ ] 08\. Task search
- [ ] 09\. Standardized API responses
- [ ] 10\. Flyway migrations
- [ ] 11\. Database constraints and indexes
- [ ] 12\. Testcontainers
- [ ] 13\. API integration test coverage
- [ ] 14\. User entity and repository
- [ ] 15\. User registration
- [ ] 16\. Spring Security
- [ ] 17\. JWT authentication
- [ ] 18\. Secure task endpoints
- [ ] 19\. User-specific task ownership
- [ ] 20\. Task comments
- [ ] 21\. Audit timestamps
- [ ] 22\. Optimistic locking
- [ ] 23\. Redis caching
- [ ] 24\. Task activity history
- [ ] 25\. Dockerfile
- [ ] 26\. Docker Compose
- [ ] 27\. Actuator and metrics
- [ ] 28\. OpenAPI / Swagger
- [ ] 29\. Production configuration and logging
- [ ] 30\. GitHub Actions CI
- [ ] 31\. Docker image workflow
- [ ] 32\. Cloud deployment
- [ ] 33\. Kafka event publishing
- [ ] 34\. Asynchronous notifications
- [ ] 35\. Caching optimization

## Author

Prashobh C P

# TaskFlow

A task management application designed to help users organize, manage, and track their tasks efficiently.

## Features
- [x] Create tasks
- [x] Retrieve and view tasks
- [x] Update tasks
- [x] Delete tasks
- [ ] Task status management
- [ ] Task prioritization
- [ ] Database persistence
- [ ] User registration and authentication

## Tech Stack

- Backend: Java 21
- Framework: Spring Boot 4.1.0
- Build Tool: Maven
- API: REST
- Storage: In-memory

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
└── README.md
```

## Prerequisites

- Java 21
- Maven (if not using the Maven wrapper)
- Database and version: The application currently uses in-memory storage. Data is not persisted across application restarts.

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
- Integrate PostgreSQL with Spring Data JPA 
- Implement persistent database storage 
- Add task status and priority management 
- Implement pagination, sorting, and filtering 
- Add validation and comprehensive exception handling 
- Introduce transaction management and Flyway migrations 
- Implement user registration and authentication using Spring Security and JWT 
- Expand unit and integration test coverage

## Author

Prashobh C P

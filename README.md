# Simple To-Do List Application

A Spring Boot application for organizing personal tasks. The project uses Spring Data JPA for persistence and separates task, category, and user logic into controllers, services, repositories, and domain models.

## Features

- Task and category management.
- User registration and user-related task workflows.
- Task status and category models.
- Request DTOs and validation support.
- Password hashing support through Spring Security Crypto.
- Persistence through Spring Data JPA and MySQL.
- H2 console dependency for development and database inspection.

## Technology stack

- Java 17
- Spring Boot 4.1.0
- Spring Web MVC
- Spring Data JPA / Hibernate
- MySQL Connector/J
- Jakarta Bean Validation
- Spring Security Crypto
- Lombok
- Maven

## Prerequisites

- JDK 17
- Maven (or the included Maven Wrapper)
- MySQL Server

## Getting started

1. Clone the repository:
   ```bash
   git clone https://github.com/robinmwaniki/Simple-To-Do-List-Application.git
   cd Simple-To-Do-List-Application
   ```
2. Create a MySQL database for the application.
3. Configure the datasource settings in `src/main/resources/application.properties` with your local database URL, username, and password. Do not commit real credentials.
4. Run the application:
   ```bash
   # Windows
   .\mvnw.cmd spring-boot:run

   # macOS / Linux
   ./mvnw spring-boot:run
   ```
5. Check the application startup output for the configured server port.

## Build and test

```bash
# Windows
.\mvnw.cmd clean verify

# macOS / Linux
./mvnw clean verify
```

## Project structure

- `controller/` — HTTP endpoints for users, tasks, and categories.
- `service/` — application and business logic.
- `repository/` — Spring Data JPA repositories.
- `model/` — task, category, status, and user domain models.
- `dto/` — request and response data transfer objects.
- `assembler/` — user representation mapping.
- `exception/` — application exception handling.

## Configuration

Review `src/main/resources/application.properties` before running the project. Database settings and any local secrets should be supplied through local configuration rather than committed values.

## License

No license file is currently documented in this repository.
# Hostel Management — Backend

Spring Boot REST API for the Hostel Management System.

## Tech Stack
- Java 17, Spring Boot
- MySQL
- JWT Authentication

## Setup

### Prerequisites
- Java 17+
- Maven
- MySQL database

### Environment Variables
Copy `.env.example` to `.env` and fill in values:
```
DB_URL=jdbc:mysql://localhost:3306/hosteldb
DB_USERNAME=your_db_user
DB_PASSWORD=your_db_password
JWT_SECRET=your_jwt_secret
```

### Run locally
```bash
mvn spring-boot:run
```

### Build JAR
```bash
mvn clean package -DskipTests
```

### Run with Docker
```bash
docker build -t hostel-backend .
docker run -p 8080:8080 --env-file .env hostel-backend
```

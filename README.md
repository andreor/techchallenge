# User Management System

This project is a **User Management System** built with **Java** and **Spring Boot**. It provides RESTful APIs for managing users, including creating, updating, deleting, and retrieving user information.

## Features

- **Create User**: Add a new user to the system.
- **Update User**: Modify existing user details.
- **Update Password**: Change the password of an existing user.
- **Delete User**: Remove a user from the system.
- **Cross-Origin Support**: Allows requests from any origin.

## Technologies Used

- **Java 17**
- **Spring Boot**
- **Maven**
- **JUnit 5** for testing
- **Mockito** for mocking
- **ModelMapper** for object mapping
- **Swagger** for API documentation 
- **Mysql**

## Prerequisites

- Java 17 or higher
- Maven 3.8 or higher

## Getting Started

### Clone the Repository

```bash
    git clone https://github.com/andreor/techchallenge.git
cd techchallenge
```

### Build the application
```bash
    mvn install -DskipTests
```

### Build image for application on docker
```bash
    docker build -t spring-boot-techchallenge .
```

### Run the application
```bash
    docker-compose up -d
```



## Enpoints Documentation (Swagger)

- http://localhost:8080/swagger-ui.html
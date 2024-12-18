# Device API

A REST API for managing products, categories, and user roles in a secure and scalable e-commerce system, developed using Java and Spring Boot.

## Table of Contents

- [Project Overview](#project-overview)
- [Installation](#installation)
- [Structure](#structure)
- [Features](#features)
- [Technologies](#technologies)


## Project Overview

**Context**:  

The Device API provides functionality for managing musics, alubms, and users with role-based access control. It is designed for platforms where administrators manage the catalog of music and albums.


**Objectives**:

- Build a secure REST API with JWT token based authentication using Spring Security.
- Implement role-based access control with distinct ADMIN and USER roles.
- Enable CRUD operations for musics, albums, and users.
- Provide efficient pagination, sorting, and filtering for product and category data.
- Handle security, validation, and exceptions robustly.
- Use Docker and Jenkins for deployment workflows.
- Use of a No sql database, MongoDB in this case.

## Installation

### Prerequisites

- Java 8 or higher
- Apache Maven
- MongoDB local service or atlas (cloud)

### Steps

1. **Clone the repository:**

   ```sh
   git clone https://github.com/Yorften/DeviceAPI
   cd DeviceAPI/musicapi

2. **Build the application:**
   ```sh
   mvn clean install

3. **Run the application:**
   ```sh
   mvn spring-boot:run

3. **Access the application :**
  - Default Port: http://localhost:8085

## Structure

- **Entities**:  
  Defines JPA entities such as `User`, `Device`, and `Zone`, and their relationships (e.g., many-to-one or many-to-many).

- **Repository Layer**:  
  Extend `MongoRepository` for data access and include custom query methods.

- **Service Layer**:  
  Contains business logic and orchestrates operations between the Controller and Repository layers.
  
- **Controller Layer**:  
  Implements REST endpoints for managing learners, trainers, classes, and training sessions using `@RestController` annotation.
  
- **Exceptions**:  
  Centralized exception handling for REST API responses.

- **Utilities**:  
  Common utility classes and methods.

- **Tests**:  
  Unit tests implemented with Mockito and JUnit.

## Features

1. **User Management**:
   - Role-based access (ADMIN, USER).
   - Admins can manage users, including assigning roles.

2. **Device Management**:
   - List of musics with pagination, sorting, and filtering accessible by both ADMIN and USER roles.
   - Admin-only access for creating, updating, and deleting products.

3. **Zone Management**:
   - Manage albums with hierarchical relationships.
   - Admin-only access for creating, updating, and deleting categories.

4. **Security**:
   - JWT authentication using Spring Security and MongoDB.
   - Prevents unauthorized access to protected resources.

5. **Deployment**:
   - Docker: The application can be containerized using Docker for easy deployment.
   - Jenkins: CI/CD pipelines for automated builds, tests, and deployments.

## Technologies

- **Java 8**: Core language used for development.
- **Apache Maven**: For dependency management and project build.
- **Spring Boot**: For creating the REST API and managing application configuration.
- **Spring Data MongoDB**: For database interactions and repository management.
- **Spring Security**: Secures the application with session-based authentication.
- **MongoDB**: No relational database for production.
- **JUnit**: For unit testing.
- **Mockito**: For mocking and testing services.
- **Lombok**: For reducing boilerplate code.
- **Docker**: For containerized deployment.
- **Jenkins**: For CI/CD pipeline management.
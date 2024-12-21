# 🌡️ API de Gestion des Capteurs IoT

Une API REST sécurisée pour la gestion des capteurs IoT, développée avec Spring Boot et intégrant les pratiques DevOps modernes.

## 📑 Table des matières

- [Aperçu](#aperçu)
- [Architecture](#architecture)
- [Installation](#installation)
- [API Endpoints](#api-endpoints)
- [Configuration](#configuration)
- [DevOps & CI/CD](#devops--cicd)
- [Technologies](#technologies)

## 🎯 Aperçu

### Contexte

Système de gestion des capteurs IoT permettant le suivi des mesures de température et d'humidité, avec gestion des alertes et zones, intégrant une authentification sécurisée et des rôles utilisateurs.

### Entités Principales 📊

- **Appareil** : Capteurs IoT (température/humidité)
- **Mesure** : Données collectées
- **Alerte** : Notifications basées sur les seuils
- **Zone** : Regroupement logique des appareils
- **Utilisateur** : Gestion des accès et rôles

### Seuils d'Alerte 🚨

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
   git clone https://github.com/JavaAura/Barj_Badi_Lamtioui_Bouchehboune_Sprint_4_B3_SmartSenseAPI
   cd ./smartsense

   ```

2. **Build the application:**

   ```sh
   mvn clean install

   ```

3. **Run the application:**

   ```sh
   mvn spring-boot:run

   ```

4. **Access the application :**

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

## 🔄 DevOps & CI/CD

### Pipeline

1. **GitHub Actions** 🔍

   - Tests unitaires
   - Validation syntaxique
   - Scan de vulnérabilités

2. **Jenkins** 🏗️
   - Build
   - Tests d'intégration
   - Déploiement Docker
   - Publication sur DockerHub

### Monitoring

- Spring Actuator
- Logging système
- Métriques de performance

## 💻 Technologies

- **Backend** : Spring Boot, Spring Security, Spring Data
- **Base de données** : MongoDB
- **Tests** : JUnit, Mockito
- **Documentation** : Swagger
- **CI/CD** : Jenkins, GitHub Actions
- **Conteneurisation** : Docker
- **Qualité** : SonarLint
- **Outils** : Lombok, DevTools

4. **Security**:
   - JWT authentication using Spring Security and MongoDB.
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

# DevConnect Project Documentation

## Project Overview
**DevConnect** is a professional networking and collaboration platform designed specifically for developers. It allows users to create professional profiles, showcase their technical skills, share updates through posts, and connect with like-minded individuals in the tech community.

---

## Core Functionality

### 1. User Authentication & Authorization
*   **Registration**: New users can register with a username, email, and password.
*   **Security**: Implements Spring Security with BCrypt password hashing.
*   **JWT Support**: Configured for JSON Web Token (JWT) based authentication (ready for stateless session management).
*   **Role Management**: (Base structure present) Designed to restrict access to sensitive endpoints.

### 2. Global Exception Handling
*   **Centralized Error Management**: Uses `@ControllerAdvice` to catch and format API errors globally.
*   **Custom Responses**: Returns structured `ApiError` objects with timestamps and error details for better debugging.
*   **Standard Exceptions**: Includes support for `ResourceNotFoundException`.

### 3. Professional Profiles
*   **Profile Management**: Users can create and update detailed profiles.
*   **Data Points**:
    *   Bio/Summary
    *   Technical Skills (string-based list)
    *   Location
*   **User Association**: Each profile is uniquely linked to a specific User entity (One-to-One).

### 4. Social Interaction (Posts)
*   **Content Sharing**: Users can create text-based posts to share knowledge, updates, or questions.
*   **Feed**: Ability to retrieve a list of all posts from the community.
*   **Associations**: Each post is linked to the user who created it (Many-to-One).

---

## Technical Stack

| Component | Technology |
| :--- | :--- |
| **Backend Framework** | Java 21 + Spring Boot 4.x (Snapshot) |
| **Database** | MySQL (Local instance: `localhost:3306/devconnect`) |
| **ORM / Persistence** | Spring Data JPA + Hibernate |
| **Security** | Spring Security 6.x + JJWT (JWT API) |
| **Build Tool** | Maven |
| **Utilities** | Lombok (for boilerplate reduction), Jakarta Validation (for DTO/Entity constraints) |

---

## Architecture & Data Flow

### Layered Architecture
The project follows a standard 4-layer Spring Boot architecture:
1.  **Controller Layer**: Handles HTTP requests and maps them to service methods (`/api/*`).
2.  **Service Layer**: Contains business logic (e.g., registration validation, post creation logic).
3.  **Repository Layer**: Interfaces extending `JpaRepository` for database abstraction.
4.  **Entity Layer**: Represents the database schema as Java classes (`User`, `Profile`, `Post`).

### Entity Relationship Model
*   **User**: The central entity.
    *   `1:1` with `Profile`
    *   `1:N` with `Post`
*   **Post**: Contains content and a reference back to the `User`.
*   **Profile**: Contains professional details and a reference back to the `User`.

---

## API Documentation (Endpoints)

| Method | Endpoint | Description | Payload (Request Body) |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/register` | Register a new user | `username`, `email`, `password` |
| `POST` | `/api/profile` | Create/Update profile | `bio`, `skills`, `location`, `userId` |
| `POST` | `/api/post` | Create a new post | `content`, `userId` |
| `GET` | `/api/post` | Retrieve all posts | N/A |

---

## How It Works (Internals)

1.  **Request Handling**: When a client sends a JSON request (e.g., to `/api/post`), Spring's `DispatcherServlet` routes it to `PostController`.
2.  **Validation**: `Jakarta Validation` (e.g., `@NotNull`, `@Size`) ensures the incoming DTO is valid before it reaches the service layer.
3.  **Persistence**: The `PostService` converts the DTO into a `Post` entity, associates it with a `User` (fetched from the DB), and saves it using `PostRepository`.
4.  **Database Strategy**: Uses `hibernate.ddl-auto=update`, meaning the database schema is automatically kept in sync with the Java entities.
5.  **Security Filter**: Currently, all endpoints are permitted in `SecurityConfig.java` for development ease, but the infrastructure for JWT filters is integrated.

---

## Usage Instructions for AI Agents
When interacting with this codebase, focus on:
- **`com.DevConnect.entity`**: To understand the data structure.
- **`com.DevConnect.controller`**: To understand external interface points.
- **`pom.xml`**: To see available libraries and dependencies.
- **`application.properties`**: For environment and connection details.

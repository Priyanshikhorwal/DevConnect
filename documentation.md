# DevConnect Project Documentation

## Project Overview
**DevConnect** is a professional networking and collaboration platform designed specifically for developers. It allows users to create professional profiles, showcase their technical skills, share updates through posts, and connect with like-minded individuals in the tech community.

---

## What the App Can Do (Current Capabilities)

### 1. User Authentication & Authorization
*   **Registration**: New users can register with a username, email, and password.
*   **Security Foundation**: Implements Spring Security with BCrypt password hashing to protect user data.
*   **Phase 1 JWT Authentication (Completed)**: 
    *   **JwtService**: Handles generating, parsing, and validating JSON Web Tokens using JJWT 0.11.5.
    *   **JwtAuthenticationFilter**: Intercepts requests, validates Bearer tokens, extracts user details, and updates the `SecurityContext`.
    *   **Route Protection & Stateless Sessions**: `SecurityConfig` is configured to protect all endpoints by default (except public ones like auth/profile/post) and enforce `STATELESS` session creation to prevent server-side session memory leaks.
*   **Role Management**: Base structure is present to restrict access to sensitive endpoints based on user roles.

### 2. Professional Profiles
*   **Profile Management**: Users can create and update detailed profiles.
*   **Data Points**:
    *   Bio/Summary
    *   Technical Skills (string-based list)
    *   Location
*   **User Association**: Each profile is uniquely linked to a specific User entity (One-to-One).

### 3. Social Interaction (Posts)
*   **Content Sharing**: Users can create text-based posts to share knowledge, updates, or questions.
*   **Feed**: Ability to retrieve a list of all posts from the community.
*   **Associations**: Each post is linked to the user who created it (Many-to-One).

### 4. Global Exception Handling
*   **Centralized Error Management**: Uses `@ControllerAdvice` to catch and format API errors globally.
*   **Custom Responses**: Returns structured `ApiError` objects with timestamps and error details for better debugging.
*   **Standard Exceptions**: Includes support for standard exceptions like `ResourceNotFoundException`.

---

## Technical Stack

| Component | Technology |
| :--- | :--- |
| **Backend Framework** | Java 21 + Spring Boot 3.x/4.x (Snapshot) |
| **Database** | MySQL (Local instance: `localhost:3306/devconnect`) |
| **ORM / Persistence** | Spring Data JPA + Hibernate |
| **Security** | Spring Security 6.x + JJWT (JWT API) |
| **Build Tool** | Maven |
| **Utilities** | Lombok (for boilerplate reduction), Jakarta Validation (for DTO/Entity constraints) |

---

## Architecture & Data Flow

### Layered Architecture
The project follows a standard 4-layer Spring Boot architecture:
1.  **Controller Layer**: Handles HTTP requests and maps them to service methods (`/api/auth`, `/api/profile`, `/api/post`).
2.  **Service Layer**: Contains business logic (`AuthService`, `ProfileService`, `PostService`).
3.  **Repository Layer**: Interfaces extending `JpaRepository` for database abstraction.
4.  **Entity Layer**: Represents the database schema as Java classes (`User`, `Profile`, `Post`).

### Entity Relationship Model
*   **User**: The central entity.
    *   `1:1` with `Profile`
    *   `1:N` with `Post`
*   **Post**: Contains content and a reference back to the `User`.
*   **Profile**: Contains professional details and a reference back to the `User`.

---

## API Documentation (Current Endpoints)

| Method | Endpoint | Description | Payload (Request Body) |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/register` | Register a new user | `username`, `email`, `password` |
| `POST` | `/api/profile` | Create/Update profile | `bio`, `skills`, `location` |
| `POST` | `/api/post` | Create a new post | `content` |
| `GET` | `/api/post` | Retrieve all posts | N/A |


---

## What to Add Next (Future Enhancements)

To make DevConnect a fully-fledged platform, the following features should be implemented next:

### 1. Advanced Authentication & Security
*   **Login Endpoint**: Implement `/api/auth/login` to generate and return JWT tokens upon successful authentication.
*   **OAuth2 Integration**: Allow users to log in using GitHub, Google, or LinkedIn.
*   **Email Verification**: Send a verification email when a user registers.
*   **Password Reset**: Allow users to reset their passwords securely via email links.

### 2. Social & Networking Features
*   **Connections/Followers System**: Allow users to connect with or follow other developers.
*   **Likes and Comments**: Enable users to interact with posts by liking them and adding comments.
*   **Direct Messaging**: Implement real-time or asynchronous private messaging between users (WebSocket/STOMP).
*   **Notifications**: Real-time or polled notifications for likes, comments, and connection requests.

### 3. Profile Enhancements
*   **Profile Pictures & Media Uploads**: Allow users to upload avatars and cover photos (integrate with AWS S3 or local storage).
*   **Experience & Education Tracking**: Let users add their work experience, projects, and education details.
*   **Skill Tags Validation**: Convert string-based skills to a standardized tagging system.

### 4. Search and Feed Optimization
*   **Search API**: Implement search functionality to find users by username, skills, or location.
*   **Feed Pagination**: Update the `GET /api/post` endpoint to support pagination and sorting (e.g., most recent, most popular).
*   **Personalized Feed**: Show posts primarily from connections or followed users.

### 5. DevOps & Testing
*   **Unit and Integration Tests**: Add comprehensive test coverage using JUnit and Mockito.
*   **Dockerization**: Create a `Dockerfile` and `docker-compose.yml` to easily spin up the application and MySQL database.
*   **CI/CD Pipeline**: Set up GitHub Actions for automated testing and deployment.
*   **API Documentation (Swagger/OpenAPI)**: Integrate `springdoc-openapi` to automatically generate an interactive API documentation UI.

---

## Usage Instructions for AI Agents
When interacting with this codebase, focus on:
- **`com.DevConnect.entity`**: To understand the data structure.
- **`com.DevConnect.controller`**: To understand external interface points.
- **`pom.xml`**: To see available libraries and dependencies.
- **`application.properties`**: For environment and connection details.

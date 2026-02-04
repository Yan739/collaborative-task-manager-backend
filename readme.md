# Collaborative Task Manager API

<div align="center">

![Java](https://img.shields.io/badge/Java-21-blue?logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.2-green?logo=spring&logoColor=white)
![H2 Database](https://img.shields.io/badge/H2-Database-blue?logo=h2&logoColor=white)
![Build](https://img.shields.io/badge/Build-Maven-red?logo=apachemaven&logoColor=white)
![Code Style](https://img.shields.io/badge/Code%20Style-MapStruct%2FDTO-lightgrey)
![JWT](https://img.shields.io/badge/JWT-Security-orange)

</div>

---

## Description

Complete REST API for collaborative task management. Built with **Spring Boot 3.4**, it implements a clean and modular architecture with **JWT-based security**, automated mapping via **MapStruct**, and unit testing with **JUnit 5 / Mockito**.

---

## Features

- **Authentication & Security**
  - Registration with unique email validation
  - Login with Access Token & Refresh Token generation
  - Session refresh via Refresh Token
  - BCrypt password hashing
- **Task Management (CRUD)**
  - Create, Read, Update, Delete
  - Task assignment between users
  - Status tracking (`TO_DO`, `IN_PROGRESS`, `DONE`) and priorities (`LOW`, `MEDIUM`, `HIGH`, `URGENT`)
- **Architecture & Quality**
  - Automated mapping via MapStruct
  - Server-side data validation (Jakarta Validation)
  - Centralized exception handling via `@RestControllerAdvice`
  - Unit testing with Mockito

---

## Technologies

| Technology | Version |
|---|---|
| Java | 21 |
| Spring Boot | 3.4.2 |
| Spring Data JPA | - |
| Spring Security | - |
| H2 Database | Development & Testing |
| MapStruct | 1.5.5 |
| Lombok | - |
| JJWT | - |
| JUnit 5 & Mockito | - |

---

## Project Structure
```
com.yann.collaborative_task_manager_backend
│
├── controler/          → REST entry points (Auth, Task, User)
├── service/            → Business logic, JWT, Security, Token management
├── repository/         → Database abstraction (JPA)
├── entity/
│   ├── userEntity/     → User, Role
│   ├── taskEntity/     → Task, Status, Priority
│   └── authEntity/     → RefreshToken
├── dto/
│   ├── authDTO/        → RegisterDTO, LoginDTO, AuthResponseDTO, RefreshTokenDTO
│   ├── taskDTO/        → TaskCreateDTO, TaskDTO
│   └── userDTO/        → UserDTO, UserUpdateDTO
├── mapper/             → MapStruct interfaces (TaskMapper, UserMapper)
├── config/             → SecurityConfig, JwtAuthenticationFilter
└── exception/          → Centralized exception handling
```

---

## Security Flow (JWT)
```
Client                          Server
  │                                │
  │──── POST /api/auth/register ──>│  Verify unique email, encode password
  │<─── 200 OK ────────────────────│
  │                                │
  │──── POST /api/auth/login ─────>│  Validate credentials via AuthenticationManager
  │<─── { accessToken, refreshToken }
  │                                │
  │──── GET /api/tasks ───────────>│  Header: Authorization: Bearer <accessToken>
  │<─── 200 OK + data ─────────────│
  │                                │
  │──── POST /api/auth/refresh ───>│  Send refreshToken
  │<─── { new accessToken } ───────│
```

---

## Endpoints

### Authentication

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| POST | `/api/auth/register` | Register | No |
| POST | `/api/auth/login` | Login | No |
| POST | `/api/auth/refresh` | Refresh token | No |

### Tasks

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| POST | `/api/tasks` | Create a task | Yes |
| GET | `/api/tasks` | All tasks | Yes |
| GET | `/api/tasks/{id}` | Task by ID | Yes |
| PUT | `/api/tasks/{id}` | Update a task | Yes |
| DELETE | `/api/tasks/{id}` | Delete a task | Yes |

### Users

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| GET | `/api/users` | List all users | ADMIN |
| GET | `/api/users/{id}` | User by ID | ADMIN |
| PUT | `/api/users/{id}` | Update a user | ADMIN |
| DELETE | `/api/users/{id}` | Delete a user | ADMIN |

---

## Examples

### Register
```http
POST /api/auth/register
Content-Type: application/json

{
  "userName": "Yann",
  "email": "yann@example.com",
  "password": "MySecurePassword123",
  "role": "ADMIN"
}
```

### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "yann@example.com",
  "password": "MySecurePassword123"
}
```

### Login Response
```json
{
  "accessToken": "eyJhbGciOiJIUzI1Ni...",
  "refreshToken": "78b4..."
}
```

### Create a Task
```http
POST /api/tasks
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1Ni...

{
  "title": "Implement the API",
  "description": "Create REST endpoints",
  "status": "TO_DO",
  "priority": "HIGH",
  "dueDate": "2026-02-15T23:59:00",
  "ownerId": 1,
  "assignedToId": 2
}
```

### Refresh Token
```http
POST /api/auth/refresh
Content-Type: application/json

{
  "refreshToken": "78b4..."
}
```

---

## Installation
```bash
# Clone the project
git clone <repo-url>
cd collaborative-task-manager-backend

# Build (generates MapStruct classes)
mvn clean compile

# Run tests
mvn test

# Run the project
mvn spring-boot:run
```

API available at: `http://localhost:8080`

---

## H2 Console

| Parameter | Value |
|---|---|
| URL | `http://localhost:8080/h2-console` |
| JDBC URL | `jdbc:h2:mem:testdb` |
| User | `sa` |
| Password | *(empty)* |

---

*Personal project — public — no restrictive license.*
# Collaborative Task Manager API

<div align="center">

![Java](https://img.shields.io/badge/Java-21-blue?logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.2-green?logo=spring&logoColor=white)
![H2 Database](https://img.shields.io/badge/H2-Database-blue?logo=h2&logoColor=white)
![Build](https://img.shields.io/badge/Build-Maven-red?logo=apachemaven&logoColor=white)
![Code Style](https://img.shields.io/badge/Code%20Style-MapStruct%2FDTO-lightgrey)
![JWT](https://img.shields.io/badge/JWT-Security-orange)
![License](https://img.shields.io/badge/License-Projet%20Personnel-lightgrey)

</div>

---

## Description

API REST complète pour la gestion de tâches collaborative. Construit avec **Spring Boot 3.4**, elle met en œuvre une architecture propre et modulaire avec une sécurité basée sur **JWT**, un mapping automatisé via **MapStruct** et des tests unitaires **JUnit 5 / Mockito**.

---

## Fonctionnalités

- **Authentification & Sécurité**
    - Inscription avec validation d'email unique
    - Connexion avec génération de couple Access Token & Refresh Token
    - Rafraîchissement de session via Refresh Token
    - Hachage des mots de passe avec BCrypt
- **Gestion des Tâches (CRUD)**
    - Création, lecture, mise à jour, suppression
    - Assignation de tâches entre utilisateurs
    - Suivi des statuts (`TO_DO`, `IN_PROGRESS`, `DONE`) et priorités (`LOW`, `MEDIUM`, `HIGH`, `URGENT`)
- **Architecture & Qualité**
    - Mapping automatisé via MapStruct
    - Validation des données côté serveur (Jakarta Validation)
    - Gestion centralisée des exceptions via `@RestControllerAdvice`
    - Tests unitaires avec Mockito

---

## Technologies

| Technologie | Version |
|---|---|
| Java | 21 |
| Spring Boot | 3.4.2 |
| Spring Data JPA | - |
| Spring Security | - |
| H2 Database | Développement & Tests |
| MapStruct | 1.5.5 |
| Lombok | - |
| JJWT | - |
| JUnit 5 & Mockito | - |

---

## Structure du Projet
```
com.yann.collaborative_task_manager_backend
│
├── controler/          → Points d'entrée REST (Auth, Task, User)
├── service/            → Logique métier, JWT, Sécurité, Gestion des jetons
├── repository/         → Abstraction de la base de données (JPA)
├── entity/
│   ├── userEntity/     → User, Role
│   ├── taskEntity/     → Task, Status, Priority
│   └── authEntity/     → RefreshToken
├── dto/
│   ├── authDTO/        → RegisterDTO, LoginDTO, AuthResponseDTO, RefreshTokenDTO
│   ├── taskDTO/        → TaskCreateDTO, TaskDTO
│   └── userDTO/        → UserDTO, UserUpdateDTO
├── mapper/             → Interfaces MapStruct (TaskMapper, UserMapper)
├── config/             → SecurityConfig, JwtAuthenticationFilter
└── exception/          → Gestion centralisée des exceptions
```

---

## Flux de Sécurité (JWT)
```
Client                          Serveur
  │                                │
  │──── POST /api/auth/register ──>│  Vérifie email unique, encode password
  │<─── 200 OK ────────────────────│
  │                                │
  │──── POST /api/auth/login ─────>│  Valide credentials via AuthenticationManager
  │<─── { accessToken, refreshToken }
  │                                │
  │──── GET /api/tasks ───────────>│  Header: Authorization: Bearer <accessToken>
  │<─── 200 OK + données ──────────│
  │                                │
  │──── POST /api/auth/refresh ───>│  Envoie refreshToken
  │<─── { nouveau accessToken } ───│
```

---

## Endpoints

### Authentification

| Méthode | Endpoint | Description | Auth requise |
|---|---|---|---|
| POST | `/api/auth/register` | Inscription | Non |
| POST | `/api/auth/login` | Connexion | Non |
| POST | `/api/auth/refresh` | Rafraîchir le token | Non |

### Tâches

| Méthode | Endpoint | Description | Auth requise |
|---|---|---|---|
| POST | `/api/tasks` | Créer une tâche | Oui |
| GET | `/api/tasks` | Toutes les tâches | Oui |
| GET | `/api/tasks/{id}` | Tâche par ID | Oui |
| PUT | `/api/tasks/{id}` | Mettre à jour une tâche | Oui |
| DELETE | `/api/tasks/{id}` | Supprimer une tâche | Oui |

### Utilisateurs

| Méthode | Endpoint | Description | Auth requise |
|---|---|---|---|
| GET | `/api/users` | Liste des utilisateurs | ADMIN |
| GET | `/api/users/{id}` | Utilisateur par ID | ADMIN |
| PUT | `/api/users/{id}` | Mettre à jour un utilisateur | ADMIN |
| DELETE | `/api/users/{id}` | Supprimer un utilisateur | ADMIN |

---

## Exemples

### Inscription
```http
POST /api/auth/register
Content-Type: application/json

{
  "userName": "Yann",
  "email": "yann@example.com",
  "password": "MonPasswordSecurise123",
  "role": "ADMIN"
}
```

### Connexion
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "yann@example.com",
  "password": "MonPasswordSecurise123"
}
```

### Réponse connexion
```json
{
  "accessToken": "eyJhbGciOiJIUzI1Ni...",
  "refreshToken": "78b4..."
}
```

### Créer une tâche
```http
POST /api/tasks
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1Ni...

{
  "title": "Implémenter l'API",
  "description": "Créer les endpoints REST",
  "status": "TO_DO",
  "priority": "HIGH",
  "dueDate": "2026-02-15T23:59:00",
  "ownerId": 1,
  "assignedToId": 2
}
```

### Rafraîchir le token
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
# Cloner le projet
git clone <url-du-repo>
cd collaborative-task-manager-backend

# Build (génère les classes MapStruct)
mvn clean compile

# Lancer les tests
mvn test

# Exécuter le projet
mvn spring-boot:run
```

API disponible à : `http://localhost:8080`

---

## Console H2

| Paramètre | Valeur |
|---|---|
| URL | `http://localhost:8080/h2-console` |
| JDBC URL | `jdbc:h2:mem:testdb` |
| User | `sa` |
| Password | *(vide)* |

---

*Projet personnel — public — pas de licence restrictive.*
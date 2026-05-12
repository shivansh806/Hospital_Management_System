# Hospital Management System

A secure and scalable Hospital Management REST API built using Spring Boot, Spring Security, JWT, OAuth2, and MySQL.

The system provides authentication, role-based authorization, patient profile management, doctor management, appointment workflows, insurance handling, and secure resource ownership validation.

---

## 📋 Table of Contents

- [Project Overview](#project-overview)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Key Features](#key-features)
- [Security Features](#-security-features)
- [API Endpoints](#api-endpoints)
- [Getting Started](#getting-started)
- [Architecture](#️-architecture)
- [Security Design](#-security-design)
- [Future Enhancements](#future-enhancements)
- [Author](#-author)

---

## 🎯 Project Overview

The Hospital Management System is a backend-focused application built using Spring Boot and Java 21 following clean architecture principles.

This project provides:

- Secure authentication system using JWT
- Refresh Token Authentication
- OAuth2 login integration
- Role-based authorization
- Patient profile management
- Insurance management
- Appointment booking system
- Doctor appointment reassignment
- Secure ownership validation
- Centralized exception handling
- Swagger/OpenAPI documentation

The application follows a layered architecture with Controllers, Services, Repositories, DTOs, and Security layers for scalability and maintainability.

---

## 🛠️ Tech Stack

| Component | Technology |
|-----------|-----------|
| **Framework** | Spring Boot 3.4.5 |
| **Language** | Java 21 |
| **Security** | Spring Security + JWT |
| **Authentication** | OAuth2 + JWT |
| **Build Tool** | Maven |
| **Database** | MySQL 8.0+ |
| **ORM** | JPA/Hibernate |
| **API Documentation** | Swagger / OpenAPI |
| **Boilerplate Reduction** | Lombok |
| **Data Mapping** | ModelMapper 3.2.0 |
| **Architecture** | Layered Architecture |
| **API Format** | REST (JSON) |

---

## 📁 Project Structure

```
src/
│
├── main/
│   ├── java/com/shivansh/code/hospitalManagement/
│   │
│   ├── config/                 # Application & Swagger Configurations
│   │
│   ├── controller/             # REST API Controllers
│   │
│   ├── dto/                    # Request & Response DTOs
│   │
│   ├── entity/                 # JPA Entities
│   │
│   ├── error/                  # Global Exception Handling
│   │
│   ├── repository/             # Database Access Layer
│   │
│   ├── response/               # Standard API Response Wrappers
│   │
│   ├── security/               # JWT, OAuth2 & Spring Security
│   │
│   ├── service/                # Business Logic Layer
│   │
│   └── HospitalManagementApplication.java
│
└── resources/
    ├── application.properties
```

---

## ✨ Key Features

### 1. **Patient Management**
- Secure patient profile retrieval
- Patient profile update
- Insurance add/remove APIs
- Secure patient data access
- Authenticated /me APIs

### 2. **Doctor Management**
- Create doctor profiles
- Fetch doctor profile
- Fetch logged-in doctor appointments
- Reassign appointments to another doctor

### 3. **Appointment Management**
- Book appointments
- Fetch logged-in patient appointments
- Delete appointments
- Doctor appointment reassignment
- Ownership validation for appointments

### 4. **Insurance Management**
- Link insurance policies to patients
- Store policy numbers and validity dates
- Manage insurance provider information
- Track insurance creation timestamps

### 5. **Authentication & Authorization**
- User Signup & Login
- JWT-based Authentication
- Refresh Token Support
- OAuth2 Login Integration (Google/GitHub)
- Role-Based Access Control (RBAC)
- Method-Level Security using @PreAuthorize
- Protected API Endpoints
- Stateless Authentication
- Custom Security Configuration

### 6. **Exception Handling**
- Global Exception Handler
- Standardized API error responses
- Clean error messaging structure

### 7. **API Documentation**
- Swagger/OpenAPI Integration
- Interactive API Testing
- Endpoint categorization
  
---

## 🔒 Security Features

- Spring Security Integration
- JWT Authentication Filter
- OAuth2 Success Handler
- Stateless Session Management
- Method-Level Authorization
- Role-Based Access Control
- Ownership Validation
- Secure Password Encoding
- Authenticated User Context

---

## 🔌 API Endpoints

### Authentication APIs
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/signup` | Create a new user |
| POST | `/auth/login` | Login user |
| POST | `/auth/refresh` | Generate new access token |
| GET | `/oauth2/authorization/google` | Google OAuth login |
| GET | `/oauth2/authorization/github` | Github OAuth login |


### Patient Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/patients/my-profile` | Get logged-in patient profile |
| PATCH | `/patients/my-profile` | Update patient profile |
| POST | `/patients/my-insurance` | Add insurance to patient |
| DELETE | `/patients/my-insurance` | Remove insurance from patient |

### Appointment Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/appointments/me` | Create new appointment |
| GET | `/appointments/me` | Get logged-in patient appointments |
| DELETE | `/appointments/{appointmentId}` | Delete own appointment | 
| PATCH | `/appointments/{appointmentId}/reassign-doctor/{doctorId}` | Reassign appointment to different doctor |

### Doctor APIs
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/doctors/me` | Get logged-in doctor |
| GET | `/doctors/appointments` | Get logged-in doctor appointment | 

### Public APIs
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/public/doctors` | Fetch public doctor list |

### Admin Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/admin/patients` | Get all patients with pagination |
| POST | `/admin/createDoctor` | Create Doctor |

**Query Parameters for `/admin/patients`:**
- `page`: Page number (default: 0)
- `Size`: Page size (default: 2)

---

## 🚀 Getting Started

### Prerequisites
- Java 21 or higher
- Maven 3.8.0 or higher
- MySQL 8.0 or higher

### Installation Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/shivansh806/Hospital_Management_System.git
   cd Hospital_Management_System
   ```

2. **Configure Database**
   - Create a MySQL database:
   ```sql
   CREATE DATABASE hospital_management;
   ```
   
   - Update `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/hospital_management
   spring.datasource.username=root
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

3. **Build the Project**
   ```bash
   mvn clean install
   ```

4. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```
   
   The server will start on `http://localhost:8080`

---

##  Architecture

The project follows a clean layered architecture:

Controller Layer
- Handles HTTP requests and API endpoints.

Service Layer
- Contains business logic and application workflows.

Repository Layer
- Manages database operations using Spring Data JPA.

DTO Layer
- Transfers request and response data securely.

Security Layer
- Handles JWT authentication, OAuth2 login, and authorization.

Exception Layer
- Provides centralized exception handling and standardized API errors.
  
## Security Design

This project follows a secure ownership-based access model.

Patients
- Can only access their own profile
- Can only manage their own appointments
- Cannot modify another patient's data

Doctors
- Can only access their own appointments
- Can only reassign appointments assigned to them
  
Authentication
- JWT-based stateless authentication
- OAuth2 login integration
- Method-level RBAC authorization

---

## 🔮 Future Enhancements

- Appointment Status Tracking
- Email Notifications
- Appointment Scheduling Calendar
- Video Consultation
- Payment Gateway Integration
- Prescription Management
- Docker Deployment
- Redis Caching
- Microservices Migration
  
---

## 📞 Support & Contribution

For issues, suggestions, or contributions, please create an issue or pull request on the repository.

---

## 📄 License

This project is open source and available under the MIT License.

---

**Last Updated:** May 12, 2026

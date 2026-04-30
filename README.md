# Hospital Management System

A comprehensive **Spring Boot REST API** for managing hospital operations including patients, doctors, appointments, insurance, and departments.

---

## 📋 Table of Contents

- [Project Overview](#project-overview)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Key Features](#key-features)
- [Database Schema](#database-schema)
- [API Endpoints](#api-endpoints)
- [Getting Started](#getting-started)
- [Usage Examples](#usage-examples)
- [Architecture](#architecture)
- [Future Enhancements](#future-enhancements)

---

## 🎯 Project Overview

The Hospital Management System is a robust REST API designed to streamline hospital operations. It provides comprehensive management of:
- Patient records and medical history
- Doctor profiles and specializations
- Appointment scheduling and management
- Insurance information tracking
- Department organization and management

Built with **Spring Boot 3.4.5** and **Java 21**, this application leverages modern Java features and Spring ecosystem best practices.

---

## 🛠️ Tech Stack

| Component | Technology |
|-----------|-----------|
| **Framework** | Spring Boot 3.4.5 |
| **Language** | Java 21 |
| **Build Tool** | Maven |
| **Database** | MySQL 8.0+ |
| **ORM** | JPA/Hibernate |
| **Data Mapping** | ModelMapper 3.2.0 |
| **Code Generator** | Lombok |
| **API Format** | REST (JSON) |

---

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/shivansh/code/hospitalManagement/
│   │   ├── HospitalManagementApplication.java          # Spring Boot Entry Point
│   │   ├── controller/                                  # REST Controllers
│   │   │   ├── PatientController.java
│   │   │   ├── AppointmentController.java
│   │   │   ├── AdminController.java
│   │   │   └── DoctorController.java
│   │   ├── service/                                     # Business Logic Layer
│   │   │   ├── PatientServices.java
│   │   │   ├── AppointmentServices.java
│   │   │   └── InsuranceServices.java
│   │   ├── repository/                                  # Data Access Layer
│   │   │   ├── PatientRepository.java
│   │   │   ├── DoctorRepository.java
│   │   │   ├── AppointmentRepository.java
│   │   │   ├── InsuranceRepository.java
│   │   │   └── DepartmentRepository.java
│   │   ├── entity/                                      # JPA Entities
│   │   │   ├── Patient.java
│   │   │   ├── Doctor.java
│   │   │   ├── Appointment.java
│   │   │   ├── Insurance.java
│   │   │   └── Department.java
│   │   ├── dto/                                         # Data Transfer Objects
│   │   │   ├── PatientResponseDto.java
│   │   │   ├── AppointmentResponseDto.java
│   │   │   ├── CreateAppointmentRequestDto.java
│   │   │   └── InsuranceRequestDto.java
│   │   ├── response/                                    # Response Wrapper Classes
│   │   │   └── ApiResponse.java
│   │   └── entity/type/                                 # Enums
│   │       ├── BloodGroup.java
│   │       └── GenderType.java
│   └── resources/
│       └── application.properties                        # Configuration
└── test/
    └── java/                                             # Unit & Integration Tests

pom.xml                                                   # Maven Dependencies
```

---

## ✨ Key Features

### 1. **Patient Management**
- Create and retrieve patient profiles
- Track patient blood groups and demographics
- Manage patient appointments and insurance
- Query patients by various criteria

### 2. **Doctor Management**
- Maintain doctor profiles with specialization
- Track doctor-department associations
- View doctors by department
- Manage doctor availability

### 3. **Appointment Scheduling**
- Create new appointments between patients and doctors
- Reassign appointments to different doctors
- Track appointment reasons and timestamps
- View appointment history

### 4. **Insurance Management**
- Link insurance policies to patients
- Store policy numbers and validity dates
- Manage insurance provider information
- Track insurance creation timestamps

### 5. **Department Organization**
- Organize doctors by departments
- Assign department head doctors
- Manage department-doctor relationships
- Track department information

### 6. **Admin Dashboard**
- View all patients with pagination
- Monitor system-wide statistics
- Access detailed patient records

---

## 🗄️ Database Schema

### Patient Entity
```
┌─────────────────────────────┐
│         PATIENT             │
├─────────────────────────────┤
│ id (PK)                     │
│ name                        │
│ birthDate                   │
│ email (UNIQUE)              │
│ gender (ENUM)               │
│ bloodGroup (ENUM)           │
│ createdAt (TIMESTAMP)       │
│ insurance_id (FK)           │
│ appointments (ONE-TO-MANY)  │
└─────────────────────────────┘
```

### Doctor Entity
```
┌──────────────────────────────┐
│         DOCTOR               │
├──────────────────────────────┤
│ id (PK)                      │
│ name                         │
│ specialization               │
│ email (UNIQUE)               │
│ departments (MANY-TO-MANY)   │
│ appointments (ONE-TO-MANY)   │
└──────────────────────────────┘
```

### Appointment Entity
```
┌────────────────────────────┐
│      APPOINTMENT           │
├────────────────────────────┤
│ id (PK)                    │
│ appointmentTime            │
│ reason                     │
│ patient_id (FK)            │
│ doctor_id (FK)             │
└────────────────────────────┘
```

### Insurance Entity
```
┌────────────────────────────┐
│      INSURANCE             │
├────────────────────────────┤
│ id (PK)                    │
│ policyNumber (UNIQUE)      │
│ provider                   │
│ validUntil                 │
│ createdAt (TIMESTAMP)      │
│ patient_id (FK)            │
└────────────────────────────┘
```

### Department Entity
```
┌────────────────────────────┐
│     DEPARTMENT             │
├────────────────────────────┤
│ id (PK)                    │
│ name (UNIQUE)              │
│ headDoctor_id (FK)         │
│ doctors (MANY-TO-MANY)     │
└────────────────────────────┘
```

---

## 🔌 API Endpoints

### Patient Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/patients/profile` | Get patient profile (hardcoded ID: 2) |
| POST | `/patients/{patientId}/insurance` | Add insurance to patient |
| DELETE | `/patients/{patientId}/insurance` | Remove insurance from patient |

### Appointment Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/appointments` | Create new appointment |
| PATCH | `/appointments/{appointmentId}/doctor/{doctorId}` | Reassign appointment to different doctor |

### Admin Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/admin/patients` | Get all patients with pagination |

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

## 📝 Usage Examples

### 1. Get Patient Profile
```bash
curl -X GET http://localhost:8080/patients/profile
```

**Response:**
```json
{
  "id": 2,
  "name": "John Doe",
  "gender": "MALE",
  "birthDate": "1990-05-15",
  "bloodGroup": "O_POSITIVE",
  "insurance": null
}
```

### 2. Create Appointment
```bash
curl -X POST http://localhost:8080/appointments \
  -H "Content-Type: application/json" \
  -d '{
    "patientId": 2,
    "doctorId": 1,
    "reason": "Regular checkup",
    "appointmentTime": "2026-05-15T10:30:00"
  }'
```

**Response:**
```json
{
  "message": "Appointment Created Successfully",
  "data": {
    "id": 1,
    "reason": "Regular checkup",
    "appointmentTime": "2026-05-15T10:30:00"
  }
}
```

### 3. Add Insurance
```bash
curl -X POST http://localhost:8080/patients/2/insurance \
  -H "Content-Type: application/json" \
  -d '{
    "policyNumber": "POL123456",
    "provider": "United Health",
    "validUntil": "2027-12-31"
  }'
```

**Response:**
```json
{
  "message": "Insurance Created Successfully",
  "data": {
    "policyNumber": "POL123456",
    "provider": "United Health",
    "validUntil": "2027-12-31"
  }
}
```

### 4. Reassign Appointment
```bash
curl -X PATCH http://localhost:8080/appointments/1/doctor/2
```

**Response:**
```json
{
  "message": "Appointment reassigned Successfully",
  "data": {
    "id": 1,
    "reason": "Regular checkup",
    "appointmentTime": "2026-05-15T10:30:00"
  }
}
```

### 5. Get All Patients (Admin)
```bash
curl -X GET "http://localhost:8080/admin/patients?page=0&Size=5"
```

**Response:**
```json
{
  "content": [
    {
      "id": 1,
      "name": "Jane Smith",
      "gender": "FEMALE",
      "birthDate": "1985-03-20",
      "bloodGroup": "B_POSITIVE"
    }
  ],
  "totalPages": 1,
  "totalElements": 1,
  "size": 5
}
```

---

## 🏗️ Architecture

The application follows a **3-Tier Layered Architecture**:

```
┌─────────────────────────────────────┐
│   Presentation Layer (Controller)   │
│  - REST endpoints                   │
│  - Request/Response handling        │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│   Service Layer (Business Logic)    │
│  - Business rules                   │
│  - Data processing                  │
│  - Transaction management           │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│   Repository Layer (Data Access)    │
│  - Database queries                 │
│  - JPA repositories                 │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│   Data Layer (Database)             │
│  - MySQL database                   │
└─────────────────────────────────────┘
```

**Benefits:**
- ✅ Separation of concerns
- ✅ Easy to test and maintain
- ✅ Reusable components
- ✅ Scalable architecture

---

## 🔮 Future Enhancements

### 1. **JWT Authorization & Authentication**
- [ ] Implement JWT token generation and validation
  - User registration and login endpoints
  - JWT token creation with configurable expiration
  - Token refresh mechanism
  - User role-based access control (RBAC)
- [ ] Add security dependencies:
  ```xml
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
  </dependency>
  <dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.3</version>
  </dependency>
  ```
- [ ] Create `User` entity with roles and permissions
- [ ] Implement `AuthController` for login/signup
- [ ] Add JWT filter for request validation

### 2. **Security Enhancements**
- [ ] **Password Security**
  - BCryptPasswordEncoder for password hashing
  - Password strength validation rules
  - Password change functionality
  
- [ ] **API Security**
  - HTTPS/SSL configuration
  - CORS policy configuration
  - Rate limiting to prevent abuse
  - Input validation and sanitization
  - CSRF protection
  
- [ ] **Authorization & Permissions**
  - Role-based access control (RBAC)
  - Admin, Doctor, Patient roles
  - Method-level security with @PreAuthorize
  - Resource-level access control
  
- [ ] **Audit & Logging**
  - AuditLog entity to track changes
  - User action logging
  - Request/response logging
  - Sensitive data masking in logs

### 3. **Global Exception Handling**
- [ ] Create centralized `GlobalExceptionHandler` class
  - `@RestControllerAdvice` for global exception handling
  - Custom exception classes:
    ```java
    - ResourceNotFoundException
    - ValidationException
    - UnauthorizedException
    - ForbiddenException
    - BadRequestException
    - InternalServerException
    ```
  
- [ ] Implement standardized error response format:
  ```json
  {
    "timestamp": "2026-05-15T10:30:00Z",
    "status": 404,
    "error": "NOT_FOUND",
    "message": "Patient not found with id 999",
    "path": "/patients/999"
  }
  ```
  
- [ ] Handle common exceptions:
  - `EntityNotFoundException`
  - `DataIntegrityViolationException`
  - `MethodArgumentNotValidException`
  - `HttpMessageNotReadableException`
  - `AccessDeniedException`
  - Generic `Exception`
  
- [ ] Add custom validation annotations:
  ```java
  - @ValidEmail
  - @ValidPhoneNumber
  - @ValidAge
  - @ValidDate
  ```
  
- [ ] Implement request/response validation with `@Valid` and `@Validated`

### 4. **Additional Enhancements**
- [ ] **Billing & Payments**
  - Invoice generation
  - Payment processing integration (Stripe/PayPal)
  - Billing history tracking
  
- [ ] **Notifications**
  - Email notifications for appointments
  - SMS reminders
  - Push notifications
  - Notification preferences management
  
- [ ] **Reporting & Analytics**
  - Patient statistics dashboard
  - Doctor performance metrics
  - Department utilization reports
  - Revenue reports
  
- [ ] **Appointment Management**
  - Appointment cancellation
  - Appointment rescheduling
  - Doctor availability calendar
  - Waiting list management
  
- [ ] **API Documentation**
  - Swagger/Springdoc OpenAPI integration
  - Detailed endpoint documentation
  - Request/response examples
  
- [ ] **Testing**
  - Unit tests for service layer
  - Integration tests for API endpoints
  - Test coverage > 80%
  
- [ ] **Caching**
  - Redis integration for caching
  - Cache invalidation strategies
  
- [ ] **Microservices** (Future)
  - Refactor into microservices architecture
  - Service mesh implementation
  - API Gateway

---

## 📞 Support & Contribution

For issues, suggestions, or contributions, please create an issue or pull request on the repository.

---

## 📄 License

This project is open source and available under the MIT License.

---

**Last Updated:** April 30, 2026

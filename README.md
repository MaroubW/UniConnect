# 🎓 UniConnect – Distributed University Management System

UniConnect is a **distributed university management platform** built using a **Service-Oriented Architecture (SOA)** and **microservices**.  
It integrates **REST** and **SOAP** services, secured with **JWT authentication**, and supports **Docker-based deployment**.

This project was developed as part of an academic module on **SOA & Distributed Systems**.

---

## 📌 Table of Contents

- [Project Overview](#project-overview)
- [Architecture](#architecture)
- [Microservices](#microservices)
- [Technologies](#technologies)
- [Security](#security)
- [Prerequisites](#prerequisites)
- [Installation & Execution](#installation--execution)
- [API Overview](#api-overview)
- [Testing](#testing)
- [Docker Deployment](#docker-deployment)
- [Project Planning](#project-planning)
- [Troubleshooting](#troubleshooting)
- [License](#license)

---

## 📖 Project Overview

UniConnect is designed to manage **students, courses, grades, authentication, and billing** in a university environment.

### Key Goals:
- Apply SOA and microservices concepts
- Combine REST and SOAP web services
- Secure APIs using JWT
- Enable scalability and maintainability
- Support containerized deployment

---

## 🧩 Architecture

The system follows a **distributed microservices architecture** with an **API Gateway**.

Client
|
v
API Gateway
|
|-- Auth Service (REST)
|-- Student Service (REST)
|-- Course Service (SOAP)
|-- Grade Service (REST)
|-- Billing Service (SOAP)

yaml
Copier le code

### Communication
- **REST** → JSON
- **SOAP** → XML

---

## 🔧 Microservices

| Service | Type | Technology | Description |
|------|------|-----------|------------|
| Auth Service | REST | Spring Boot | Authentication & JWT |
| Student Service | REST | Node.js + Express | Student management |
| Course Service | SOAP | Java (JAX-WS) | Course & schedule management |
| Grade Service | REST | FastAPI | Grades & GPA calculation |
| Billing Service | SOAP | .NET | Invoices & payments |
| API Gateway | Gateway | Spring Cloud Gateway | Routing & filtering |

---

## 🛠️ Technologies

- **Backend**
  - Node.js / Express
  - Spring Boot
  - Java JAX-WS (SOAP)
  - FastAPI (Python)
  - .NET (SOAP)

- **Database**
  - MongoDB

- **Security**
  - JWT Authentication
  - Role-based access control

- **Tools**
  - Docker & Docker Compose
  - Postman
  - Git & GitHub

---

## 🔐 Security

- JWT-based authentication
- Role management (ADMIN / USER)
- Middleware protection for sensitive routes
- CORS enabled
- Token validation at API Gateway level

---

## ⚙️ Prerequisites

Before running the project, make sure you have:

- Node.js **v18+**
- MongoDB (local or Docker)
- Java 11+
- Python 3.9+
- .NET SDK
- Docker (optional but recommended)
- Postman (for testing)

---

## 🚀 Installation & Execution

### 1️⃣ Clone the repository
```bash
git clone https://github.com/your-username/uniconnect.git
cd uniconnect
2️⃣ Student Service (Node.js)
bash
Copier le code
cd services/student-service
npm install
npm start
Environment file (.env)

env
Copier le code
PORT=4001
MONGO_URI=mongodb://127.0.0.1:27017/soa_project
JWT_SECRET=secretkey
3️⃣ Other Services
Each service can be started independently according to its technology stack:

Auth Service → Spring Boot

Course Service → Java SOAP (JAX-WS)

Grade Service → FastAPI (uvicorn)

Billing Service → .NET SOAP

🌐 API Overview
Auth Service (REST)
bash
Copier le code
POST /auth/register
POST /auth/login
Student Service (REST)
Method	Endpoint	Description
GET	/students	Get all students
GET	/students/{id}	Get student by ID
POST	/students	Add student
PUT	/students/{id}	Update student
DELETE	/students/{id}	Delete student

Course Service (SOAP)
getAllCourses()

getSchedule(studentId)

addCourse()

assignCourse()

Grade Service (REST)
bash
Copier le code
GET /grades/student/{id}
POST /grades
PUT /grades/{id}
Billing Service (SOAP)
getInvoiceList(studentId)

getBalance(studentId)

payInvoice(invoiceId)

🧪 Testing
Health Check
http
Copier le code
GET http://localhost:4001/health
Add Student (Postman)
h
Copier le code
POST http://localhost:4001/students
Content-Type: application/json
json
Copier le code
{
  "studentNumber": "2024-GL-001",
  "firstName": "Ahmed",
  "lastName": "Ben Ali",
  "email": "ahmed@example.com"
}
🐳 Docker Deployment
Build & Run All Services
bash
Copier le code
docker-compose up --build
Example service definition:

yaml
Copier le code
student-service:
  build: ./services/student-service
  ports:
    - "4001:4001"
Each microservice contains its own Dockerfile.

📅 Project Planning
The project was developed using Agile methodology, divided into multiple sprints:

Sprint 1: Architecture & Auth Service

Sprint 2: Student & Course Services

Sprint 3: Grades, Billing & Integration

Sprint 4: Docker & Final Testing

🧯 Troubleshooting
403 Forbidden → JWT token missing or insufficient role

MongoDB connection error → Check MONGO_URI

SOAP service unreachable → Verify WSDL endpoint

Port conflict → Update ports in .env or Docker Compose

📄 License
This project is intended for educational use.

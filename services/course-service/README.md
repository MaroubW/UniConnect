# SOA University Project

A comprehensive Service-Oriented Architecture (SOA) implementation for university management system.

## 🎯 Project Overview

This project implements a microservices-based university management system with the following services:
- **Authentication Service** (Spring Boot) - User management and JWT authentication
- **Student Service** (Node.js/Express) - Student CRUD operations
- **Course Service** (Java/JAX-WS) - Course and schedule management
- **Grade Service** (Python/FastAPI) - Grade management and GPA calculations
- **Billing Service** (.NET Core/SOAP) - Invoice and payment management
- **API Gateway** (Spring Cloud) - Request routing and load balancing

## 🏗️ Architecture

```
┌─────────────────┐    ┌─────────────────┐
│   API Gateway   │────│  Auth Service   │
│  (Spring Cloud) │    │  (Spring Boot)  │
└─────────────────┘    └─────────────────┘
          │                       │
          ├───────────────────────┼───────────────────────┐
          │                       │                       │
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│ Student Service │    │ Course Service  │    │ Grade Service   │
│ (Node.js/Mongo) │    │ (Java/SOAP)     │    │ (Python/Postgre) │
└─────────────────┘    └─────────────────┘    └─────────────────┘
          │
┌─────────────────┐
│ Billing Service │
│(.NET/SQL Server)│
└─────────────────┘
```

## 🚀 Quick Start

### Prerequisites
- Docker Desktop
- Docker Compose
- 8GB+ RAM for Docker

### 1. Clone and Setup
```bash
git clone <repository-url>
cd projet-soa-universite
```

### 2. Start the System
```bash
docker-compose -f docker/docker-compose.yml up -d
```

### 3. Check Health
```bash
# API Gateway
curl http://localhost:8080/actuator/health

# Individual services
curl http://localhost:8081/actuator/health  # Auth
curl http://localhost:8083/health           # Student
curl http://localhost:8082/course-service/  # Course
curl http://localhost:8084/health           # Grade
curl http://localhost:8085/health           # Billing
```

## 📁 Project Structure

```
projet-soa-universite/
├── documentation/           # Project documentation
│   ├── cahier-des-charges.md
│   ├── specifications-techniques.md
│   └── manuel-utilisation.md
├── services/               # Microservices
│   ├── auth-service/       # Authentication service
│   ├── student-service/    # Student management
│   ├── course-service/     # Course management (SOAP)
│   ├── grade-service/      # Grade management
│   ├── billing-service/    # Billing service (SOAP)
│   └── api-gateway/        # API Gateway
├── docker/                 # Docker configuration
│   ├── docker-compose.yml  # Complete system orchestration
│   └── README.md          # Docker setup guide
├── presentations/          # Presentation materials
│   ├── soutenance-finale.pptx.placeholder
│   ├── demo-video.mp4.placeholder
│   └── README.md
├── .env                    # Environment variables
└── README.md              # This file
```

## 🔧 Development

### Individual Service Development

Each service can be developed independently:

```bash
# Auth Service
cd services/auth-service
mvn spring-boot:run

# Student Service
cd services/student-service
npm install && npm start

# Course Service
cd services/course-service
mvn clean package && java -jar target/course-service-1.0.0.jar

# Grade Service
cd services/grade-service
pip install -r requirements.txt && python src/main.py

# Billing Service
cd services/billing-service
dotnet run

# API Gateway
cd services/api-gateway
mvn spring-boot:run
```

## 🧪 Testing

### API Testing
- **Postman Collection**: Import `documentation/postman_collection.json`
- **SOAP UI**: Import WSDL from `http://localhost:8082/course-service?wsdl`

### Health Checks
All services expose health endpoints for monitoring.

## 📊 Databases

- **Auth Service**: PostgreSQL (port 5432)
- **Student Service**: MongoDB (port 27017)
- **Grade Service**: PostgreSQL (port 5433)
- **Billing Service**: SQL Server (port 1433)

## 🔒 Security

- JWT-based authentication
- Role-based authorization (STUDENT, PROFESSOR, ADMIN)
- Password encryption with BCrypt
- Input validation and sanitization

## 📈 Monitoring

- Health checks for all services
- Centralized logging
- Docker container monitoring
- API Gateway request routing logs

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## 📝 Documentation

- [Cahier des Charges](documentation/cahier-des-charges.md)
- [Spécifications Techniques](documentation/specifications-techniques.md)
- [Manuel d'Utilisation](documentation/manuel-utilisation.md)
- [Docker Setup](docker/README.md)

## 🎓 Evaluation Criteria

### Technical Skills (13 points)
- SOA Architecture: 3 points
- RESTful/SOAP Services: 5 points
- Security: bonus
- Interoperability: 2 points
- Deployment/Containerization: 2 points

### Methodological Skills (8 points)
- Teamwork: 2 points
- Agile Project Management: bonus
- Documentation: 3 points
- Oral Presentation: 3 points

## 📞 Support

For questions or issues:
1. Check the documentation
2. Review service logs
3. Open an issue on GitHub

## 📄 License

This project is licensed under the MIT License.

---

**SOA University Project** - Comprehensive microservices implementation for educational management.
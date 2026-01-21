# Spécifications Techniques - Projet SOA Université

## Architecture Générale

### Pattern SOA (Service-Oriented Architecture)
- Services faiblement couplés
- Contrats d'interface explicites
- Réutilisabilité des services
- Orchestration via API Gateway

### Microservices
- Chaque service est indépendant
- Base de données dédiée par service
- Déploiement séparé
- Communication synchrone/asynchrone

## Spécifications par Service

### 1. Service Authentification (auth-service)

**Technologies**: Spring Boot, Spring Security, JWT, PostgreSQL

**Endpoints REST**:
```
POST /api/auth/login
POST /api/auth/register
POST /api/auth/refresh
GET /api/auth/validate
```

**Modèle de données**:
```json
{
  "id": "string",
  "username": "string",
  "email": "string",
  "role": "STUDENT|PROFESSOR|ADMIN",
  "enabled": "boolean"
}
```

**Sécurité**: JWT tokens, bcrypt password hashing

### 2. Service Étudiants (student-service)

**Technologies**: Node.js, Express, MongoDB

**Endpoints REST**:
```
GET /api/students
GET /api/students/{id}
POST /api/students
PUT /api/students/{id}
DELETE /api/students/{id}
```

**Modèle de données**:
```json
{
  "id": "string",
  "firstName": "string",
  "lastName": "string",
  "email": "string",
  "dateOfBirth": "date",
  "enrollmentDate": "date",
  "major": "string"
}
```

### 3. Service Cours (course-service)

**Technologies**: Java, JAX-WS, Tomcat, H2

**Opérations SOAP**:
- createCourse
- getCourseById
- getCourseByCode
- getAllCourses
- updateCourse
- deleteCourse
- addSchedule
- updateSchedule
- deleteSchedule
- getCourseSchedules
- searchCourses
- getAvailableCourses

**Modèle de données**:
```xml
<course>
  <id>long</id>
  <code>string</code>
  <name>string</name>
  <credits>integer</credits>
  <semester>string</semester>
  <capacity>integer</capacity>
</course>
```

### 4. Service Notes (grade-service)

**Technologies**: Python, FastAPI, PostgreSQL

**Endpoints REST**:
```
GET /api/grades/student/{studentId}
GET /api/grades/course/{courseId}
POST /api/grades
PUT /api/grades/{id}
GET /api/grades/average/{studentId}
```

**Modèle de données**:
```json
{
  "id": "integer",
  "studentId": "string",
  "courseId": "long",
  "grade": "float",
  "date": "date",
  "type": "EXAM|ASSIGNMENT|PROJECT"
}
```

### 5. Service Facturation (billing-service)

**Technologies**: .NET Core, SOAP, SQL Server

**Opérations SOAP**:
- CreateInvoice
- GetInvoice
- UpdateInvoice
- PayInvoice
- GetStudentInvoices

**Modèle de données**:
```xml
<invoice>
  <id>guid</id>
  <studentId>string</studentId>
  <amount>decimal</amount>
  <dueDate>date</dueDate>
  <status>UNPAID|PAID|OVERDUE</status>
</invoice>
```

### 6. API Gateway (api-gateway)

**Technologies**: Spring Cloud Gateway, Eureka

**Fonctionnalités**:
- Routage intelligent
- Load balancing
- Authentification JWT
- Rate limiting
- Logging centralisé

## Communication Inter-Services

### Protocoles
- REST pour services internes
- SOAP pour services externes
- Message queues (optionnel)

### Sécurité
- Authentification JWT
- Autorisation basée sur les rôles
- Chiffrement des communications
- Validation des entrées

## Bases de Données

### Auth Service: PostgreSQL
- Tables: users, roles, permissions

### Student Service: MongoDB
- Collections: students, enrollments

### Course Service: H2 (en mémoire)
- Tables: courses, schedules

### Grade Service: PostgreSQL
- Tables: grades, averages

### Billing Service: SQL Server
- Tables: invoices, payments

## Déploiement

### Conteneurisation Docker
- Image par service
- Multi-stage builds
- Optimisation des images

### Orchestration Docker Compose
- Services interconnectés
- Volumes persistants
- Réseau isolé
- Health checks

### Configuration
- Variables d'environnement
- Fichiers de configuration externes
- Secrets management

## Tests

### Tests Unitaires
- Couverture minimale 80%
- Mocks pour dépendances externes

### Tests d'Intégration
- Tests end-to-end
- Tests de contrats d'interface

### Tests de Performance
- Tests de charge
- Tests de montée en charge

## Monitoring et Logging

### Métriques
- Temps de réponse
- Taux d'erreur
- Utilisation ressources

### Logs
- Logs structurés
- Centralisation (ELK stack)
- Traçabilité des requêtes

## Sécurité

### Authentification
- JWT tokens
- Refresh tokens
- Expiration automatique

### Autorisation
- Rôles: STUDENT, PROFESSOR, ADMIN
- Permissions granulaires
- Validation côté serveur

### Protection
- CORS configuration
- Rate limiting
- Input validation
- SQL injection prevention
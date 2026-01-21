# Spécifications Techniques  
## Projet SOA – “UniConnect”

---

## 1. Architecture générale
Architecture SOA composée de microservices autonomes :  
- Auth (Spring Boot – REST)  
- Students (Node.js – REST)  
- Courses (Java JAX-WS – SOAP)  
- Grades (FastAPI – REST)  
- Billing (.NET Core – REST)  
- API Gateway (Spring Cloud Gateway)

Communications :  
- REST -> JSON  
- SOAP -> XML

---

## 2. Services

### 2.1 Auth Service
- JWT Authentication
- Rôles : ADMIN / STUDENT
- Endpoints :
  - POST /auth/register  
  - POST /auth/login  

---

### 2.2 Student Service
Techno : Node.js + Express  
Mongoose + MongoDB

#### Routes :
| Methode | Endpoint | Description |
|--------|----------|-------------|
| GET | /students | Liste |
| GET | /students/:id | Détails |
| POST | /students | Ajouter |
| PUT | /students/:id | Modifier |
| DELETE | /students/:id | Supprimer |

---

### 2.3 Course Service (SOAP)
Opérations :
- getAllCourses()
- getSchedule(studentId)
- addCourse()
- assignCourse()

---

### 2.4 Grade Service (FastAPI)
Routes :
- GET /grades/student/{id}
- POST /grades
- PUT /grades/{id}

Calcul :
- Moyenne par module
- GPA global

---

### 2.5 Billing Service (.NET SOAP)
Fonctions :
- getInvoiceList(studentId)
- getBalance(studentId)
- payInvoice(invoiceId)

---

### 2.6 API Gateway
Routing :
- /auth → Auth Service  
- /students → Node.js  
- /courses → SOAP Java  
- /grades → FastAPI  
- /billing → SOAP .NET  

---

## 3. Base de données
Exemple Student Service (MongoDB) :

| Champ | Type |
|------|------|
| studentNumber | String |
| firstName | String |
| lastName | String |
| email | String (unique) |
| dateOfBirth | Date |
| department | String |
| level | String |

---

## 4. Sécurité
- JWT + middleware
- CORS activé
- Restriction admin pour opérations sensibles

---

## 5. Déploiement (Docker)
Chaque service :  
- Dockerfile  
- docker-compose.yml à la racine  

Ex :
```yaml
student-service:
  build: ./services/student-service
  ports:
    - "4001:4001"

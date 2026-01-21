# Manuel d'Utilisation - Projet SOA Université

## Prérequis

### Environnement de Développement
- Docker Desktop
- Docker Compose
- Git
- IDE (VS Code, IntelliJ, etc.)

### Technologies Requises
- Java 11+
- Node.js 16+
- Python 3.8+
- .NET Core 6+
- Maven
- npm

## Installation et Configuration

### 1. Clonage du Repository
```bash
git clone <repository-url>
cd projet-soa-universite
```

### 2. Configuration des Variables d'Environnement

Créer un fichier `.env` à la racine du projet :
```env
# Base de données Auth Service
POSTGRES_DB=authdb
POSTGRES_USER=authuser
POSTGRES_PASSWORD=authpass

# Base de données Grade Service
GRADE_DB_HOST=grade-db
GRADE_DB_NAME=gradedb
GRADE_DB_USER=gradeuser
GRADE_DB_PASSWORD=gradepass

# JWT Secret
JWT_SECRET=your-secret-key-here

# API Gateway
GATEWAY_PORT=8080
```

### 3. Démarrage des Services

#### Démarrage Complet (Recommandé)
```bash
docker-compose -f docker/docker-compose.yml up -d
```

#### Démarrage Individuel

**Service Authentification**:
```bash
cd services/auth-service
mvn spring-boot:run
```

**Service Étudiants**:
```bash
cd services/student-service
npm install
npm start
```

**Service Cours**:
```bash
cd services/course-service
mvn clean package
docker build -t course-service .
docker run -p 8082:8080 course-service
```

**Service Notes**:
```bash
cd services/grade-service
pip install -r requirements.txt
uvicorn main:app --reload
```

**Service Facturation**:
```bash
cd services/billing-service
dotnet run
```

**API Gateway**:
```bash
cd services/api-gateway
mvn spring-boot:run
```

## Utilisation des Services

### Service Authentification

#### Inscription
```bash
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "student1",
    "email": "student1@university.com",
    "password": "password123",
    "role": "STUDENT"
  }'
```

#### Connexion
```bash
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "student1",
    "password": "password123"
  }'
```

### Service Étudiants

#### Créer un étudiant
```bash
curl -X POST http://localhost:8083/api/students \
  -H "Authorization: Bearer <jwt-token>" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@university.com",
    "major": "Computer Science"
  }'
```

#### Récupérer tous les étudiants
```bash
curl -X GET http://localhost:8083/api/students \
  -H "Authorization: Bearer <jwt-token>"
```

### Service Cours (SOAP)

#### Exemple de requête SOAP
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:ser="http://service.course.soa.university.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:createCourse>
         <course>
            <code>CS101</code>
            <name>Introduction to Programming</name>
            <credits>3</credits>
            <semester>Fall 2024</semester>
            <capacity>30</capacity>
         </course>
      </ser:createCourse>
   </soapenv:Body>
</soapenv:Envelope>
```

Utiliser un outil comme SoapUI ou Postman pour envoyer des requêtes SOAP à :
`http://localhost:8082/course-service/services/course?wsdl`

### Service Notes

#### Ajouter une note
```bash
curl -X POST http://localhost:8084/api/grades \
  -H "Authorization: Bearer <jwt-token>" \
  -H "Content-Type: application/json" \
  -d '{
    "studentId": "student123",
    "courseId": 1,
    "grade": 85.5,
    "type": "EXAM"
  }'
```

#### Calculer la moyenne d'un étudiant
```bash
curl -X GET http://localhost:8084/api/grades/average/student123 \
  -H "Authorization: Bearer <jwt-token>"
```

### Service Facturation (SOAP)

#### Créer une facture
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:bill="http://service.billing.soa.university.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <bill:CreateInvoice>
         <invoice>
            <studentId>student123</studentId>
            <amount>1500.00</amount>
            <dueDate>2024-12-31</dueDate>
         </invoice>
      </bill:CreateInvoice>
   </soapenv:Body>
</soapenv:Envelope>
```

### API Gateway

Toutes les requêtes passent par le gateway :
```
http://localhost:8080/
```

#### Exemples :
- `GET /auth/login` → Service Authentification
- `GET /students` → Service Étudiants
- `POST /grades` → Service Notes

## Tests

### Tests Unitaires

**Service Authentification**:
```bash
cd services/auth-service
mvn test
```

**Service Étudiants**:
```bash
cd services/student-service
npm test
```

**Service Cours**:
```bash
cd services/course-service
mvn test
```

**Service Notes**:
```bash
cd services/grade-service
pytest
```

**Service Facturation**:
```bash
cd services/billing-service
dotnet test
```

### Tests d'Intégration

Utiliser Postman avec la collection fournie dans `documentation/postman_collection.json`

## Monitoring

### Health Checks
- Auth Service: `http://localhost:8081/actuator/health`
- Student Service: `http://localhost:8083/health`
- Course Service: `http://localhost:8082/course-service/`
- Grade Service: `http://localhost:8084/health`
- Billing Service: `http://localhost:8085/health`
- API Gateway: `http://localhost:8080/actuator/health`

### Logs
```bash
# Voir les logs de tous les services
docker-compose -f docker/docker-compose.yml logs -f

# Logs d'un service spécifique
docker-compose -f docker/docker-compose.yml logs -f auth-service
```

## Dépannage

### Problèmes Courants

#### Port déjà utilisé
```bash
# Vérifier les ports utilisés
netstat -tulpn | grep :808

# Changer les ports dans docker-compose.yml
```

#### Service ne démarre pas
```bash
# Vérifier les logs
docker-compose -f docker/docker-compose.yml logs <service-name>

# Redémarrer un service
docker-compose -f docker/docker-compose.yml restart <service-name>
```

#### Erreur de connexion à la base de données
```bash
# Vérifier que les bases de données sont démarrées
docker ps | grep db

# Vérifier les variables d'environnement
cat .env
```

### Commandes Utiles

#### Arrêter tous les services
```bash
docker-compose -f docker/docker-compose.yml down
```

#### Nettoyer les volumes
```bash
docker-compose -f docker/docker-compose.yml down -v
```

#### Reconstruire les images
```bash
docker-compose -f docker/docker-compose.yml build --no-cache
```

## Support

Pour toute question ou problème :
1. Consulter les logs des services
2. Vérifier la documentation technique
3. Ouvrir une issue sur le repository Git

## Annexes

### URLs des Services
- API Gateway: http://localhost:8080
- Auth Service: http://localhost:8081
- Student Service: http://localhost:8083
- Course Service: http://localhost:8082
- Grade Service: http://localhost:8084
- Billing Service: http://localhost:8085

### Ports par Défaut
- PostgreSQL (Auth): 5432
- MongoDB (Student): 27017
- PostgreSQL (Grade): 5433
- SQL Server (Billing): 1433
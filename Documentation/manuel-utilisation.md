# 📙 Manuel d'Utilisation -- Projet SOA "UniConnect"

(VERSION FUSIONNÉE ET COMPLÈTE)

## 1. Introduction

Ce manuel explique comment installer, lancer et tester le Student
Service, utiliser Postman, une page HTML et résoudre les erreurs
courantes.

## 2. Pré-requis

-   Node.js (v18+)
-   MongoDB
-   Postman
-   Navigateur Web
-   Docker (optionnel)

## 3. Installation du projet

### 3.1 Ouvrir le projet

    cd project

### 3.2 Installer les dépendances

    npm install

### 3.3 Fichier .env

    PORT=4001
    MONGO_URI=mongodb://127.0.0.1:27017/soa_project
    JWT_SECRET=changeme

## 4. Lancer le Student Service

### Mode normal

    npm start

### Mode développement (auto-restart)

    npm run dev

## 5. Tester via Postman

### 5.1 Health check

    GET http://localhost:4001/health

### 5.2 Ajouter un étudiant

    POST http://localhost:4001/students

Body :

``` json
{
  "studentNumber": "2024-GL-001",
  "firstName": "Ahmed",
  "lastName": "Ben Ali",
  "email": "ahmed@example.com"
}
```

## 6. Tester via page HTML

``` html
<script>
async function loadStudents() {
  const res = await fetch("http://localhost:4001/students");
  const data = await res.json();
  console.log(data);
}
loadStudents();
</script>
```

## 7. Erreurs fréquentes

### 403 Forbidden

Token sans rôle ADMIN.

### MongoDB error

Corriger :

    MONGO_URI=mongodb://127.0.0.1:27017/soa_project

## 8. Arrêter le service

    CTRL + C

## 9. Docker (phase finale)

    docker-compose up --build

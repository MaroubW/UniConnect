# Cahier des Charges  
## Projet SOA – Plateforme Universitaire “UniConnect”

---

## 1. Contexte du projet
Ce projet s'inscrit dans le cadre du module *Architecture SOA et Services Web*.  
L’objectif est de développer une plateforme universitaire distribuée basée sur une architecture orientée services (SOA).

---

## 2. Objectifs
- Concevoir une architecture SOA modulaire.
- Implémenter des services REST et SOAP.
- Garantir l'interopérabilité entre technologies.
- Assurer la sécurité via JWT.
- Conteneuriser l’ensemble via Docker.
- Fournir documentation et présentation finale.

---

## 3. Description générale
Le système “UniConnect” comprend les services suivants :
- Authentification (Spring Boot)
- Gestion étudiants (Node.js / Express)
- Gestion des cours (Java JAX-WS – SOAP)
- Gestion des notes (Python FastAPI)
- Facturation (SOAP – .NET Core)
- API Gateway (Spring Cloud Gateway)

---

## 4. Fonctionnalités
### 4.1 Étudiants
- CRUD
- Recherche par département, niveau

### 4.2 Cours
- Gestion cours
- Emploi du temps
- Assignation étudiant–cours

### 4.3 Notes
- Gestion notes
- Calcul moyennes & GPA

### 4.4 Authentification
- Login / Register
- JWT et rôles (ADMIN / STUDENT / MANAGER / TEACHER)

### 4.5 Facturation
- Gestion factures
- Paiement
- Historique

---

## 5. Contraintes techniques
- SOA
- REST + SOAP
- Technologies imposées : Spring Boot, Node.js, Java, FastAPI, .NET Core
- Docker obligatoire
- Base de données : MongoDB / MySQL / PostgreSQL au choix

---

## 6. Livrables
- Code source complet
- Documentation technique
- Manuel utilisateur
- Présentation
- Docker Compose
- Vidéo de démonstration

---

## 7. Planning
| Tâche | Durée | Responsable |
|------|--------|-------------|
| Setup projet | 1j | Équipe |
| Student Service | 2j | Dev 1 |
| Auth Service | 2j | Dev 2 |
| Course Service | 2j | Dev 1 |
| Gateway | 1j | Dev 2 |
| Tests + Docs | 2j | Équipe |

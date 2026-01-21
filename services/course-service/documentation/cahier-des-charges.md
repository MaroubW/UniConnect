# Cahier des Charges - Projet SOA Université

## Présentation du Projet

Développement d'une architecture orientée services (SOA) pour la gestion d'une université, permettant la gestion des étudiants, cours, notes et facturation de manière distribuée et interopérable.

## Objectifs

- Concevoir une architecture SOA modulaire et scalable
- Développer des services web RESTful et SOAP
- Assurer l'interopérabilité entre différents systèmes
- Implémenter la sécurité et l'authentification
- Conteneuriser les services avec Docker
- Déployer via orchestration Docker Compose

## Services à Développer

### Service Authentification (REST - Spring Boot)
- Gestion des utilisateurs (étudiants, professeurs, administrateurs)
- Authentification JWT
- Autorisation basée sur les rôles

### Service Étudiants (REST - Node.js/Express)
- CRUD des étudiants
- Gestion des informations personnelles
- Intégration avec le service d'authentification

### Service Cours (SOAP - Java/JAX-WS)
- Gestion des cours et emplois du temps
- Inscription des étudiants aux cours
- Gestion des professeurs et salles

### Service Notes (REST - Python/FastAPI)
- Gestion des notes et moyennes
- Calcul automatique des moyennes
- Historique des résultats

### Service Facturation (SOAP - .NET Core)
- Gestion des frais universitaires
- Génération de factures
- Suivi des paiements

### API Gateway (Spring Cloud)
- Routage intelligent des requêtes
- Agrégation de services
- Load balancing et sécurité

## Technologies Utilisées

- **Auth Service**: Spring Boot, Spring Security, JWT, PostgreSQL
- **Student Service**: Node.js, Express, MongoDB
- **Course Service**: Java, JAX-WS, Tomcat, H2
- **Grade Service**: Python, FastAPI, PostgreSQL
- **Billing Service**: .NET Core, SOAP, SQL Server
- **API Gateway**: Spring Cloud Gateway, Eureka
- **Conteneurisation**: Docker, Docker Compose

## Contraintes Techniques

- Architecture microservices
- Communication REST/SOAP selon les besoins
- Base de données adaptée à chaque service
- Sécurité JWT pour l'authentification
- Tests unitaires et d'intégration
- Documentation API complète

## Livrables

- Code source complet des services
- Documentation technique
- Fichiers Docker et docker-compose
- Tests et démonstration
- Présentation finale

## Échéances

- Phase 1: Conception et spécifications (Semaine 1-2)
- Phase 2: Développement des services de base (Semaine 3-6)
- Phase 3: Intégration et sécurité (Semaine 7-8)
- Phase 4: Tests et déploiement (Semaine 9-10)
- Phase 5: Présentation finale (Semaine 11)

## Critères d'Évaluation

### Compétences Techniques (13 points)
- Architecture SOA: 3 points
- Services web RESTful/SOAP: 5 points
- Sécurité: bonus
- Interopérabilité: 2 points
- Déploiement/Conteneurisation: 2 points

### Compétences Méthodologiques (8 points)
- Travail en équipe: 2 points
- Gestion de projet agile: bonus
- Documentation: 3 points
- Présentation: 3 points
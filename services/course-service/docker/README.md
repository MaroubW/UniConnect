# Docker Setup for SOA University Project

This directory contains the Docker configuration for the complete SOA University system.

## Architecture

The system consists of:
- 5 microservices (auth, student, course, grade, billing)
- 1 API Gateway
- 4 databases (PostgreSQL x2, MongoDB, SQL Server)
- 1 Eureka server for service discovery

## Prerequisites

- Docker Desktop
- Docker Compose
- At least 8GB RAM allocated to Docker
- At least 10GB free disk space

## Environment Variables

Create a `.env` file in the project root with the following variables:

```env
# Database credentials
POSTGRES_USER=authuser
POSTGRES_PASSWORD=authpass
GRADE_DB_USER=gradeuser
GRADE_DB_PASSWORD=gradepass
BILLING_DB_PASSWORD=Billing123!

# JWT Secret
JWT_SECRET=your-super-secret-jwt-key-here

# Optional: Custom ports
AUTH_SERVICE_PORT=8081
STUDENT_SERVICE_PORT=8083
COURSE_SERVICE_PORT=8082
GRADE_SERVICE_PORT=8084
BILLING_SERVICE_PORT=8085
GATEWAY_PORT=8080
```

## Starting the System

### Complete System
```bash
docker-compose -f docker/docker-compose.yml up -d
```

### Individual Services
```bash
# Start only databases
docker-compose -f docker/docker-compose.yml up -d auth-db student-db grade-db billing-db

# Start services one by one
docker-compose -f docker/docker-compose.yml up -d auth-service
docker-compose -f docker/docker-compose.yml up -d student-service
# ... etc
```

## Checking System Health

### All services health
```bash
docker-compose -f docker/docker-compose.yml ps
```

### Individual service logs
```bash
docker-compose -f docker/docker-compose.yml logs -f auth-service
```

### Health endpoints
- API Gateway: http://localhost:8080/actuator/health
- Auth Service: http://localhost:8081/actuator/health
- Student Service: http://localhost:8083/health
- Course Service: http://localhost:8082/course-service/
- Grade Service: http://localhost:8084/health
- Billing Service: http://localhost:8085/health

## Database Access

### PostgreSQL (Auth Service)
```bash
docker exec -it auth-db psql -U authuser -d authdb
```

### MongoDB (Student Service)
```bash
docker exec -it student-db mongosh studentdb
```

### SQL Server (Billing Service)
```bash
docker exec -it billing-db /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P Billing123!
```

## Troubleshooting

### Common Issues

#### Port conflicts
```bash
# Check used ports
netstat -tulpn | grep :808

# Stop conflicting services or change ports in docker-compose.yml
```

#### Database connection failures
```bash
# Check database logs
docker-compose -f docker/docker-compose.yml logs auth-db

# Restart database
docker-compose -f docker/docker-compose.yml restart auth-db
```

#### Service startup failures
```bash
# Check service logs
docker-compose -f docker/docker-compose.yml logs auth-service

# Check dependencies
docker-compose -f docker/docker-compose.yml up -d auth-db
```

### Reset System
```bash
# Stop all services
docker-compose -f docker/docker-compose.yml down

# Remove volumes (WARNING: This deletes all data)
docker-compose -f docker/docker-compose.yml down -v

# Rebuild and start
docker-compose -f docker/docker-compose.yml up -d --build
```

## Development

### Building Individual Services
```bash
# Build specific service
docker-compose -f docker/docker-compose.yml build auth-service

# Build all services
docker-compose -f docker/docker-compose.yml build
```

### Scaling Services
```bash
# Scale a service (example: 3 instances of student-service)
docker-compose -f docker/docker-compose.yml up -d --scale student-service=3
```

## Monitoring

### Resource Usage
```bash
docker stats
```

### Container Logs
```bash
# All containers
docker-compose -f docker/docker-compose.yml logs -f

# Specific container
docker-compose -f docker/docker-compose.yml logs -f auth-service
```

## Backup and Restore

### Database Backups
```bash
# PostgreSQL backup
docker exec auth-db pg_dump -U authuser authdb > auth_backup.sql

# MongoDB backup
docker exec student-db mongodump --db studentdb --out /backup
docker cp student-db:/backup ./mongo_backup

# SQL Server backup
docker exec billing-db /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P Billing123! -Q "BACKUP DATABASE billingdb TO DISK = '/var/opt/mssql/backup/billingdb.bak'"
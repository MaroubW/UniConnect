# Course Service - Quick Start Guide

## 🚀 Get Started in 5 Minutes

This guide will help you quickly deploy and test the Course Service.

## Prerequisites

Choose one of the following options:

### Option A: Docker (Recommended)
- Docker installed
- Docker Compose installed

### Option B: Manual Deployment
- Java 11 or higher
- Maven 3.6+
- Apache Tomcat 9

## Quick Start with Docker

### Step 1: Build and Run

```bash
cd services/course-service
docker-compose up -d
```

### Step 2: Verify Service

Open your browser and navigate to:
```
http://localhost:8082/course-service/
```

You should see the Course Service welcome page.

### Step 3: Access WSDL

```
http://localhost:8082/course-service/services/course?wsdl
```

### Step 4: Test with cURL

```bash
curl -X POST http://localhost:8082/course-service/services/course \
  -H "Content-Type: text/xml" \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.course.soa.university.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:getAllCourses/>
   </soapenv:Body>
</soapenv:Envelope>'
```

## Quick Start with Maven & Tomcat

### Step 1: Build

```bash
cd services/course-service
mvn clean package
```

### Step 2: Deploy

```bash
cp target/course-service.war $TOMCAT_HOME/webapps/
```

### Step 3: Start Tomcat

```bash
$TOMCAT_HOME/bin/catalina.sh run
```

### Step 4: Access Service

```
http://localhost:8080/course-service/
```

## Sample Operations

### 1. Get All Courses

**Request:**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://service.course.soa.university.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:getAllCourses/>
   </soapenv:Body>
</soapenv:Envelope>
```

**cURL:**
```bash
curl -X POST http://localhost:8080/course-service/services/course \
  -H "Content-Type: text/xml" \
  -d @- << EOF
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.course.soa.university.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:getAllCourses/>
   </soapenv:Body>
</soapenv:Envelope>
EOF
```

### 2. Get Course by ID

**Request:**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://service.course.soa.university.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:getCourseById>
         <courseId>1</courseId>
      </ser:getCourseById>
   </soapenv:Body>
</soapenv:Envelope>
```

### 3. Create a New Course

**Request:**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://service.course.soa.university.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:createCourse>
         <course>
            <code>CS401</code>
            <name>Advanced Programming</name>
            <description>Advanced programming concepts</description>
            <credits>4</credits>
            <semester>Spring 2025</semester>
            <professorId>1</professorId>
            <professorName>Dr. Johnson</professorName>
            <capacity>30</capacity>
         </course>
      </ser:createCourse>
   </soapenv:Body>
</soapenv:Envelope>
```

### 4. Search Courses

**Request:**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://service.course.soa.university.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:searchCourses>
         <searchTerm>Computer</searchTerm>
      </ser:searchCourses>
   </soapenv:Body>
</soapenv:Envelope>
```

## Testing with SoapUI

### Step 1: Create New SOAP Project
1. Open SoapUI
2. File → New SOAP Project
3. Project Name: `CourseService`
4. Initial WSDL: `http://localhost:8080/course-service/services/course?wsdl`
5. Click OK

### Step 2: Test Operations
1. Expand the project tree
2. Select an operation (e.g., `getAllCourses`)
3. Double-click on `Request 1`
4. Click the green play button to send request
5. View the response

## Testing with Postman

### Step 1: Create New Request
1. Click "New" → "Request"
2. Name: `Get All Courses`
3. Method: `POST`
4. URL: `http://localhost:8080/course-service/services/course`

### Step 2: Configure Headers
Add header:
- Key: `Content-Type`
- Value: `text/xml`

### Step 3: Add Body
Select "raw" and paste SOAP XML:
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://service.course.soa.university.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:getAllCourses/>
   </soapenv:Body>
</soapenv:Envelope>
```

### Step 4: Send Request
Click "Send" and view the response.

## Pre-loaded Sample Data

The service comes with sample data:

### Courses
1. **CS101** - Introduction to Computer Science (3 credits)
2. **MATH201** - Calculus II (4 credits)
3. **ENG101** - English Composition (3 credits)

### Schedules
- CS101: Monday & Wednesday, 09:00-10:30, Room 101
- MATH201: Tuesday & Thursday, 14:00-16:00, Room 205

## Common Issues

### Port Already in Use
If port 8080 (or 8082 for Docker) is in use:

**Docker:**
Edit `docker-compose.yml`:
```yaml
ports:
  - "8083:8080"  # Change 8082 to 8083
```

**Tomcat:**
Edit `$TOMCAT_HOME/conf/server.xml` and change the port.

### Service Not Starting
Check logs:
- **Docker**: `docker logs course-service`
- **Tomcat**: `$TOMCAT_HOME/logs/catalina.out`

### WSDL Not Accessible
1. Verify service is running
2. Check URL is correct
3. Ensure no firewall blocking

## Next Steps

1. ✅ Service is running
2. 📖 Read [API_DOCUMENTATION.md](API_DOCUMENTATION.md) for detailed API reference
3. 📚 Check [README.md](README.md) for comprehensive documentation
4. 🧪 Explore all 14 operations
5. 🔧 Customize for your needs

## Useful Commands

### Docker Commands
```bash
# Start service
docker-compose up -d

# Stop service
docker-compose down

# View logs
docker logs -f course-service

# Rebuild
docker-compose up -d --build
```

### Maven Commands
```bash
# Build
mvn clean package

# Run tests
mvn test

# Clean
mvn clean
```

## Support

Need help?
- Check the [README.md](README.md)
- Review [API_DOCUMENTATION.md](API_DOCUMENTATION.md)
- Examine the WSDL file
- Look at sample requests in this guide

## What's Next?

Now that your service is running, you can:
- Integrate with other services (Auth, Student, Grade, Billing)
- Add database persistence
- Implement security
- Add monitoring and logging
- Create a client application

---

**Congratulations! Your Course Service is ready to use! 🎉**
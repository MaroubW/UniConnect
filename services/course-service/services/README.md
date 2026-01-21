# Course Service - SOAP Web Service

## 📋 Description

The Course Service is a SOAP-based web service built with Java and JAX-WS for managing university courses and schedules. It provides comprehensive CRUD operations for courses and their associated schedules.

## 🏗️ Architecture

- **Technology**: Java 11, JAX-WS, SOAP
- **Build Tool**: Maven
- **Server**: Apache Tomcat 9
- **Data Storage**: In-memory (ConcurrentHashMap)
- **Containerization**: Docker

## 📁 Project Structure

```
course-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/university/soa/course/
│   │   │       ├── model/
│   │   │       │   ├── Course.java
│   │   │       │   ├── Schedule.java
│   │   │       │   ├── CourseResponse.java
│   │   │       │   └── CourseListResponse.java
│   │   │       ├── repository/
│   │   │       │   └── CourseRepository.java
│   │   │       └── service/
│   │   │           ├── CourseService.java (Interface)
│   │   │           └── CourseServiceImpl.java
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   ├── web.xml
│   │       │   └── sun-jaxws.xml
│   │       └── index.html
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

## 🚀 Features

### Course Management
- ✅ Create new courses
- ✅ Retrieve courses by ID or code
- ✅ List all courses
- ✅ Filter courses by semester
- ✅ Filter courses by professor
- ✅ Update course information
- ✅ Delete courses
- ✅ Search courses by name/code
- ✅ Get available courses (with capacity)

### Schedule Management
- ✅ Add schedules to courses
- ✅ Update schedules
- ✅ Delete schedules
- ✅ Retrieve course schedules

## 🔧 Prerequisites

- Java 11 or higher
- Maven 3.6+
- Docker (optional, for containerization)
- Apache Tomcat 9 (if not using Docker)

## 📦 Installation & Deployment

### Option 1: Using Maven and Tomcat

1. **Build the project**:
```bash
cd services/course-service
mvn clean package
```

2. **Deploy to Tomcat**:
```bash
cp target/course-service.war $TOMCAT_HOME/webapps/
```

3. **Start Tomcat**:
```bash
$TOMCAT_HOME/bin/catalina.sh run
```

4. **Access the service**:
- Service URL: `http://localhost:8080/course-service/`
- WSDL: `http://localhost:8080/course-service/services/course?wsdl`

### Option 2: Using Docker

1. **Build and run with Docker Compose**:
```bash
cd services/course-service
docker-compose up -d
```

2. **Access the service**:
- Service URL: `http://localhost:8082/course-service/`
- WSDL: `http://localhost:8082/course-service/services/course?wsdl`

### Option 3: Using Docker manually

1. **Build the Docker image**:
```bash
docker build -t course-service:1.0 .
```

2. **Run the container**:
```bash
docker run -d -p 8082:8080 --name course-service course-service:1.0
```

## 📡 API Endpoints

### Service Information
- **WSDL URL**: `http://localhost:8080/course-service/services/course?wsdl`
- **Service Endpoint**: `http://localhost:8080/course-service/services/course`
- **Namespace**: `http://service.course.soa.university.com/`

### Available Operations

| Operation | Description |
|-----------|-------------|
| `createCourse` | Create a new course |
| `getCourseById` | Get course by ID |
| `getCourseByCode` | Get course by code |
| `getAllCourses` | Get all courses |
| `getCoursesBySemester` | Get courses by semester |
| `getCoursesByProfessor` | Get courses by professor |
| `updateCourse` | Update a course |
| `deleteCourse` | Delete a course |
| `addSchedule` | Add schedule to course |
| `updateSchedule` | Update a schedule |
| `deleteSchedule` | Delete a schedule |
| `getCourseSchedules` | Get course schedules |
| `searchCourses` | Search courses |
| `getAvailableCourses` | Get available courses |

## 📝 SOAP Request Examples

### 1. Get All Courses

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://service.course.soa.university.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:getAllCourses/>
   </soapenv:Body>
</soapenv:Envelope>
```

### 2. Get Course by ID

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

### 3. Create Course

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://service.course.soa.university.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:createCourse>
         <course>
            <code>CS301</code>
            <name>Data Structures</name>
            <description>Advanced data structures and algorithms</description>
            <credits>4</credits>
            <semester>Fall 2024</semester>
            <professorId>1</professorId>
            <professorName>Dr. Smith</professorName>
            <capacity>30</capacity>
         </course>
      </ser:createCourse>
   </soapenv:Body>
</soapenv:Envelope>
```

### 4. Add Schedule

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://service.course.soa.university.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:addSchedule>
         <schedule>
            <courseId>1</courseId>
            <dayOfWeek>MONDAY</dayOfWeek>
            <startTime>09:00</startTime>
            <endTime>10:30</endTime>
            <room>Room 101</room>
            <building>Building A</building>
         </schedule>
      </ser:addSchedule>
   </soapenv:Body>
</soapenv:Envelope>
```

### 5. Search Courses

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

## 🧪 Testing

### Using cURL

```bash
curl -X POST \
  http://localhost:8080/course-service/services/course \
  -H 'Content-Type: text/xml' \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.course.soa.university.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:getAllCourses/>
   </soapenv:Body>
</soapenv:Envelope>'
```

### Using SoapUI

1. Create a new SOAP project
2. Import WSDL: `http://localhost:8080/course-service/services/course?wsdl`
3. Test operations from the generated requests

### Using Postman

1. Create a new request
2. Set method to POST
3. Set URL to: `http://localhost:8080/course-service/services/course`
4. Set Content-Type header to `text/xml`
5. Add SOAP XML in the body
6. Send request

## 📊 Data Models

### Course
```java
{
    "id": Long,
    "code": String,
    "name": String,
    "description": String,
    "credits": Integer,
    "semester": String,
    "professorId": Long,
    "professorName": String,
    "capacity": Integer,
    "enrolledStudents": Integer,
    "schedules": List<Schedule>
}
```

### Schedule
```java
{
    "id": Long,
    "courseId": Long,
    "dayOfWeek": String,
    "startTime": String,
    "endTime": String,
    "room": String,
    "building": String
}
```

## 🔒 Security Considerations

For production deployment, consider implementing:
- WS-Security for authentication
- SSL/TLS encryption
- Input validation and sanitization
- Rate limiting
- API key authentication

## 🐛 Troubleshooting

### Service not starting
- Check if port 8080 is available
- Verify Java 11 is installed
- Check Tomcat logs: `$TOMCAT_HOME/logs/catalina.out`

### WSDL not accessible
- Ensure service is deployed correctly
- Check `sun-jaxws.xml` configuration
- Verify `web.xml` servlet mapping

### Docker issues
- Ensure Docker daemon is running
- Check container logs: `docker logs course-service`
- Verify port 8082 is not in use

## 📈 Performance

- In-memory storage for fast access
- Thread-safe operations using ConcurrentHashMap
- Optimized for read-heavy workloads
- Supports concurrent requests

## 🔄 Future Enhancements

- [ ] Database integration (PostgreSQL/MySQL)
- [ ] Authentication and authorization
- [ ] Caching layer (Redis)
- [ ] Logging and monitoring
- [ ] Unit and integration tests
- [ ] API versioning
- [ ] Swagger/OpenAPI documentation

## 📞 Support

For issues or questions:
- Check the WSDL documentation
- Review the sample requests
- Consult the technical documentation

## 📄 License

This project is part of the SOA University Project.

## 👥 Contributors

- Development Team
- SOA Course Project

---

**Version**: 1.0.0  
**Last Updated**: 2024  
**Technology Stack**: Java 11, JAX-WS, SOAP, Maven, Docker, Tomcat
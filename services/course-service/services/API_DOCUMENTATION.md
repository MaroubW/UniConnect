# Course Service - API Documentation

## Table of Contents
1. [Overview](#overview)
2. [Service Information](#service-information)
3. [Data Models](#data-models)
4. [Operations](#operations)
5. [Error Handling](#error-handling)
6. [Examples](#examples)

## Overview

The Course Service is a SOAP web service that provides comprehensive course and schedule management functionality for a university system.

### Key Features
- Complete CRUD operations for courses
- Schedule management for courses
- Search and filter capabilities
- Capacity tracking for courses
- Semester-based organization

## Service Information

### Endpoints
- **Service URL**: `http://localhost:8080/course-service/services/course`
- **WSDL URL**: `http://localhost:8080/course-service/services/course?wsdl`
- **Namespace**: `http://service.course.soa.university.com/`

### Protocol
- **Type**: SOAP 1.1/1.2
- **Style**: Document/Literal
- **Encoding**: UTF-8

## Data Models

### Course

Represents a university course with all its details.

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| id | Long | Yes | Unique identifier |
| code | String | Yes | Course code (e.g., "CS101") |
| name | String | Yes | Course name |
| description | String | No | Course description |
| credits | Integer | Yes | Number of credits |
| semester | String | Yes | Semester (e.g., "Fall 2024") |
| professorId | Long | No | Professor's ID |
| professorName | String | No | Professor's name |
| capacity | Integer | No | Maximum students |
| enrolledStudents | Integer | No | Current enrollment |
| schedules | List<Schedule> | No | Course schedules |

### Schedule

Represents a course schedule/timetable entry.

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| id | Long | Yes | Unique identifier |
| courseId | Long | Yes | Associated course ID |
| dayOfWeek | String | Yes | Day (MONDAY, TUESDAY, etc.) |
| startTime | String | Yes | Start time (HH:mm format) |
| endTime | String | Yes | End time (HH:mm format) |
| room | String | Yes | Room number |
| building | String | No | Building name |

### CourseResponse

Standard response wrapper for single course operations.

| Field | Type | Description |
|-------|------|-------------|
| success | Boolean | Operation success status |
| message | String | Response message |
| course | Course | Course data (if applicable) |

### CourseListResponse

Response wrapper for multiple courses operations.

| Field | Type | Description |
|-------|------|-------------|
| success | Boolean | Operation success status |
| message | String | Response message |
| courses | List<Course> | List of courses |
| totalCount | Integer | Total number of courses |

## Operations

### 1. createCourse

Creates a new course in the system.

**Input Parameters:**
- `course` (Course): The course to create

**Returns:** CourseResponse

**Validation:**
- Course code is required and must be unique
- Course name is required
- Credits must be greater than 0

**Example Request:**
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

---

### 2. getCourseById

Retrieves a course by its unique identifier.

**Input Parameters:**
- `courseId` (Long): The course ID

**Returns:** CourseResponse

**Example Request:**
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

---

### 3. getCourseByCode

Retrieves a course by its course code.

**Input Parameters:**
- `courseCode` (String): The course code

**Returns:** CourseResponse

**Example Request:**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://service.course.soa.university.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:getCourseByCode>
         <courseCode>CS101</courseCode>
      </ser:getCourseByCode>
   </soapenv:Body>
</soapenv:Envelope>
```

---

### 4. getAllCourses

Retrieves all courses in the system.

**Input Parameters:** None

**Returns:** CourseListResponse

**Example Request:**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://service.course.soa.university.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:getAllCourses/>
   </soapenv:Body>
</soapenv:Envelope>
```

---

### 5. getCoursesBySemester

Retrieves all courses for a specific semester.

**Input Parameters:**
- `semester` (String): The semester (e.g., "Fall 2024")

**Returns:** CourseListResponse

**Example Request:**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://service.course.soa.university.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:getCoursesBySemester>
         <semester>Fall 2024</semester>
      </ser:getCoursesBySemester>
   </soapenv:Body>
</soapenv:Envelope>
```

---

### 6. getCoursesByProfessor

Retrieves all courses taught by a specific professor.

**Input Parameters:**
- `professorId` (Long): The professor's ID

**Returns:** CourseListResponse

**Example Request:**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://service.course.soa.university.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:getCoursesByProfessor>
         <professorId>1</professorId>
      </ser:getCoursesByProfessor>
   </soapenv:Body>
</soapenv:Envelope>
```

---

### 7. updateCourse

Updates an existing course.

**Input Parameters:**
- `course` (Course): The course with updated information (must include ID)

**Returns:** CourseResponse

**Example Request:**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://service.course.soa.university.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:updateCourse>
         <course>
            <id>1</id>
            <code>CS101</code>
            <name>Introduction to Computer Science - Updated</name>
            <description>Updated description</description>
            <credits>3</credits>
            <semester>Fall 2024</semester>
            <capacity>35</capacity>
         </course>
      </ser:updateCourse>
   </soapenv:Body>
</soapenv:Envelope>
```

---

### 8. deleteCourse

Deletes a course from the system.

**Input Parameters:**
- `courseId` (Long): The course ID to delete

**Returns:** CourseResponse

**Note:** This will also delete all associated schedules.

**Example Request:**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://service.course.soa.university.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:deleteCourse>
         <courseId>1</courseId>
      </ser:deleteCourse>
   </soapenv:Body>
</soapenv:Envelope>
```

---

### 9. addSchedule

Adds a schedule to a course.

**Input Parameters:**
- `schedule` (Schedule): The schedule to add

**Returns:** CourseResponse (with updated course including the new schedule)

**Example Request:**
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

---

### 10. updateSchedule

Updates an existing schedule.

**Input Parameters:**
- `schedule` (Schedule): The schedule with updated information (must include ID)

**Returns:** CourseResponse

**Example Request:**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://service.course.soa.university.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:updateSchedule>
         <schedule>
            <id>1</id>
            <courseId>1</courseId>
            <dayOfWeek>TUESDAY</dayOfWeek>
            <startTime>10:00</startTime>
            <endTime>11:30</endTime>
            <room>Room 202</room>
            <building>Building B</building>
         </schedule>
      </ser:updateSchedule>
   </soapenv:Body>
</soapenv:Envelope>
```

---

### 11. deleteSchedule

Deletes a schedule.

**Input Parameters:**
- `scheduleId` (Long): The schedule ID to delete

**Returns:** CourseResponse

**Example Request:**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://service.course.soa.university.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:deleteSchedule>
         <scheduleId>1</scheduleId>
      </ser:deleteSchedule>
   </soapenv:Body>
</soapenv:Envelope>
```

---

### 12. getCourseSchedules

Retrieves all schedules for a specific course.

**Input Parameters:**
- `courseId` (Long): The course ID

**Returns:** CourseResponse (with course including all schedules)

**Example Request:**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://service.course.soa.university.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:getCourseSchedules>
         <courseId>1</courseId>
      </ser:getCourseSchedules>
   </soapenv:Body>
</soapenv:Envelope>
```

---

### 13. searchCourses

Searches for courses by name, code, or description.

**Input Parameters:**
- `searchTerm` (String): The search term

**Returns:** CourseListResponse

**Example Request:**
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

---

### 14. getAvailableCourses

Retrieves all courses that have available capacity (enrolled < capacity).

**Input Parameters:** None

**Returns:** CourseListResponse

**Example Request:**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://service.course.soa.university.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:getAvailableCourses/>
   </soapenv:Body>
</soapenv:Envelope>
```

## Error Handling

All operations return structured responses with success/failure indicators.

### Success Response Example
```xml
<courseResponse>
   <success>true</success>
   <message>Operation successful</message>
   <course>
      <!-- Course data -->
   </course>
</courseResponse>
```

### Error Response Example
```xml
<courseResponse>
   <success>false</success>
   <message>Course not found with ID: 999</message>
   <course/>
</courseResponse>
```

### Common Error Messages

| Error | Description |
|-------|-------------|
| "Course ID is required" | Missing required course ID parameter |
| "Course not found with ID: X" | Course with specified ID doesn't exist |
| "Course code already exists: X" | Duplicate course code |
| "Course code is required" | Missing required course code |
| "Valid credits value is required" | Invalid or missing credits |
| "Schedule ID is required" | Missing required schedule ID |
| "Day of week is required" | Missing required day of week |

## Examples

### Complete Workflow Example

#### 1. Create a Course
```xml
<ser:createCourse>
   <course>
      <code>CS401</code>
      <name>Advanced Algorithms</name>
      <credits>4</credits>
      <semester>Spring 2025</semester>
      <capacity>25</capacity>
   </course>
</ser:createCourse>
```

#### 2. Add Schedules
```xml
<ser:addSchedule>
   <schedule>
      <courseId>4</courseId>
      <dayOfWeek>MONDAY</dayOfWeek>
      <startTime>14:00</startTime>
      <endTime>16:00</endTime>
      <room>Lab 301</room>
   </schedule>
</ser:addSchedule>
```

#### 3. Search for the Course
```xml
<ser:searchCourses>
   <searchTerm>Algorithms</searchTerm>
</ser:searchCourses>
```

#### 4. Update the Course
```xml
<ser:updateCourse>
   <course>
      <id>4</id>
      <code>CS401</code>
      <name>Advanced Algorithms and Data Structures</name>
      <credits>4</credits>
      <capacity>30</capacity>
   </course>
</ser:updateCourse>
```

## Testing Tools

### Using cURL
```bash
curl -X POST http://localhost:8080/course-service/services/course \
  -H "Content-Type: text/xml" \
  -d @request.xml
```

### Using SoapUI
1. Import WSDL
2. Generate sample requests
3. Modify and test

### Using Postman
1. Create POST request
2. Set Content-Type: text/xml
3. Add SOAP envelope in body
4. Send request

## Best Practices

1. **Always validate input** before sending requests
2. **Check response success flag** before processing data
3. **Handle errors gracefully** in client applications
4. **Use appropriate timeouts** for network operations
5. **Cache WSDL** to improve performance
6. **Implement retry logic** for transient failures

## Support

For additional support:
- Review the WSDL documentation
- Check the README.md file
- Consult the sample client code

---

**Version**: 1.0.0  
**Last Updated**: 2024
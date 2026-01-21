package com.university.soa.course.client;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

/**
 * Example SOAP client for testing the Course Service
 * This demonstrates how to consume the SOAP web service
 */
public class CourseServiceClient {

    public static void main(String[] args) {
        try {
            // WSDL URL
            URL wsdlURL = new URL("http://localhost:8080/course-service/services/course?wsdl");
            
            // Service QName
            QName qname = new QName(
                "http://service.course.soa.university.com/",
                "CourseService"
            );
            
            // Create service
            Service service = Service.create(wsdlURL, qname);
            
            // Get port (proxy)
            // Note: You would need to generate client stubs using wsimport
            // wsimport -keep -p com.university.soa.course.client http://localhost:8080/course-service/services/course?wsdl
            
            System.out.println("Course Service Client");
            System.out.println("====================");
            System.out.println("WSDL URL: " + wsdlURL);
            System.out.println("Service QName: " + qname);
            System.out.println("\nTo generate client stubs, run:");
            System.out.println("wsimport -keep -p com.university.soa.course.client http://localhost:8080/course-service/services/course?wsdl");
            
            // Example usage after generating stubs:
            /*
            CourseService port = service.getPort(CourseService.class);
            
            // Get all courses
            CourseListResponse response = port.getAllCourses();
            System.out.println("Total courses: " + response.getTotalCount());
            
            for (Course course : response.getCourses()) {
                System.out.println("Course: " + course.getName() + " (" + course.getCode() + ")");
            }
            
            // Get course by ID
            CourseResponse courseResponse = port.getCourseById(1L);
            if (courseResponse.isSuccess()) {
                Course course = courseResponse.getCourse();
                System.out.println("Found course: " + course.getName());
            }
            
            // Create new course
            Course newCourse = new Course();
            newCourse.setCode("CS401");
            newCourse.setName("Advanced Algorithms");
            newCourse.setCredits(4);
            newCourse.setSemester("Spring 2025");
            newCourse.setCapacity(25);
            
            CourseResponse createResponse = port.createCourse(newCourse);
            if (createResponse.isSuccess()) {
                System.out.println("Course created: " + createResponse.getMessage());
            }
            */
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
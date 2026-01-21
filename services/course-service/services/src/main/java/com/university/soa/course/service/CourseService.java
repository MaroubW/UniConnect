package com.university.soa.course.service;

import com.university.soa.course.model.Course;
import com.university.soa.course.model.CourseListResponse;
import com.university.soa.course.model.CourseResponse;
import com.university.soa.course.model.Schedule;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import java.util.List;

/**
 * SOAP Web Service Interface for Course Management
 * This service provides operations for managing courses and schedules
 */
@WebService(name = "CourseService", targetNamespace = "http://service.course.soa.university.com/")
@SOAPBinding(style = Style.DOCUMENT)
public interface CourseService {

    /**
     * Create a new course
     * @param course The course to create
     * @return CourseResponse with the created course
     */
    @WebMethod(operationName = "createCourse")
    CourseResponse createCourse(
        @WebParam(name = "course") Course course
    );

    /**
     * Get a course by ID
     * @param courseId The course ID
     * @return CourseResponse with the course details
     */
    @WebMethod(operationName = "getCourseById")
    CourseResponse getCourseById(
        @WebParam(name = "courseId") Long courseId
    );

    /**
     * Get a course by code
     * @param courseCode The course code
     * @return CourseResponse with the course details
     */
    @WebMethod(operationName = "getCourseByCode")
    CourseResponse getCourseByCode(
        @WebParam(name = "courseCode") String courseCode
    );

    /**
     * Get all courses
     * @return CourseListResponse with all courses
     */
    @WebMethod(operationName = "getAllCourses")
    CourseListResponse getAllCourses();

    /**
     * Get courses by semester
     * @param semester The semester (e.g., "Fall 2024")
     * @return CourseListResponse with courses for the semester
     */
    @WebMethod(operationName = "getCoursesBySemester")
    CourseListResponse getCoursesBySemester(
        @WebParam(name = "semester") String semester
    );

    /**
     * Get courses by professor
     * @param professorId The professor ID
     * @return CourseListResponse with courses taught by the professor
     */
    @WebMethod(operationName = "getCoursesByProfessor")
    CourseListResponse getCoursesByProfessor(
        @WebParam(name = "professorId") Long professorId
    );

    /**
     * Update an existing course
     * @param course The course with updated information
     * @return CourseResponse with the updated course
     */
    @WebMethod(operationName = "updateCourse")
    CourseResponse updateCourse(
        @WebParam(name = "course") Course course
    );

    /**
     * Delete a course
     * @param courseId The course ID to delete
     * @return CourseResponse indicating success or failure
     */
    @WebMethod(operationName = "deleteCourse")
    CourseResponse deleteCourse(
        @WebParam(name = "courseId") Long courseId
    );

    /**
     * Add a schedule to a course
     * @param schedule The schedule to add
     * @return CourseResponse with the updated course
     */
    @WebMethod(operationName = "addSchedule")
    CourseResponse addSchedule(
        @WebParam(name = "schedule") Schedule schedule
    );

    /**
     * Update a schedule
     * @param schedule The schedule with updated information
     * @return CourseResponse indicating success or failure
     */
    @WebMethod(operationName = "updateSchedule")
    CourseResponse updateSchedule(
        @WebParam(name = "schedule") Schedule schedule
    );

    /**
     * Delete a schedule
     * @param scheduleId The schedule ID to delete
     * @return CourseResponse indicating success or failure
     */
    @WebMethod(operationName = "deleteSchedule")
    CourseResponse deleteSchedule(
        @WebParam(name = "scheduleId") Long scheduleId
    );

    /**
     * Get all schedules for a course
     * @param courseId The course ID
     * @return CourseResponse with the course and its schedules
     */
    @WebMethod(operationName = "getCourseSchedules")
    CourseResponse getCourseSchedules(
        @WebParam(name = "courseId") Long courseId
    );

    /**
     * Search courses by name
     * @param searchTerm The search term
     * @return CourseListResponse with matching courses
     */
    @WebMethod(operationName = "searchCourses")
    CourseListResponse searchCourses(
        @WebParam(name = "searchTerm") String searchTerm
    );

    /**
     * Get available courses (courses with available capacity)
     * @return CourseListResponse with available courses
     */
    @WebMethod(operationName = "getAvailableCourses")
    CourseListResponse getAvailableCourses();
}
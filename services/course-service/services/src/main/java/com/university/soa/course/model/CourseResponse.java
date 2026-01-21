package com.university.soa.course.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Response wrapper for single course operations
 */
@XmlRootElement(name = "courseResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"success", "message", "course"})
public class CourseResponse implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @XmlElement(required = true)
    private boolean success;

    @XmlElement
    private String message;

    @XmlElement
    private Course course;

    public CourseResponse() {
    }

    public CourseResponse(boolean success, String message, Course course) {
        this.success = success;
        this.message = message;
        this.course = course;
    }

    public static CourseResponse success(Course course) {
        return new CourseResponse(true, "Operation successful", course);
    }

    public static CourseResponse success(String message, Course course) {
        return new CourseResponse(true, message, course);
    }

    public static CourseResponse error(String message) {
        return new CourseResponse(false, message, null);
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
package com.university.soa.course.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Response wrapper for multiple courses operations
 */
@XmlRootElement(name = "courseListResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"success", "message", "courses", "totalCount"})
public class CourseListResponse implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @XmlElement(required = true)
    private boolean success;

    @XmlElement
    private String message;

    @XmlElement
    private List<Course> courses;

    @XmlElement
    private Integer totalCount;

    public CourseListResponse() {
        this.courses = new ArrayList<>();
    }

    public CourseListResponse(boolean success, String message, List<Course> courses) {
        this.success = success;
        this.message = message;
        this.courses = courses != null ? courses : new ArrayList<>();
        this.totalCount = this.courses.size();
    }

    public static CourseListResponse success(List<Course> courses) {
        return new CourseListResponse(true, "Operation successful", courses);
    }

    public static CourseListResponse success(String message, List<Course> courses) {
        return new CourseListResponse(true, message, courses);
    }

    public static CourseListResponse error(String message) {
        return new CourseListResponse(false, message, new ArrayList<>());
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

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
        this.totalCount = courses != null ? courses.size() : 0;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
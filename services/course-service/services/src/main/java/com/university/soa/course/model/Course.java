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
 * Course entity representing a university course
 */
@XmlRootElement(name = "course")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id", "code", "name", "description", "credits", "semester", 
                      "professorId", "professorName", "capacity", "enrolledStudents", "schedules"})
public class Course implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @XmlElement(required = true)
    private Long id;

    @XmlElement(required = true)
    private String code;

    @XmlElement(required = true)
    private String name;

    @XmlElement
    private String description;

    @XmlElement(required = true)
    private Integer credits;

    @XmlElement(required = true)
    private String semester;

    @XmlElement
    private Long professorId;

    @XmlElement
    private String professorName;

    @XmlElement
    private Integer capacity;

    @XmlElement
    private Integer enrolledStudents;

    @XmlElement
    private List<Schedule> schedules;

    public Course() {
        this.schedules = new ArrayList<>();
        this.enrolledStudents = 0;
    }

    public Course(Long id, String code, String name, String description, Integer credits, 
                  String semester, Long professorId, String professorName, Integer capacity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.credits = credits;
        this.semester = semester;
        this.professorId = professorId;
        this.professorName = professorName;
        this.capacity = capacity;
        this.enrolledStudents = 0;
        this.schedules = new ArrayList<>();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(Integer enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public void addSchedule(Schedule schedule) {
        if (this.schedules == null) {
            this.schedules = new ArrayList<>();
        }
        this.schedules.add(schedule);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", credits=" + credits +
                ", semester='" + semester + '\'' +
                ", professorName='" + professorName + '\'' +
                ", capacity=" + capacity +
                ", enrolledStudents=" + enrolledStudents +
                '}';
    }
}
package com.university.soa.course.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Schedule entity representing a course schedule/timetable
 */
@XmlRootElement(name = "schedule")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id", "courseId", "dayOfWeek", "startTime", "endTime", "room", "building"})
public class Schedule implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @XmlElement(required = true)
    private Long id;

    @XmlElement(required = true)
    private Long courseId;

    @XmlElement(required = true)
    private String dayOfWeek; // MONDAY, TUESDAY, etc.

    @XmlElement(required = true)
    private String startTime; // Format: HH:mm

    @XmlElement(required = true)
    private String endTime; // Format: HH:mm

    @XmlElement(required = true)
    private String room;

    @XmlElement
    private String building;

    public Schedule() {
    }

    public Schedule(Long id, Long courseId, String dayOfWeek, String startTime, 
                    String endTime, String room, String building) {
        this.id = id;
        this.courseId = courseId;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.building = building;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", room='" + room + '\'' +
                ", building='" + building + '\'' +
                '}';
    }
}
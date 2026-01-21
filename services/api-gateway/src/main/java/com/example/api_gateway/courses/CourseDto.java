package com.example.api_gateway.courses;

import java.util.List;

public class CourseDto {
    public Long id;
    public String code;
    public String name;
    public String description;
    public Integer credits;
    public String semester;
    public Long professorId;
    public String professorName;
    public Integer capacity;
    public Integer enrolledStudents;
    // TODO: add schedules if you want
    public List<ScheduleDto> schedules;
}

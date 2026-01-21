package com.example.api_gateway.courses;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.api_gateway.soap.coursews.Course;
import com.example.api_gateway.soap.coursews.CourseListResponse;
import com.example.api_gateway.soap.coursews.CourseResponse;
import com.example.api_gateway.soap.coursews.CourseService;
import com.example.api_gateway.soap.coursews.CourseService_Service;
import com.example.api_gateway.soap.coursews.Schedule; // <-- important import

@Component
public class CourseSoapClient {

    @Value("${courses.soap.url}")
    private String soapEndpointUrl;   // e.g. http://localhost:5050/course-service/services/course

    /** JAX-WS port interface generated from the WSDL */
    private CourseService port;

    private void initPort() {
        if (port == null) {
            // Use the default constructor so it loads the WSDL from the generated metadata
            CourseService_Service service = new CourseService_Service();

            // Get the actual port (proxy) – this is what you call methods on
            port = service.getCourseServicePort();

            // Override endpoint URL if needed (so you can change it via properties)
            Map<String, Object> ctx = ((BindingProvider) port).getRequestContext();
            ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, soapEndpointUrl);
        }
    }

    /** Call SOAP getAllCourses() and convert to DTO list */
    public List<CourseDto> getAllCourses() {
        initPort();
        List<CourseDto> result = new ArrayList<>();

        CourseListResponse response = port.getAllCourses();   // SOAP call

        if (response != null && response.getCourses() != null) {
            for (Course c : response.getCourses()) {
                result.add(toDto(c));
            }
        }
        return result;
    }

    /** Call SOAP getCourseById(courseId) and map to DTO */
    public CourseDto getCourseById(Long id) {
        initPort();
        CourseResponse response = port.getCourseById(id);      // SOAP call
        if (response == null || response.getCourse() == null) {
            return null;
        }
        return toDto(response.getCourse());
    }

    /** Create *or* update a course, depending on whether id is null */
    public CourseDto saveCourse(CourseDto dto) {
        initPort();

        Course course = new Course();

        // Only set id when updating; for creation it should stay null
        if (dto.id != null) {
            course.setId(dto.id);
        }

        course.setCode(dto.code);
        course.setName(dto.name);
        course.setDescription(dto.description);
        course.setCredits(dto.credits);
        course.setSemester(dto.semester);
        course.setProfessorId(dto.professorId);
        course.setProfessorName(dto.professorName);
        course.setCapacity(dto.capacity);
        course.setEnrolledStudents(dto.enrolledStudents);

        // ====== NEW: map schedules from DTO -> SOAP ======
        if (dto.schedules != null) {
            List<Schedule> soapSchedules = new ArrayList<>();

            for (ScheduleDto sd : dto.schedules) {
                Schedule s = new Schedule();

                if (sd.id != null) {
                    s.setId(sd.id);
                }

                // Prefer course id from DTO if present, fallback to sd.courseId
                if (dto.id != null) {
                    s.setCourseId(dto.id);
                } else if (sd.courseId != null) {
                    s.setCourseId(sd.courseId);
                }

                s.setDayOfWeek(sd.dayOfWeek);
                s.setStartTime(sd.startTime);
                s.setEndTime(sd.endTime);
                s.setRoom(sd.room);
                s.setBuilding(sd.building);

                soapSchedules.add(s);
            }

            // JAX-WS generated Course usually has getSchedules() that returns a live List
            course.getSchedules().clear();
            course.getSchedules().addAll(soapSchedules);
        }
        // ================================================

        CourseResponse response;

        // If id is null => createCourse, else updateCourse
        if (dto.id == null) {
            response = port.createCourse(course);
        } else {
            response = port.updateCourse(course);
        }

        if (response == null || response.getCourse() == null) {
            return null;
        }
        return toDto(response.getCourse());
    }

    /** Delete a course by id, return true if SOAP says success */
    public boolean deleteCourse(Long id) {
        initPort();
        CourseResponse response = port.deleteCourse(id);
        // JAXB boolean getter is usually "isSuccess()"
        return response != null && response.isSuccess();
    }

    /** Helper: map SOAP Course to our DTO (including schedules) */
    private CourseDto toDto(Course c) {
        CourseDto dto = new CourseDto();
        dto.id = c.getId();
        dto.code = c.getCode();
        dto.name = c.getName();
        dto.description = c.getDescription();
        dto.credits = c.getCredits();
        dto.semester = c.getSemester();
        dto.professorId = c.getProfessorId();
        dto.professorName = c.getProfessorName();
        dto.capacity = c.getCapacity();
        dto.enrolledStudents = c.getEnrolledStudents();

        // ====== NEW: SOAP schedules -> DTO schedules ======
        if (c.getSchedules() != null) {
            dto.schedules = new ArrayList<>();

            for (Schedule s : c.getSchedules()) {
                ScheduleDto sd = new ScheduleDto();
                sd.id        = s.getId();
                sd.courseId  = s.getCourseId();
                sd.dayOfWeek = s.getDayOfWeek();
                sd.startTime = s.getStartTime();
                sd.endTime   = s.getEndTime();
                sd.room      = s.getRoom();
                sd.building  = s.getBuilding();
                dto.schedules.add(sd);
            }
        }
        // ======================================

        return dto;
    }
}

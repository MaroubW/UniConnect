package com.university.soa.course.service;

import com.university.soa.course.model.Course;
import com.university.soa.course.model.CourseListResponse;
import com.university.soa.course.model.CourseResponse;
import com.university.soa.course.model.Schedule;
import com.university.soa.course.repository.CourseRepository;

import javax.jws.WebService;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the Course SOAP Web Service
 */
@WebService(
    endpointInterface = "com.university.soa.course.service.CourseService",
    serviceName = "CourseService",
    portName = "CourseServicePort",
    targetNamespace = "http://service.course.soa.university.com/"
)
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;

    public CourseServiceImpl() {
        this.repository = CourseRepository.getInstance();
    }

    @Override
    public CourseResponse createCourse(Course course) {
        try {
            // Validate course data
            if (course == null) {
                return CourseResponse.error("Course data is required");
            }
            
            if (course.getCode() == null || course.getCode().trim().isEmpty()) {
                return CourseResponse.error("Course code is required");
            }
            
            if (course.getName() == null || course.getName().trim().isEmpty()) {
                return CourseResponse.error("Course name is required");
            }
            
            if (course.getCredits() == null || course.getCredits() <= 0) {
                return CourseResponse.error("Valid credits value is required");
            }
            
            // Check if course code already exists
            if (repository.courseCodeExists(course.getCode())) {
                return CourseResponse.error("Course code already exists: " + course.getCode());
            }
            
            // Create the course
            Course createdCourse = repository.createCourse(course);
            return CourseResponse.success("Course created successfully", createdCourse);
            
        } catch (Exception e) {
            return CourseResponse.error("Error creating course: " + e.getMessage());
        }
    }

    @Override
    public CourseResponse getCourseById(Long courseId) {
        try {
            if (courseId == null) {
                return CourseResponse.error("Course ID is required");
            }
            
            Course course = repository.getCourseById(courseId);
            if (course == null) {
                return CourseResponse.error("Course not found with ID: " + courseId);
            }
            
            return CourseResponse.success(course);
            
        } catch (Exception e) {
            return CourseResponse.error("Error retrieving course: " + e.getMessage());
        }
    }

    @Override
    public CourseResponse getCourseByCode(String courseCode) {
        try {
            if (courseCode == null || courseCode.trim().isEmpty()) {
                return CourseResponse.error("Course code is required");
            }
            
            Course course = repository.getCourseByCode(courseCode);
            if (course == null) {
                return CourseResponse.error("Course not found with code: " + courseCode);
            }
            
            return CourseResponse.success(course);
            
        } catch (Exception e) {
            return CourseResponse.error("Error retrieving course: " + e.getMessage());
        }
    }

    @Override
    public CourseListResponse getAllCourses() {
        try {
            List<Course> courses = repository.getAllCourses();
            return CourseListResponse.success(courses);
            
        } catch (Exception e) {
            return CourseListResponse.error("Error retrieving courses: " + e.getMessage());
        }
    }

    @Override
    public CourseListResponse getCoursesBySemester(String semester) {
        try {
            if (semester == null || semester.trim().isEmpty()) {
                return CourseListResponse.error("Semester is required");
            }
            
            List<Course> courses = repository.getCoursesBySemester(semester);
            return CourseListResponse.success(courses);
            
        } catch (Exception e) {
            return CourseListResponse.error("Error retrieving courses: " + e.getMessage());
        }
    }

    @Override
    public CourseListResponse getCoursesByProfessor(Long professorId) {
        try {
            if (professorId == null) {
                return CourseListResponse.error("Professor ID is required");
            }
            
            List<Course> courses = repository.getCoursesByProfessor(professorId);
            return CourseListResponse.success(courses);
            
        } catch (Exception e) {
            return CourseListResponse.error("Error retrieving courses: " + e.getMessage());
        }
    }

    @Override
    public CourseResponse updateCourse(Course course) {
        try {
            if (course == null || course.getId() == null) {
                return CourseResponse.error("Course ID is required for update");
            }

            if (!repository.courseExists(course.getId())) {
                return CourseResponse.error("Course not found with ID: " + course.getId());
            }

            // Validate course data
            if (course.getCode() == null || course.getCode().trim().isEmpty()) {
                return CourseResponse.error("Course code is required");
            }

            if (course.getName() == null || course.getName().trim().isEmpty()) {
                return CourseResponse.error("Course name is required");
            }

            if (course.getCredits() == null || course.getCredits() <= 0) {
                return CourseResponse.error("Valid credits value is required");
            }

            // Check if course code already exists (excluding current course)
            Course existingCourse = repository.getCourseById(course.getId());
            if (!course.getCode().equals(existingCourse.getCode()) && repository.courseCodeExists(course.getCode())) {
                return CourseResponse.error("Course code already exists: " + course.getCode());
            }

            Course updatedCourse = repository.updateCourse(course);
            return CourseResponse.success("Course updated successfully", updatedCourse);

        } catch (Exception e) {
            return CourseResponse.error("Error updating course: " + e.getMessage());
        }
    }

    @Override
    public CourseResponse deleteCourse(Long courseId) {
        try {
            if (courseId == null) {
                return CourseResponse.error("Course ID is required");
            }
            
            if (!repository.courseExists(courseId)) {
                return CourseResponse.error("Course not found with ID: " + courseId);
            }
            
            boolean deleted = repository.deleteCourse(courseId);
            if (deleted) {
                return CourseResponse.success("Course deleted successfully", null);
            } else {
                return CourseResponse.error("Failed to delete course");
            }
            
        } catch (Exception e) {
            return CourseResponse.error("Error deleting course: " + e.getMessage());
        }
    }

    @Override
    public CourseResponse addSchedule(Schedule schedule) {
        try {
            if (schedule == null) {
                return CourseResponse.error("Schedule data is required");
            }
            
            if (schedule.getCourseId() == null) {
                return CourseResponse.error("Course ID is required");
            }
            
            if (!repository.courseExists(schedule.getCourseId())) {
                return CourseResponse.error("Course not found with ID: " + schedule.getCourseId());
            }
            
            // Validate schedule data
            if (schedule.getDayOfWeek() == null || schedule.getDayOfWeek().trim().isEmpty()) {
                return CourseResponse.error("Day of week is required");
            }
            
            if (schedule.getStartTime() == null || schedule.getStartTime().trim().isEmpty()) {
                return CourseResponse.error("Start time is required");
            }
            
            if (schedule.getEndTime() == null || schedule.getEndTime().trim().isEmpty()) {
                return CourseResponse.error("End time is required");
            }
            
            if (schedule.getRoom() == null || schedule.getRoom().trim().isEmpty()) {
                return CourseResponse.error("Room is required");
            }
            
            Schedule createdSchedule = repository.createSchedule(schedule);
            Course course = repository.getCourseById(schedule.getCourseId());
            
            return CourseResponse.success("Schedule added successfully", course);
            
        } catch (Exception e) {
            return CourseResponse.error("Error adding schedule: " + e.getMessage());
        }
    }

    @Override
    public CourseResponse updateSchedule(Schedule schedule) {
        try {
            if (schedule == null || schedule.getId() == null) {
                return CourseResponse.error("Schedule ID is required for update");
            }

            Schedule existingSchedule = repository.getScheduleById(schedule.getId());
            if (existingSchedule == null) {
                return CourseResponse.error("Schedule not found with ID: " + schedule.getId());
            }

            // Validate schedule data
            if (schedule.getDayOfWeek() == null || schedule.getDayOfWeek().trim().isEmpty()) {
                return CourseResponse.error("Day of week is required");
            }

            if (schedule.getStartTime() == null || schedule.getStartTime().trim().isEmpty()) {
                return CourseResponse.error("Start time is required");
            }

            if (schedule.getEndTime() == null || schedule.getEndTime().trim().isEmpty()) {
                return CourseResponse.error("End time is required");
            }

            if (schedule.getRoom() == null || schedule.getRoom().trim().isEmpty()) {
                return CourseResponse.error("Room is required");
            }

            Schedule updatedSchedule = repository.updateSchedule(schedule);
            return CourseResponse.success("Schedule updated successfully", null);

        } catch (Exception e) {
            return CourseResponse.error("Error updating schedule: " + e.getMessage());
        }
    }

    @Override
    public CourseResponse deleteSchedule(Long scheduleId) {
        try {
            if (scheduleId == null) {
                return CourseResponse.error("Schedule ID is required");
            }
            
            Schedule schedule = repository.getScheduleById(scheduleId);
            if (schedule == null) {
                return CourseResponse.error("Schedule not found with ID: " + scheduleId);
            }
            
            boolean deleted = repository.deleteSchedule(scheduleId);
            if (deleted) {
                return CourseResponse.success("Schedule deleted successfully", null);
            } else {
                return CourseResponse.error("Failed to delete schedule");
            }
            
        } catch (Exception e) {
            return CourseResponse.error("Error deleting schedule: " + e.getMessage());
        }
    }

    @Override
    public CourseResponse getCourseSchedules(Long courseId) {
        try {
            if (courseId == null) {
                return CourseResponse.error("Course ID is required");
            }
            
            Course course = repository.getCourseById(courseId);
            if (course == null) {
                return CourseResponse.error("Course not found with ID: " + courseId);
            }
            
            return CourseResponse.success(course);
            
        } catch (Exception e) {
            return CourseResponse.error("Error retrieving course schedules: " + e.getMessage());
        }
    }

    @Override
    public CourseListResponse searchCourses(String searchTerm) {
        try {
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                return CourseListResponse.error("Search term is required");
            }
            
            List<Course> allCourses = repository.getAllCourses();
            String lowerSearchTerm = searchTerm.toLowerCase();
            
            List<Course> matchingCourses = allCourses.stream()
                .filter(course -> 
                    course.getName().toLowerCase().contains(lowerSearchTerm) ||
                    course.getCode().toLowerCase().contains(lowerSearchTerm) ||
                    (course.getDescription() != null && 
                     course.getDescription().toLowerCase().contains(lowerSearchTerm))
                )
                .collect(Collectors.toList());
            
            return CourseListResponse.success(matchingCourses);
            
        } catch (Exception e) {
            return CourseListResponse.error("Error searching courses: " + e.getMessage());
        }
    }

    @Override
    public CourseListResponse getAvailableCourses() {
        try {
            List<Course> allCourses = repository.getAllCourses();
            
            List<Course> availableCourses = allCourses.stream()
                .filter(course -> 
                    course.getCapacity() != null && 
                    course.getEnrolledStudents() != null &&
                    course.getEnrolledStudents() < course.getCapacity()
                )
                .collect(Collectors.toList());
            
            return CourseListResponse.success(availableCourses);
            
        } catch (Exception e) {
            return CourseListResponse.error("Error retrieving available courses: " + e.getMessage());
        }
    }
}
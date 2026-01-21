package com.example.api_gateway.courses;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*") // for frontend
public class CourseController {

    private final CourseSoapClient soapClient;

    public CourseController(CourseSoapClient soapClient) {
        this.soapClient = soapClient;
    }

    // GET /api/courses
    @GetMapping
    public List<CourseDto> getAllCourses() {
        return soapClient.getAllCourses();
    }
    // GET /api/courses/list
    @GetMapping("/list")
    public List<CourseDto> listCourses() {
        return soapClient.getAllCourses();
    }

    // GET /api/courses/dummy  (test endpoint)
    @GetMapping("/dummy")
    public List<CourseDto> dummy() {
        CourseDto dto = new CourseDto();
        dto.id = 1L;
        dto.code = "SOA101";
        dto.name = "Service Oriented Architecture";
        dto.credits = 4;
        List<CourseDto> list = new ArrayList<>();
        list.add(dto);
        return list;
    }

    // GET /api/courses/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourse(@PathVariable Long id) {
        CourseDto dto = soapClient.getCourseById(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    // POST /api/courses  -> CREATE
    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto dto) {
        // make sure this is treated as "create"
        dto.id = null;

        CourseDto saved = soapClient.saveCourse(dto);

        if (saved == null) {
            // something went wrong in the SOAP call
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // 201 + JSON body
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // PUT /api/courses/{id}  -> UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable Long id,
                                                  @RequestBody CourseDto dto) {
        dto.id = id;
        CourseDto saved = soapClient.saveCourse(dto);

        if (saved == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(saved);
    }

    // DELETE /api/courses/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        boolean ok = soapClient.deleteCourse(id);
        if (ok) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

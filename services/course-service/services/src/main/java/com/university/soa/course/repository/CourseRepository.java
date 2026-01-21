package com.university.soa.course.repository;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.university.soa.course.model.Course;
import com.university.soa.course.model.Schedule;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class CourseRepository {

    private static final CourseRepository INSTANCE = new CourseRepository();

    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final MongoCollection<Document> coursesCollection;
    private final MongoCollection<Document> schedulesCollection;

    private CourseRepository() {
        // Use environment variable for MongoDB URI, default to localhost
        String mongoUri = System.getenv("MONGODB_URI") != null ? System.getenv("MONGODB_URI") : "mongodb://host.docker.internal:27017";
        mongoClient = MongoClients.create(mongoUri);
        database = mongoClient.getDatabase("soa_project");
        coursesCollection = database.getCollection("courses");
        schedulesCollection = database.getCollection("schedules");
        
    }

    public static CourseRepository getInstance() {
        return INSTANCE;
    }

    /* ------------------ Course helpers ------------------ */

    private long nextCourseId() {
        Document last = coursesCollection
                .find()
                .sort(Sorts.descending("id"))
                .first();

        if (last == null) return 1L;
        Number id = last.get("id", Number.class);
        return (id == null ? 1L : id.longValue() + 1L);
    }

    private Document courseToDoc(Course c) {
        Document doc = new Document();
        if (c.getId() != null) {
            doc.append("id", c.getId());
        }
        doc.append("code", c.getCode());
        doc.append("name", c.getName());
        doc.append("description", c.getDescription());
        doc.append("credits", c.getCredits());
        doc.append("semester", c.getSemester());
        doc.append("professorId", c.getProfessorId());
        doc.append("professorName", c.getProfessorName());
        doc.append("capacity", c.getCapacity());
        doc.append("enrolledStudents", c.getEnrolledStudents());
        return doc;
    }

    private Course docToCourse(Document doc) {
        if (doc == null) return null;
        Course c = new Course();
        c.setId(doc.get("id", Long.class));
        c.setCode(doc.getString("code"));
        c.setName(doc.getString("name"));
        c.setDescription(doc.getString("description"));
        c.setCredits((Integer) doc.get("credits"));          // adjust type if needed
        c.setSemester(doc.getString("semester"));
        c.setProfessorId(doc.get("professorId", Long.class));
        c.setProfessorName(doc.getString("professorName"));
        c.setCapacity((Integer) doc.get("capacity"));
        c.setEnrolledStudents((Integer) doc.get("enrolledStudents"));
        return c;
    }

    /* ------------------ Schedule helpers ------------------ */

    private long nextScheduleId() {
        Document last = schedulesCollection
                .find()
                .sort(Sorts.descending("id"))
                .first();

        if (last == null) return 1L;
        Number id = last.get("id", Number.class);
        return (id == null ? 1L : id.longValue() + 1L);
    }

    private Document scheduleToDoc(Schedule s) {
        Document doc = new Document();
        if (s.getId() != null) {
            doc.append("id", s.getId());
        }
        doc.append("courseId", s.getCourseId());
        doc.append("dayOfWeek", s.getDayOfWeek());
        doc.append("startTime", s.getStartTime());
        doc.append("endTime", s.getEndTime());
        doc.append("room", s.getRoom());
        doc.append("building", s.getBuilding());
        return doc;
    }

    private Schedule docToSchedule(Document doc) {
        if (doc == null) return null;
        Schedule s = new Schedule();
        s.setId(doc.get("id", Long.class));
        s.setCourseId(doc.get("courseId", Long.class));
        s.setDayOfWeek(doc.getString("dayOfWeek"));
        s.setStartTime(doc.getString("startTime"));
        s.setEndTime(doc.getString("endTime"));
        s.setRoom(doc.getString("room"));
        s.setBuilding(doc.getString("building"));
        return s;
    }

    /* ------------------ Courses API used by CourseServiceImpl ------------------ */

    public boolean courseCodeExists(String code) {
        long count = coursesCollection.countDocuments(Filters.eq("code", code));
        return count > 0;
    }

    public Course createCourse(Course course) {
        if (course.getId() == null) {
            course.setId(nextCourseId());
        }
        Document doc = courseToDoc(course);
        coursesCollection.insertOne(doc);
        return course;
    }

    public Course getCourseById(Long id) {
        Document doc = coursesCollection.find(Filters.eq("id", id)).first();
        if (doc == null) return null;

        // also load schedules for this course
        List<Schedule> schedules = getSchedulesForCourse(id);
        Course c = docToCourse(doc);
        c.setSchedules(schedules); // if your Course has this list
        return c;
    }

    public Course getCourseByCode(String code) {
        Document doc = coursesCollection.find(Filters.eq("code", code)).first();
        if (doc == null) return null;
        Course c = docToCourse(doc);
        // optionally load schedules as well
        return c;
    }

    public List<Course> getAllCourses() {
        List<Course> result = new ArrayList<>();
        for (Document doc : coursesCollection.find()) {
            Course c = docToCourse(doc);
            result.add(c);
        }
        return result;
    }

    public List<Course> getCoursesBySemester(String semester) {
        List<Course> result = new ArrayList<>();
        for (Document doc : coursesCollection.find(Filters.eq("semester", semester))) {
            result.add(docToCourse(doc));
        }
        return result;
    }

    public List<Course> getCoursesByProfessor(Long professorId) {
        List<Course> result = new ArrayList<>();
        for (Document doc : coursesCollection.find(Filters.eq("professorId", professorId))) {
            result.add(docToCourse(doc));
        }
        return result;
    }

    public boolean courseExists(Long id) {
        return coursesCollection.countDocuments(Filters.eq("id", id)) > 0;
    }

    public Course updateCourse(Course course) {
        if (course.getId() == null) {
            throw new IllegalArgumentException("Course ID required for update");
        }

        Document doc = courseToDoc(course);
        coursesCollection.replaceOne(Filters.eq("id", course.getId()), doc);
        return course;
    }

    public boolean deleteCourse(Long id) {
        long deleted = coursesCollection.deleteOne(Filters.eq("id", id)).getDeletedCount();
        // delete associated schedules as well
        schedulesCollection.deleteMany(Filters.eq("courseId", id));
        return deleted > 0;
    }

    /* ------------------ Schedules API used by CourseServiceImpl ------------------ */

    public Schedule createSchedule(Schedule schedule) {
        if (schedule.getId() == null) {
            schedule.setId(nextScheduleId());
        }
        Document doc = scheduleToDoc(schedule);
        schedulesCollection.insertOne(doc);
        return schedule;
    }

    public Schedule getScheduleById(Long id) {
        Document doc = schedulesCollection.find(Filters.eq("id", id)).first();
        return docToSchedule(doc);
    }

    public Schedule updateSchedule(Schedule schedule) {
        if (schedule.getId() == null) {
            throw new IllegalArgumentException("Schedule ID required for update");
        }
        Document doc = scheduleToDoc(schedule);
        schedulesCollection.replaceOne(Filters.eq("id", schedule.getId()), doc);
        return schedule;
    }

    public boolean deleteSchedule(Long id) {
        long deleted = schedulesCollection.deleteOne(Filters.eq("id", id)).getDeletedCount();
        return deleted > 0;
    }

    public List<Schedule> getSchedulesForCourse(Long courseId) {
        List<Schedule> result = new ArrayList<>();
        for (Document doc : schedulesCollection.find(Filters.eq("courseId", courseId))) {
            result.add(docToSchedule(doc));
        }
        return result;
    }
}


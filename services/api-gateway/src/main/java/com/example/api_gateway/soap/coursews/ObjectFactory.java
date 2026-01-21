
package com.example.api_gateway.soap.coursews;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.example.api_gateway.soap.coursews package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AddSchedule_QNAME = new QName("http://service.course.soa.university.com/", "addSchedule");
    private final static QName _AddScheduleResponse_QNAME = new QName("http://service.course.soa.university.com/", "addScheduleResponse");
    private final static QName _Course_QNAME = new QName("http://service.course.soa.university.com/", "course");
    private final static QName _CourseListResponse_QNAME = new QName("http://service.course.soa.university.com/", "courseListResponse");
    private final static QName _CourseResponse_QNAME = new QName("http://service.course.soa.university.com/", "courseResponse");
    private final static QName _CreateCourse_QNAME = new QName("http://service.course.soa.university.com/", "createCourse");
    private final static QName _CreateCourseResponse_QNAME = new QName("http://service.course.soa.university.com/", "createCourseResponse");
    private final static QName _DeleteCourse_QNAME = new QName("http://service.course.soa.university.com/", "deleteCourse");
    private final static QName _DeleteCourseResponse_QNAME = new QName("http://service.course.soa.university.com/", "deleteCourseResponse");
    private final static QName _DeleteSchedule_QNAME = new QName("http://service.course.soa.university.com/", "deleteSchedule");
    private final static QName _DeleteScheduleResponse_QNAME = new QName("http://service.course.soa.university.com/", "deleteScheduleResponse");
    private final static QName _GetAllCourses_QNAME = new QName("http://service.course.soa.university.com/", "getAllCourses");
    private final static QName _GetAllCoursesResponse_QNAME = new QName("http://service.course.soa.university.com/", "getAllCoursesResponse");
    private final static QName _GetAvailableCourses_QNAME = new QName("http://service.course.soa.university.com/", "getAvailableCourses");
    private final static QName _GetAvailableCoursesResponse_QNAME = new QName("http://service.course.soa.university.com/", "getAvailableCoursesResponse");
    private final static QName _GetCourseByCode_QNAME = new QName("http://service.course.soa.university.com/", "getCourseByCode");
    private final static QName _GetCourseByCodeResponse_QNAME = new QName("http://service.course.soa.university.com/", "getCourseByCodeResponse");
    private final static QName _GetCourseById_QNAME = new QName("http://service.course.soa.university.com/", "getCourseById");
    private final static QName _GetCourseByIdResponse_QNAME = new QName("http://service.course.soa.university.com/", "getCourseByIdResponse");
    private final static QName _GetCourseSchedules_QNAME = new QName("http://service.course.soa.university.com/", "getCourseSchedules");
    private final static QName _GetCourseSchedulesResponse_QNAME = new QName("http://service.course.soa.university.com/", "getCourseSchedulesResponse");
    private final static QName _GetCoursesByProfessor_QNAME = new QName("http://service.course.soa.university.com/", "getCoursesByProfessor");
    private final static QName _GetCoursesByProfessorResponse_QNAME = new QName("http://service.course.soa.university.com/", "getCoursesByProfessorResponse");
    private final static QName _GetCoursesBySemester_QNAME = new QName("http://service.course.soa.university.com/", "getCoursesBySemester");
    private final static QName _GetCoursesBySemesterResponse_QNAME = new QName("http://service.course.soa.university.com/", "getCoursesBySemesterResponse");
    private final static QName _Schedule_QNAME = new QName("http://service.course.soa.university.com/", "schedule");
    private final static QName _SearchCourses_QNAME = new QName("http://service.course.soa.university.com/", "searchCourses");
    private final static QName _SearchCoursesResponse_QNAME = new QName("http://service.course.soa.university.com/", "searchCoursesResponse");
    private final static QName _UpdateCourse_QNAME = new QName("http://service.course.soa.university.com/", "updateCourse");
    private final static QName _UpdateCourseResponse_QNAME = new QName("http://service.course.soa.university.com/", "updateCourseResponse");
    private final static QName _UpdateSchedule_QNAME = new QName("http://service.course.soa.university.com/", "updateSchedule");
    private final static QName _UpdateScheduleResponse_QNAME = new QName("http://service.course.soa.university.com/", "updateScheduleResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.example.api_gateway.soap.coursews
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddSchedule }
     * 
     */
    public AddSchedule createAddSchedule() {
        return new AddSchedule();
    }

    /**
     * Create an instance of {@link AddScheduleResponse }
     * 
     */
    public AddScheduleResponse createAddScheduleResponse() {
        return new AddScheduleResponse();
    }

    /**
     * Create an instance of {@link Course }
     * 
     */
    public Course createCourse() {
        return new Course();
    }

    /**
     * Create an instance of {@link CourseListResponse }
     * 
     */
    public CourseListResponse createCourseListResponse() {
        return new CourseListResponse();
    }

    /**
     * Create an instance of {@link CourseResponse }
     * 
     */
    public CourseResponse createCourseResponse() {
        return new CourseResponse();
    }

    /**
     * Create an instance of {@link CreateCourse }
     * 
     */
    public CreateCourse createCreateCourse() {
        return new CreateCourse();
    }

    /**
     * Create an instance of {@link CreateCourseResponse }
     * 
     */
    public CreateCourseResponse createCreateCourseResponse() {
        return new CreateCourseResponse();
    }

    /**
     * Create an instance of {@link DeleteCourse }
     * 
     */
    public DeleteCourse createDeleteCourse() {
        return new DeleteCourse();
    }

    /**
     * Create an instance of {@link DeleteCourseResponse }
     * 
     */
    public DeleteCourseResponse createDeleteCourseResponse() {
        return new DeleteCourseResponse();
    }

    /**
     * Create an instance of {@link DeleteSchedule }
     * 
     */
    public DeleteSchedule createDeleteSchedule() {
        return new DeleteSchedule();
    }

    /**
     * Create an instance of {@link DeleteScheduleResponse }
     * 
     */
    public DeleteScheduleResponse createDeleteScheduleResponse() {
        return new DeleteScheduleResponse();
    }

    /**
     * Create an instance of {@link GetAllCourses }
     * 
     */
    public GetAllCourses createGetAllCourses() {
        return new GetAllCourses();
    }

    /**
     * Create an instance of {@link GetAllCoursesResponse }
     * 
     */
    public GetAllCoursesResponse createGetAllCoursesResponse() {
        return new GetAllCoursesResponse();
    }

    /**
     * Create an instance of {@link GetAvailableCourses }
     * 
     */
    public GetAvailableCourses createGetAvailableCourses() {
        return new GetAvailableCourses();
    }

    /**
     * Create an instance of {@link GetAvailableCoursesResponse }
     * 
     */
    public GetAvailableCoursesResponse createGetAvailableCoursesResponse() {
        return new GetAvailableCoursesResponse();
    }

    /**
     * Create an instance of {@link GetCourseByCode }
     * 
     */
    public GetCourseByCode createGetCourseByCode() {
        return new GetCourseByCode();
    }

    /**
     * Create an instance of {@link GetCourseByCodeResponse }
     * 
     */
    public GetCourseByCodeResponse createGetCourseByCodeResponse() {
        return new GetCourseByCodeResponse();
    }

    /**
     * Create an instance of {@link GetCourseById }
     * 
     */
    public GetCourseById createGetCourseById() {
        return new GetCourseById();
    }

    /**
     * Create an instance of {@link GetCourseByIdResponse }
     * 
     */
    public GetCourseByIdResponse createGetCourseByIdResponse() {
        return new GetCourseByIdResponse();
    }

    /**
     * Create an instance of {@link GetCourseSchedules }
     * 
     */
    public GetCourseSchedules createGetCourseSchedules() {
        return new GetCourseSchedules();
    }

    /**
     * Create an instance of {@link GetCourseSchedulesResponse }
     * 
     */
    public GetCourseSchedulesResponse createGetCourseSchedulesResponse() {
        return new GetCourseSchedulesResponse();
    }

    /**
     * Create an instance of {@link GetCoursesByProfessor }
     * 
     */
    public GetCoursesByProfessor createGetCoursesByProfessor() {
        return new GetCoursesByProfessor();
    }

    /**
     * Create an instance of {@link GetCoursesByProfessorResponse }
     * 
     */
    public GetCoursesByProfessorResponse createGetCoursesByProfessorResponse() {
        return new GetCoursesByProfessorResponse();
    }

    /**
     * Create an instance of {@link GetCoursesBySemester }
     * 
     */
    public GetCoursesBySemester createGetCoursesBySemester() {
        return new GetCoursesBySemester();
    }

    /**
     * Create an instance of {@link GetCoursesBySemesterResponse }
     * 
     */
    public GetCoursesBySemesterResponse createGetCoursesBySemesterResponse() {
        return new GetCoursesBySemesterResponse();
    }

    /**
     * Create an instance of {@link Schedule }
     * 
     */
    public Schedule createSchedule() {
        return new Schedule();
    }

    /**
     * Create an instance of {@link SearchCourses }
     * 
     */
    public SearchCourses createSearchCourses() {
        return new SearchCourses();
    }

    /**
     * Create an instance of {@link SearchCoursesResponse }
     * 
     */
    public SearchCoursesResponse createSearchCoursesResponse() {
        return new SearchCoursesResponse();
    }

    /**
     * Create an instance of {@link UpdateCourse }
     * 
     */
    public UpdateCourse createUpdateCourse() {
        return new UpdateCourse();
    }

    /**
     * Create an instance of {@link UpdateCourseResponse }
     * 
     */
    public UpdateCourseResponse createUpdateCourseResponse() {
        return new UpdateCourseResponse();
    }

    /**
     * Create an instance of {@link UpdateSchedule }
     * 
     */
    public UpdateSchedule createUpdateSchedule() {
        return new UpdateSchedule();
    }

    /**
     * Create an instance of {@link UpdateScheduleResponse }
     * 
     */
    public UpdateScheduleResponse createUpdateScheduleResponse() {
        return new UpdateScheduleResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddSchedule }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AddSchedule }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "addSchedule")
    public JAXBElement<AddSchedule> createAddSchedule(AddSchedule value) {
        return new JAXBElement<AddSchedule>(_AddSchedule_QNAME, AddSchedule.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddScheduleResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AddScheduleResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "addScheduleResponse")
    public JAXBElement<AddScheduleResponse> createAddScheduleResponse(AddScheduleResponse value) {
        return new JAXBElement<AddScheduleResponse>(_AddScheduleResponse_QNAME, AddScheduleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Course }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Course }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "course")
    public JAXBElement<Course> createCourse(Course value) {
        return new JAXBElement<Course>(_Course_QNAME, Course.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CourseListResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CourseListResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "courseListResponse")
    public JAXBElement<CourseListResponse> createCourseListResponse(CourseListResponse value) {
        return new JAXBElement<CourseListResponse>(_CourseListResponse_QNAME, CourseListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CourseResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CourseResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "courseResponse")
    public JAXBElement<CourseResponse> createCourseResponse(CourseResponse value) {
        return new JAXBElement<CourseResponse>(_CourseResponse_QNAME, CourseResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateCourse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreateCourse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "createCourse")
    public JAXBElement<CreateCourse> createCreateCourse(CreateCourse value) {
        return new JAXBElement<CreateCourse>(_CreateCourse_QNAME, CreateCourse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateCourseResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreateCourseResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "createCourseResponse")
    public JAXBElement<CreateCourseResponse> createCreateCourseResponse(CreateCourseResponse value) {
        return new JAXBElement<CreateCourseResponse>(_CreateCourseResponse_QNAME, CreateCourseResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteCourse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DeleteCourse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "deleteCourse")
    public JAXBElement<DeleteCourse> createDeleteCourse(DeleteCourse value) {
        return new JAXBElement<DeleteCourse>(_DeleteCourse_QNAME, DeleteCourse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteCourseResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DeleteCourseResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "deleteCourseResponse")
    public JAXBElement<DeleteCourseResponse> createDeleteCourseResponse(DeleteCourseResponse value) {
        return new JAXBElement<DeleteCourseResponse>(_DeleteCourseResponse_QNAME, DeleteCourseResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteSchedule }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DeleteSchedule }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "deleteSchedule")
    public JAXBElement<DeleteSchedule> createDeleteSchedule(DeleteSchedule value) {
        return new JAXBElement<DeleteSchedule>(_DeleteSchedule_QNAME, DeleteSchedule.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteScheduleResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DeleteScheduleResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "deleteScheduleResponse")
    public JAXBElement<DeleteScheduleResponse> createDeleteScheduleResponse(DeleteScheduleResponse value) {
        return new JAXBElement<DeleteScheduleResponse>(_DeleteScheduleResponse_QNAME, DeleteScheduleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllCourses }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetAllCourses }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "getAllCourses")
    public JAXBElement<GetAllCourses> createGetAllCourses(GetAllCourses value) {
        return new JAXBElement<GetAllCourses>(_GetAllCourses_QNAME, GetAllCourses.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllCoursesResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetAllCoursesResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "getAllCoursesResponse")
    public JAXBElement<GetAllCoursesResponse> createGetAllCoursesResponse(GetAllCoursesResponse value) {
        return new JAXBElement<GetAllCoursesResponse>(_GetAllCoursesResponse_QNAME, GetAllCoursesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAvailableCourses }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetAvailableCourses }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "getAvailableCourses")
    public JAXBElement<GetAvailableCourses> createGetAvailableCourses(GetAvailableCourses value) {
        return new JAXBElement<GetAvailableCourses>(_GetAvailableCourses_QNAME, GetAvailableCourses.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAvailableCoursesResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetAvailableCoursesResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "getAvailableCoursesResponse")
    public JAXBElement<GetAvailableCoursesResponse> createGetAvailableCoursesResponse(GetAvailableCoursesResponse value) {
        return new JAXBElement<GetAvailableCoursesResponse>(_GetAvailableCoursesResponse_QNAME, GetAvailableCoursesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCourseByCode }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetCourseByCode }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "getCourseByCode")
    public JAXBElement<GetCourseByCode> createGetCourseByCode(GetCourseByCode value) {
        return new JAXBElement<GetCourseByCode>(_GetCourseByCode_QNAME, GetCourseByCode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCourseByCodeResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetCourseByCodeResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "getCourseByCodeResponse")
    public JAXBElement<GetCourseByCodeResponse> createGetCourseByCodeResponse(GetCourseByCodeResponse value) {
        return new JAXBElement<GetCourseByCodeResponse>(_GetCourseByCodeResponse_QNAME, GetCourseByCodeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCourseById }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetCourseById }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "getCourseById")
    public JAXBElement<GetCourseById> createGetCourseById(GetCourseById value) {
        return new JAXBElement<GetCourseById>(_GetCourseById_QNAME, GetCourseById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCourseByIdResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetCourseByIdResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "getCourseByIdResponse")
    public JAXBElement<GetCourseByIdResponse> createGetCourseByIdResponse(GetCourseByIdResponse value) {
        return new JAXBElement<GetCourseByIdResponse>(_GetCourseByIdResponse_QNAME, GetCourseByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCourseSchedules }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetCourseSchedules }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "getCourseSchedules")
    public JAXBElement<GetCourseSchedules> createGetCourseSchedules(GetCourseSchedules value) {
        return new JAXBElement<GetCourseSchedules>(_GetCourseSchedules_QNAME, GetCourseSchedules.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCourseSchedulesResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetCourseSchedulesResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "getCourseSchedulesResponse")
    public JAXBElement<GetCourseSchedulesResponse> createGetCourseSchedulesResponse(GetCourseSchedulesResponse value) {
        return new JAXBElement<GetCourseSchedulesResponse>(_GetCourseSchedulesResponse_QNAME, GetCourseSchedulesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCoursesByProfessor }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetCoursesByProfessor }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "getCoursesByProfessor")
    public JAXBElement<GetCoursesByProfessor> createGetCoursesByProfessor(GetCoursesByProfessor value) {
        return new JAXBElement<GetCoursesByProfessor>(_GetCoursesByProfessor_QNAME, GetCoursesByProfessor.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCoursesByProfessorResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetCoursesByProfessorResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "getCoursesByProfessorResponse")
    public JAXBElement<GetCoursesByProfessorResponse> createGetCoursesByProfessorResponse(GetCoursesByProfessorResponse value) {
        return new JAXBElement<GetCoursesByProfessorResponse>(_GetCoursesByProfessorResponse_QNAME, GetCoursesByProfessorResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCoursesBySemester }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetCoursesBySemester }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "getCoursesBySemester")
    public JAXBElement<GetCoursesBySemester> createGetCoursesBySemester(GetCoursesBySemester value) {
        return new JAXBElement<GetCoursesBySemester>(_GetCoursesBySemester_QNAME, GetCoursesBySemester.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCoursesBySemesterResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetCoursesBySemesterResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "getCoursesBySemesterResponse")
    public JAXBElement<GetCoursesBySemesterResponse> createGetCoursesBySemesterResponse(GetCoursesBySemesterResponse value) {
        return new JAXBElement<GetCoursesBySemesterResponse>(_GetCoursesBySemesterResponse_QNAME, GetCoursesBySemesterResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Schedule }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Schedule }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "schedule")
    public JAXBElement<Schedule> createSchedule(Schedule value) {
        return new JAXBElement<Schedule>(_Schedule_QNAME, Schedule.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchCourses }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SearchCourses }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "searchCourses")
    public JAXBElement<SearchCourses> createSearchCourses(SearchCourses value) {
        return new JAXBElement<SearchCourses>(_SearchCourses_QNAME, SearchCourses.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchCoursesResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SearchCoursesResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "searchCoursesResponse")
    public JAXBElement<SearchCoursesResponse> createSearchCoursesResponse(SearchCoursesResponse value) {
        return new JAXBElement<SearchCoursesResponse>(_SearchCoursesResponse_QNAME, SearchCoursesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCourse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UpdateCourse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "updateCourse")
    public JAXBElement<UpdateCourse> createUpdateCourse(UpdateCourse value) {
        return new JAXBElement<UpdateCourse>(_UpdateCourse_QNAME, UpdateCourse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCourseResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UpdateCourseResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "updateCourseResponse")
    public JAXBElement<UpdateCourseResponse> createUpdateCourseResponse(UpdateCourseResponse value) {
        return new JAXBElement<UpdateCourseResponse>(_UpdateCourseResponse_QNAME, UpdateCourseResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateSchedule }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UpdateSchedule }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "updateSchedule")
    public JAXBElement<UpdateSchedule> createUpdateSchedule(UpdateSchedule value) {
        return new JAXBElement<UpdateSchedule>(_UpdateSchedule_QNAME, UpdateSchedule.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateScheduleResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UpdateScheduleResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.course.soa.university.com/", name = "updateScheduleResponse")
    public JAXBElement<UpdateScheduleResponse> createUpdateScheduleResponse(UpdateScheduleResponse value) {
        return new JAXBElement<UpdateScheduleResponse>(_UpdateScheduleResponse_QNAME, UpdateScheduleResponse.class, null, value);
    }

}

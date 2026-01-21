
package com.example.api_gateway.soap.coursews;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour course complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="course"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="credits" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="semester" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="professorId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="professorName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="capacity" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="enrolledStudents" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="schedules" type="{http://service.course.soa.university.com/}schedule" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "course", propOrder = {
    "id",
    "code",
    "name",
    "description",
    "credits",
    "semester",
    "professorId",
    "professorName",
    "capacity",
    "enrolledStudents",
    "schedules"
})
public class Course {

    protected long id;
    @XmlElement(required = true)
    protected String code;
    @XmlElement(required = true)
    protected String name;
    protected String description;
    protected int credits;
    @XmlElement(required = true)
    protected String semester;
    protected Long professorId;
    protected String professorName;
    protected Integer capacity;
    protected Integer enrolledStudents;
    protected List<Schedule> schedules;

    /**
     * Obtient la valeur de la propriété id.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Définit la valeur de la propriété id.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Obtient la valeur de la propriété code.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Définit la valeur de la propriété code.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Obtient la valeur de la propriété name.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Définit la valeur de la propriété name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Obtient la valeur de la propriété description.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Définit la valeur de la propriété description.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Obtient la valeur de la propriété credits.
     * 
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Définit la valeur de la propriété credits.
     * 
     */
    public void setCredits(int value) {
        this.credits = value;
    }

    /**
     * Obtient la valeur de la propriété semester.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSemester() {
        return semester;
    }

    /**
     * Définit la valeur de la propriété semester.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSemester(String value) {
        this.semester = value;
    }

    /**
     * Obtient la valeur de la propriété professorId.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getProfessorId() {
        return professorId;
    }

    /**
     * Définit la valeur de la propriété professorId.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setProfessorId(Long value) {
        this.professorId = value;
    }

    /**
     * Obtient la valeur de la propriété professorName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProfessorName() {
        return professorName;
    }

    /**
     * Définit la valeur de la propriété professorName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProfessorName(String value) {
        this.professorName = value;
    }

    /**
     * Obtient la valeur de la propriété capacity.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapacity() {
        return capacity;
    }

    /**
     * Définit la valeur de la propriété capacity.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapacity(Integer value) {
        this.capacity = value;
    }

    /**
     * Obtient la valeur de la propriété enrolledStudents.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEnrolledStudents() {
        return enrolledStudents;
    }

    /**
     * Définit la valeur de la propriété enrolledStudents.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEnrolledStudents(Integer value) {
        this.enrolledStudents = value;
    }

    /**
     * Gets the value of the schedules property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the schedules property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSchedules().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Schedule }
     * 
     * 
     */
    public List<Schedule> getSchedules() {
        if (schedules == null) {
            schedules = new ArrayList<Schedule>();
        }
        return this.schedules;
    }

}


package com.example.api_gateway.soap.coursews;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour getCoursesByProfessor complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="getCoursesByProfessor"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="professorId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCoursesByProfessor", propOrder = {
    "professorId"
})
public class GetCoursesByProfessor {

    protected Long professorId;

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

}

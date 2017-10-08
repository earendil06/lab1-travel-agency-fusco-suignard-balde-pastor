
package carplanner.planner.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour getCarByPlaceResponse complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="getCarByPlaceResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="car_planner_result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCarByPlaceResponse", propOrder = {
    "carPlannerResult"
})
public class GetCarByPlaceResponse {

    @XmlElement(name = "car_planner_result")
    protected String carPlannerResult;

    /**
     * Obtient la valeur de la propriété carPlannerResult.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarPlannerResult() {
        return carPlannerResult;
    }

    /**
     * Définit la valeur de la propriété carPlannerResult.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarPlannerResult(String value) {
        this.carPlannerResult = value;
    }

}

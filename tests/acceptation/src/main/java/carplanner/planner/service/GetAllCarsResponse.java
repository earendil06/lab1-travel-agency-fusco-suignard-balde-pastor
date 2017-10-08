
package carplanner.planner.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour getAllCarsResponse complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="getAllCarsResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="all_car_result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAllCarsResponse", propOrder = {
    "allCarResult"
})
public class GetAllCarsResponse {

    @XmlElement(name = "all_car_result")
    protected String allCarResult;

    /**
     * Obtient la valeur de la propriété allCarResult.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAllCarResult() {
        return allCarResult;
    }

    /**
     * Définit la valeur de la propriété allCarResult.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAllCarResult(String value) {
        this.allCarResult = value;
    }

}

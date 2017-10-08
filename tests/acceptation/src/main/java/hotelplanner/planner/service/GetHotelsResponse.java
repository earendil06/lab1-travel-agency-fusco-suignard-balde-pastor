
package hotelplanner.planner.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour getHotelsResponse complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="getHotelsResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="all_hotels_result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getHotelsResponse", propOrder = {
    "allHotelsResult"
})
public class GetHotelsResponse {

    @XmlElement(name = "all_hotels_result")
    protected String allHotelsResult;

    /**
     * Obtient la valeur de la propriété allHotelsResult.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAllHotelsResult() {
        return allHotelsResult;
    }

    /**
     * Définit la valeur de la propriété allHotelsResult.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAllHotelsResult(String value) {
        this.allHotelsResult = value;
    }

}

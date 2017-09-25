package travelapproval.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class TravelRequest {
    private String identifier;

    @XmlElement(name = "id", required = true)
    public String getIdentifier() { return identifier; }
    public void setIdentifier(String identifier) { this.identifier = identifier; }


    @Override
    public String toString() {
        return "TaxRequest:\n  identifier: " + identifier;
    }

}

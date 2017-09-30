package travel.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class TravelConfirmAnswer {
    private String identifier;
    private boolean confirmed;

    @XmlElement(name = "id", required = true)
    public String getIdentifier() { return identifier; }
    public void setIdentifier(String identifier) { this.identifier = identifier; }

    @XmlElement(name = "confirm", required = true)
    public boolean isConfirmed() { return confirmed; }
    public void setConfirmed(boolean confirmed) { this.confirmed = confirmed; }
}

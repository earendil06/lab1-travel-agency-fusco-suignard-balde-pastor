package travel.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class TravelConfirmRequest {
    private String uuidRequest;
    private boolean confirmed;

    @XmlElement(name = "uuid", required = true)
    public String getUuid() { return uuidRequest; }
    public void setUuid(String uuid) { this.uuidRequest = uuid; }

    @XmlElement(name = "confirm", required = true)
    public boolean isConfirmed() { return confirmed; }
    public void setConfirmed(boolean confirmed) { this.confirmed = confirmed; }
}

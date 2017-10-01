package travel.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class TravelSubmitAnswer {
    private String uuidRequest;
    private boolean success;

    @XmlElement
    public String getUuid() { return uuidRequest; }
    public void setUuid(String uuid) { this.uuidRequest = uuid; }

    @XmlElement
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

}

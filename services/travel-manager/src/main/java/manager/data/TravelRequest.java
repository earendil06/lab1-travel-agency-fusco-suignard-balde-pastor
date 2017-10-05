package manager.data;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.UUID;

@XmlRootElement
public class TravelRequest {
    @MongoObjectId
    public String id;

    private String uuidRequest;
    @XmlElement
    private String email;
    @XmlElement
    private List<String> hotels;
    @XmlElement
    private List<String> flights;

    public TravelRequest() {
    }

    public TravelRequest(String email, List<String> hotels, List<String> flights) {
        this.uuidRequest = UUID.randomUUID().toString();
        this.email = email;
        this.hotels = hotels;
        this.flights = flights;
    }

    public String getUuidRequest() {
        return uuidRequest;
    }
    public void setUuidRequest(String uuidRequest) {
        this.uuidRequest = uuidRequest;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<String> getHotels() { return hotels; }
    public void setHotels(List<String> hotels) { this.hotels = hotels; }

    public List<String> getFlights() { return flights; }
    public void setFlights(List<String> flights) { this.flights = flights; }

    public JSONObject toJson() {
        return new JSONObject()
                .put("uuidRequest", this.uuidRequest)
                .put("email", this.email)
                .put("hotels", this.hotels)
                .put("flights", this.flights);
    }

    @Override
    public String toString() {
        return "TravelRequest{" +
                "uuidRequest='" + uuidRequest + '\'' +
                ", email='" + email + '\'' +
                ", hotels=" + hotels +
                ", flights=" + flights +
                '}';
    }
}

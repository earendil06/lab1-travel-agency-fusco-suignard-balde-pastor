package manager.service;

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
    private String owner;
    @XmlElement
    private List<String> hotels;
    @XmlElement
    private List<String> flights;

    public TravelRequest() {
    }

    public TravelRequest(String owner, List<String> hotels, List<String> flights) {
        this.uuidRequest = UUID.randomUUID().toString();
        this.owner = owner;
        this.hotels = hotels;
        this.flights = flights;
    }

    public String getUuidRequest() {
        return uuidRequest;
    }
    public void setUuidRequest(String uuidRequest) {
        this.uuidRequest = uuidRequest;
    }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public List<String> getHotels() { return hotels; }
    public void setHotels(List<String> hotels) { this.hotels = hotels; }

    public List<String> getFlights() { return flights; }
    public void setFlights(List<String> flights) { this.flights = flights; }

    JSONObject toJson() {
        return new JSONObject()
                .put("uuidRequest", this.uuidRequest)
                .put("owner", this.owner)
                .put("hotels", this.hotels)
                .put("flights", this.flights);
    }

    @Override
    public String toString() {
        return "TravelRequest{" +
                "uuidRequest='" + uuidRequest + '\'' +
                ", owner='" + owner + '\'' +
                ", hotels=" + hotels +
                ", flights=" + flights +
                '}';
    }
}

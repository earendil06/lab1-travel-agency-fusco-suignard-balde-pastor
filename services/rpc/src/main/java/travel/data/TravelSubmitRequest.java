package travel.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType
public class TravelSubmitRequest {
    private String owner;
    private List<String> hotels;
    private List<String> flights;

    @XmlElement(name = "owner", required = true)
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    @XmlElement(name = "hotels", required = true)
    public List<String> getHotels() { return hotels; }
    public void setHotels(List<String> hotels) { this.hotels = hotels; }

    @XmlElement(name = "flights", required = true)
    public List<String> getFlights() { return flights; }
    public void setFlights(List<String> flights) { this.flights = flights; }
}

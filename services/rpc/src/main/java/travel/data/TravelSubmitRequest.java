package travel.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType
public class TravelSubmitRequest {
    private String identifier;
    private List<String> hotels;
    private List<String> flights;

    @XmlElement(name = "id")
    public String getIdentifier() { return identifier; }
    public void setIdentifier(String identifier) { this.identifier = identifier; }

    @XmlElement(name = "hotels", required = true)
    public List<String> getHotels() { return hotels; }
    public void setHotels(List<String> hotels) { this.hotels = hotels; }

    @XmlElement(name = "flights", required = true)
    public List<String> getFlights() { return flights; }
    public void setFlights(List<String> flights) { this.flights = flights; }
}

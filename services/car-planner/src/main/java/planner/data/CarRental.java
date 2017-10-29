package planner.data;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.UUID;

@XmlType
public class CarRental {
    private String name;
    private String place;
    private int duration;
    private String uid;
    private int price;

    public CarRental() {
    }

    @MongoObjectId
    String id;

    @XmlElement
    public String getName() {
        return name;
    }
    @XmlElement
    public String getPlace() {
        return place;
    }
    @XmlElement
    public int getDuration() {
        return duration;
    }
    @XmlElement
    public String getUid() {
        return uid;
    }

    @XmlElement
    public int getPrice() {
        return price;
    }

    public CarRental(String name, String place, int duration, int price) {
        this(UUID.randomUUID().toString(), name, place, duration, price);
    }

    public CarRental(String uuid, String name, String place, int duration, int price) {
        this.uid = uuid;
        this.name = name;
        this.place = place;
        this.duration = duration;
        this.price = price;
    }

    JSONObject toJson() {
        return new JSONObject()
                .put("uid", this.uid)
                .put("name", this.name)
                .put("place", this.place)
                .put("price", this.price)
                .put("duration", this.duration);
    }

    @Override
    public String toString() {
        return "CarRental{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", place='" + place + '\'' +
                ", duration=" + duration +
                ", uid='" + uid + '\'' +
                '}';
    }
}

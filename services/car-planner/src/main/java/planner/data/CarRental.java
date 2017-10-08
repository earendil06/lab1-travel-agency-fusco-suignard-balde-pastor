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

    public CarRental(String name, String place, int duration) {
        this.uid = UUID.randomUUID().toString();
        this.name = name;
        this.place = place;
        this.duration = duration;
    }

    JSONObject toJson() {
        return new JSONObject()
                .put("uid", this.uid)
                .put("name", this.name)
                .put("place", this.place)
                .put("duration", this.duration);
    }

    @Override
    public String toString() {
        return "CarRental{" +
                "name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", duration=" + duration +
                ", uid='" + uid + '\'' +
                '}';
    }
}

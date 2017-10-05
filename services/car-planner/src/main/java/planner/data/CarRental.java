package planner.data;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

import java.util.UUID;

public class CarRental {
    private String name;
    private String place;
    private int duration;
    private String uid;

    public CarRental() {
    }

    @MongoObjectId
    String id;

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public int getDuration() {
        return duration;
    }

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

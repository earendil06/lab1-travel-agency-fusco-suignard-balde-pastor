package planner.data;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

public class CarRental {
    private String name;
    private String place;
    private int duration;

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

    public CarRental(String name, String place, int duration) {
        this.name = name;
        this.place = place;
        this.duration = duration;
    }

    JSONObject toJson() {
        return new JSONObject()
                .put("name", this.name)
                .put("place", this.place)
                .put("duration", this.duration);
    }

    @Override
    public String toString() {
        return "old.CarRental{" +
                "name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}

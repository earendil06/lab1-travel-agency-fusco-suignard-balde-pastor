package oldhotel;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

public class HotelRental {
    private String name;
    private String place;
    private String date;

    @MongoObjectId
    String id;

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public String getDate() {
        return date;
    }

    public HotelRental() {
    }

    public HotelRental(String name, String place, String date) {
        this.name = name;
        this.place = place;
        this.date = date;
    }

    JSONObject toJson() {
        return new JSONObject()
                .put("place", this.place)
                .put("date", this.date);
    }

    @Override
    public String toString() {
        return "HotelRental{" +
                "name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}

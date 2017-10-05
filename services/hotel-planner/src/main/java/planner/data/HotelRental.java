package planner.data;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

import java.util.UUID;

public class HotelRental {
    private String name;
    private String place;
    private int day;
    private int month;
    private int year;
    private int price;
    private String uid;

    @MongoObjectId
    String id;

    public HotelRental() {
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getPrice() {
        return price;
    }

    public String getUid() {
        return uid;
    }

    public HotelRental(String name, String place, int day, int month, int year, int price) {
        this.uid = UUID.randomUUID().toString();
        this.name = name;
        this.place = place;
        this.day = day;
        this.month = month;
        this.year = year;
        this.price = price;
    }

    JSONObject toJson() {
        return new JSONObject()
                .put("uid", uid)
                .put("name", this.name)
                .put("place", this.place)
                .put("day", this.day)
                .put("month", this.month)
                .put("year", this.year)
                .put("price", this.price);
    }

    @Override
    public String toString() {
        return "HotelRental{" +
                "name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", price=" + price +
                ", uid='" + uid + '\'' +
                '}';
    }
}

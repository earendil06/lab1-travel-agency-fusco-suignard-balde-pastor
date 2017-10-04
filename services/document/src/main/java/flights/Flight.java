package flights;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.Date;

public class Flight {

    @AttributeQueryable
    private String from;

    @AttributeQueryable
    private String to;

    @AttributeQueryable
    private String date;

    @AttributeQueryable
    private String hour;

    @AttributeQueryable
    private int duration;

    @AttributeQueryable
    private double price;

    @AttributeQueryable
    private boolean direct;

    @MongoObjectId
    String id;

    public Flight() {}

    public Flight(String from, String to, String date, String hour, int duration, double price, boolean direct) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.hour = hour;
        this.duration = duration;
        this.price = price;
        this.direct= direct;
    }

    public Flight(JSONObject data) {
        this.from = data.getString("from");
        this.to = data.getString("to");
        this.date = data.getString("date");
        this.hour = data.getString("hour");
        this.duration = data.getInt("duration");
        this.price = data.getDouble("price");
        this.direct = data.getBoolean("direct");
    }


    JSONObject toJson() {
        return new JSONObject()
                .put("from", this.from)
                .put("to", this.to)
                .put("date", this.date)
                .put("hour", hour)
                .put("duration", this.duration)
                .put("price", this.price)
                .put("direct", this.direct);
    }

}

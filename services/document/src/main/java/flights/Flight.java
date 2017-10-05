package flights;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

import java.text.ParseException;

public class Flight {

    @AttributeQueryable(order = true, filter = true)
    private String from;

    @AttributeQueryable(order = true, filter = true)
    private String to;

    @AttributeQueryable(filter = true)
    private String date;

    @AttributeQueryable(filter = true)
    private String hour;

    @AttributeQueryable(order = true, filter = true, minMax = true)
    private int duration;

    @AttributeQueryable(order = true, filter = true, minMax = true)
    private double price;

    @AttributeQueryable(order = true, filter = true)
    private boolean direct;

    @MongoObjectId
    String id;

    public Flight() {}

    public Flight(String from, String to, String date, String hour, int duration, double price, boolean direct) throws ParseException {
        this.from = from;
        this.to = to;
        this.date = date;
        this.hour = hour;
        this.duration = duration;
        this.price = price;
        this.direct= direct;
    }

    public Flight(JSONObject data) throws ParseException {
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
                .put("hour", this.hour)
                .put("duration", this.duration)
                .put("price", this.price)
                .put("direct", this.direct);
    }

}

package flights;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

import java.util.Date;

public class Flight {

    private String from;
    private String to;
    private String dateFrom;
    private int duration;
    private double price;
    private boolean directFlight;

    @MongoObjectId
    String id;

    public Flight() {}

    public Flight(String from, String to, String dateFrom, int duration, double price, boolean directFlight) {
        this.from = from;
        this.to = to;
        this.dateFrom = dateFrom;
        this.duration = duration;
        this.price = price;
        this.directFlight = directFlight;
    }

    public Flight(JSONObject data) {
        this.from = data.getString("from");
        this.to = data.getString("to");
        this.dateFrom = data.getString("dateFrom");
        this.duration = data.getInt("duration");
        this.price = data.getDouble("price");
        this.directFlight = data.getBoolean("directFlight");
    }


    JSONObject toJson() {
        return new JSONObject()
                .put("from", this.from)
                .put("to", this.to)
                .put("dateFrom", this.dateFrom)
                .put("duration", this.duration)
                .put("price", this.price)
                .put("directFlight", this.directFlight);
    }

}

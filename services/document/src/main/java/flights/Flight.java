package flights;

import org.joda.time.format.DateTimeFormat;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Flight {

    @AttributeQueryable(order = true, filter = true)
    private String from;

    @AttributeQueryable(order = true, filter = true)
    private String to;

//    @AttributeQueryable(order = true, filter = true)
//    private Date date;

    @AttributeQueryable(order = true, filter = true, minMax = true)
    private int duration;

    @AttributeQueryable(order = true, filter = true, minMax = true)
    private double price;

    @AttributeQueryable(order = true, filter = true)
    private boolean direct;

    @MongoObjectId
    String id;

    private static final String DATE_PATTERN = "dd/MM/yyyy-HH:mm";

    public Flight() {}

    public Flight(String from, String to, String date, int duration, double price, boolean direct) throws ParseException {
        this.from = from;
        this.to = to;
        //this.date = new SimpleDateFormat(DATE_PATTERN).parse(date);
        this.duration = duration;
        this.price = price;
        this.direct= direct;
    }

    public Flight(JSONObject data) throws ParseException {
        this.from = data.getString("from");
        this.to = data.getString("to");
        //this.date = new SimpleDateFormat(DATE_PATTERN).parse(data.getString("date"));
        this.duration = data.getInt("duration");
        this.price = data.getDouble("price");
        this.direct = data.getBoolean("direct");
    }


    JSONObject toJson() {
        return new JSONObject()
                .put("from", this.from)
                .put("to", this.to)
                //.put("date", new SimpleDateFormat(DATE_PATTERN).format(this.date))
                .put("duration", this.duration)
                .put("price", this.price)
                .put("direct", this.direct);
    }

}

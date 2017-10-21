package flights;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Flight {

    @AttributeQueryable(order = true, filter = true)
    private String from;

    @AttributeQueryable(order = true, filter = true)
    private String to;

    @AttributeQueryable(filter = true, order = true, minMax = true)
    private Date date;

    @AttributeQueryable(filter = true, order = true, minMax = true)
    private Date hour;

    @AttributeQueryable(order = true, filter = true, minMax = true)
    private int duration;

    @AttributeQueryable(order = true, filter = true, minMax = true)
    private double price;

    @AttributeQueryable(order = true, filter = true)
    private boolean direct;

    @MongoObjectId
    String id;

    public static final String DATE_PATTERN = "dd.MM.yyyy";
    public static final String HOUR_PATTERN = "HH.mm";
    public Flight() {}

    public Flight(String from, String to, Date date, Date hour, int duration, double price, boolean direct) throws ParseException {
        this.from = from;
        this.to = to;
        this.date = date;
        this.hour = hour;
        this.duration = duration;
        this.price = price;
        this.direct= direct;
    }

    public Flight(JSONObject data) throws ParseException {
        DateFormat format = new SimpleDateFormat(Flight.DATE_PATTERN);
        DateFormat hourformat = new SimpleDateFormat(Flight.HOUR_PATTERN);
        this.from = data.getString("from");
        this.to = data.getString("to");
        this.date = format.parse(data.getString("date"));
        this.hour = hourformat.parse(data.getString("hour"));
        this.duration = data.getInt("duration");
        this.price = data.getDouble("price");
        this.direct = data.getBoolean("direct");
    }


    public JSONObject toJson() {
        DateFormat df = new SimpleDateFormat(DATE_PATTERN);
        DateFormat hf = new SimpleDateFormat(HOUR_PATTERN);
        return new JSONObject()
                .put("from", this.from)
                .put("to", this.to)
                .put("date", df.format(this.date))
                .put("hour", hf.format(this.hour))
                .put("duration", this.duration)
                .put("price", this.price)
                .put("direct", this.direct);
    }

}

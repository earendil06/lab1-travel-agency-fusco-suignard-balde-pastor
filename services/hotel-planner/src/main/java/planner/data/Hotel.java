package planner.data;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class Hotel {
    private String name;
    private String place;
    private int price;
    private String uid;
    private Date from;
    private Date to;


    public static final String DATE_PATTERN = "dd/MM/yyyy";


    @MongoObjectId
    String id;

    public Hotel() {
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public int getPrice() {
        return price;
    }

    public String getUid() {
        return uid;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }

    public Hotel(String name, String place, int price, Date from, Date to) {
        this.uid = UUID.randomUUID().toString();
        this.name = name;
        this.place = place;
        this.price = price;
        this.from = from;
        this.to = to;
    }

    JSONObject toJson() {
        DateFormat df = new SimpleDateFormat(DATE_PATTERN);
        return new JSONObject()
                .put("uid", uid)
                .put("name", this.name)
                .put("place", this.place)
                .put("price", this.price)
                .put("from", df.format(this.from))
                .put("to", df.format(this.to));
    }


    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat(DATE_PATTERN);
        return "Hotel{" +
                "name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", price=" + price +
                ", uid='" + uid + '\'' +
                ", from=" + df.format(this.from) +
                ", to=" + df.format(this.to) +
                '}';
    }
}

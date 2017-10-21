package planner.data;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class Hotel {
    private String name;
    private String place;
    private int price;
    private String uid;
    private Date dateArrival;
    private Date dateDeparture;


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

    public Date getDateArrival() {
        return dateArrival;
    }

    public Date getDateDeparture() {
        return dateDeparture;
    }

    public Hotel(String name, String place, int price, Date dateArrival, Date dateDeparture) {
        this.uid = UUID.randomUUID().toString();
        this.name = name;
        this.place = place;
        this.price = price;
        this.dateArrival = dateArrival;
        this.dateDeparture = dateDeparture;
    }

    JSONObject toJson() {
        DateFormat df = new SimpleDateFormat(DATE_PATTERN);
        return new JSONObject()
                .put("uid", uid)
                .put("name", this.name)
                .put("place", this.place)
                .put("price", this.price)
                .put("dateArrival", df.format(this.dateArrival))
                .put("dateDeparture", df.format(this.dateDeparture));
    }


    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat(DATE_PATTERN);
        return "Hotel{" +
                "name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", price=" + price +
                ", uid='" + uid + '\'' +
                ", dateArrival=" + df.format(this.dateArrival) +
                ", dateDeparture=" + df.format(this.dateDeparture) +
                '}';
    }
}

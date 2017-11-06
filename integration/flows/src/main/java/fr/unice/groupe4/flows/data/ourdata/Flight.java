package fr.unice.groupe4.flows.data.ourdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Flight implements Serializable {
    @JsonProperty("from")
    private String from;

    @JsonProperty("to")
    private String to;

    @JsonProperty("date")
    private String date;

    @JsonProperty("hour")
    private String hour;

    @JsonProperty("duration")
    private int duration;

    @JsonProperty("price")
    private double price = 0;

    @JsonProperty("direct")
    private boolean direct;

    @JsonProperty("uid")
    private String uid;

    public Flight() {
    }

    public Flight(Flight other) {
        this.from = other.from;
        this.to = other.to;
        this.date = other.date;
        this.hour = other.hour;
        this.duration = other.duration;
        this.price = other.price;
        this.direct = other.direct;
        this.uid = other.uid;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isDirect() {
        return direct;
    }

    public void setDirect(boolean direct) {
        this.direct = direct;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                ", duration=" + duration +
                ", price=" + price +
                ", direct=" + direct +
                ", uid='" + uid + '\'' +
                '}';
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("from", from);
        map.put("to", to);
        return map;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

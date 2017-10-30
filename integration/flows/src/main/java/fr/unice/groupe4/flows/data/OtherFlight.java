package fr.unice.groupe4.flows.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class OtherFlight implements Serializable {
    @JsonProperty("destination")
    private String destination;

    @JsonProperty("date")
    private String date;

    @JsonProperty("id")
    private String id;

    @JsonProperty("price")
    private double price;

    @JsonProperty("getIsDirect")
    private boolean isDirect;

    @JsonProperty("stops")
    private List<String> stops;

    public OtherFlight() {
    }

    @Override
    public String toString() {
        return "OtherFlight{" +
                "destination='" + destination + '\'' +
                ", date='" + date + '\'' +
                ", id='" + id + '\'' +
                ", price=" + price +
                ", getIsDirect=" + isDirect +
                ", stops=" + stops +
                '}';
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isDirect() {
        return isDirect;
    }

    public void setDirect(boolean direct) {
        isDirect = direct;
    }

    public List<String> getStops() {
        return stops;
    }

    public void setStops(List<String> stops) {
        this.stops = stops;
    }
}

package fr.unice.groupe4.flows.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class TravelFlight implements Serializable {
    @JsonProperty("destination")
    private String destination;

    @JsonProperty("date")
    private String date;

    @JsonProperty("id")
    private String id;

    @JsonProperty("price")
    private double price;

    @JsonProperty("isDirect")
    private boolean isDirect;

    public TravelFlight() {
    }

    public TravelFlight(OtherFlight otherFlight) {
        this.date = otherFlight.getDate();
        this.destination = otherFlight.getDestination();
        this.id = otherFlight.getId();
        this.isDirect = otherFlight.isDirect();
        this.price = otherFlight.getPrice();
    }

    public TravelFlight(Flight otherFlight) {
        this.date = otherFlight.getDate();
        this.destination = otherFlight.getTo();
        this.id = otherFlight.getUid();
        this.isDirect = otherFlight.isDirect();
        this.price = otherFlight.getPrice();
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

    public boolean getIsDirect() {
        return isDirect;
    }

    public void setIsDirect(boolean direct) {
        isDirect = direct;
    }

    @Override
    public String toString() {
        return "TravelFlight{" +
                "destination='" + destination + '\'' +
                ", date='" + date + '\'' +
                ", id='" + id + '\'' +
                ", price=" + price +
                ", isDirect=" + isDirect +
                '}';
    }
}

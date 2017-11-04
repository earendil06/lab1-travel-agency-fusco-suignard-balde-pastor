package fr.unice.groupe4.flows.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class TravelHotel implements Serializable {
    @JsonProperty("place")
    private String place;
    @JsonProperty("dateArrival")
    private String dateArrival;
    @JsonProperty("dateDeparture")
    private String dateDeparture;
    @JsonProperty("price")
    private int price = 0;
    @JsonProperty("name")
    private String name = "PastorHotel";
    @JsonProperty("uid")
    private String uid = "";

    public TravelHotel() {
    }

    public TravelHotel(Hotel origin) {
        this.place = origin.getPlace();
        this.dateArrival = origin.getDateArrival();
        this.dateDeparture = origin.getDateDeparture();
        this.price = origin.getPrice();
        this.name = origin.getName();
        this.uid = origin.getUid();
    }

    public TravelHotel(OtherHotel origin) {
        this.place = origin.getCity();
        this.dateArrival = origin.getArrivalDate();
        this.dateDeparture = origin.getDepartureDate();
        this.price = (int) (origin.getAmount() * origin.getNumberOfNight());
        this.name = origin.getName();
        this.uid = String.valueOf(origin.getId());
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDateArrival() {
        return dateArrival;
    }

    public void setDateArrival(String dateArrival) {
        this.dateArrival = dateArrival;
    }

    public String getDateDeparture() {
        return dateDeparture;
    }

    public void setDateDeparture(String dateDeparture) {
        this.dateDeparture = dateDeparture;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "TravelHotel{" +
                "place='" + place + '\'' +
                ", dateArrival='" + dateArrival + '\'' +
                ", dateDeparture='" + dateDeparture + '\'' +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }

    public int getPrice() {
        return price;
    }
}

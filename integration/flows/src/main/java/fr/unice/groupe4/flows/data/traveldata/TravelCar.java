package fr.unice.groupe4.flows.data.traveldata;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.groupe4.flows.data.otherdata.OtherCar;
import fr.unice.groupe4.flows.data.ourdata.Car;

import java.io.Serializable;

public class TravelCar implements Serializable {
    @JsonProperty("name")
    private String name;
    @JsonProperty("place")
    private String place;
    @JsonProperty("duration")
    private int duration;
    @JsonProperty("uid")
    private String uid;
    @JsonProperty("price")
    private double price;

    public TravelCar(String name, String place, int duration, String uid, int price) {
        this.name = name;
        this.place = place;
        this.duration = duration;
        this.uid = uid;
        this.price = price;
    }

    public TravelCar(OtherCar otherCar) {
        this.name = otherCar.getAgency().getName();
        this.place = otherCar.getAgency().getCity();
        this.duration = otherCar.getDuration();
        this.uid = String.valueOf(otherCar.getId());
        this.price = otherCar.getPrice();
    }

    public TravelCar(Car otherCar) {
        this.name = otherCar.getName();
        this.place = otherCar.getPlace();
        this.duration = otherCar.getDuration();
        this.uid = otherCar.getUid();
        this.price = otherCar.getPrice();
    }

    public TravelCar() {
    }

    @Override
    public String toString() {
        return "TravelCar{" +
                "name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", duration=" + duration +
                ", uid='" + uid + '\'' +
                ", price=" + price +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

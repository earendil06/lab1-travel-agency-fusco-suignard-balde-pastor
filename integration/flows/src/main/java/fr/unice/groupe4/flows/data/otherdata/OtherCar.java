package fr.unice.groupe4.flows.data.otherdata;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OtherCar implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("agency")
    private Agency agency;
    @SerializedName("priceperday")
    private double price;
    private int duration;

    public OtherCar(int id, Agency agency, int price, int duration) {
        this.id = id;
        this.agency = agency;
        this.price = price;
        this.duration = duration;
    }

    public OtherCar() {
    }

    @Override
    public String toString() {
        return "OtherCar{" +
                "id=" + id +
                ", agency=" + agency +
                ", price=" + price +
                ", duration=" + duration +
                '}';
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

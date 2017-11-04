package fr.unice.groupe4.flows.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OtherHotel implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("hotel_name")
    private String name; // json : hotel_name (unique)
    @SerializedName("city")
    private String city; // json : city
    @SerializedName("hotel_type")
    private String type; // json : hotel_type
    @SerializedName("price_per_night")
    private double amount; // json : price_per_night

    @SerializedName("departure_date")
    private String departureDate; // json : departure_date
    @SerializedName("arrival_date")
    private String arrivalDate; // json : arrival_date
    @SerializedName("number_of_night")
    private int numberOfNight; // json : number_of_night


    public OtherHotel(int id, String name, String city, String type, double amount, String departureDate, String arrivalDate, int numberOfNight) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.type = type;
        this.amount = amount;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.numberOfNight = numberOfNight;
    }

    public OtherHotel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public int getNumberOfNight() {
        return numberOfNight;
    }

    public void setNumberOfNight(int numberOfNight) {
        this.numberOfNight = numberOfNight;
    }

    @Override
    public String toString() {
        return "OtherHotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", departureDate='" + departureDate + '\'' +
                ", arrivalDate='" + arrivalDate + '\'' +
                ", numberOfNight='" + numberOfNight + '\'' +
                '}';
    }
}

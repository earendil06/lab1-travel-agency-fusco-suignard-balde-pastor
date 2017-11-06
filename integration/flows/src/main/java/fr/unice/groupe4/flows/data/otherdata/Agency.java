package fr.unice.groupe4.flows.data.otherdata;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Agency implements Serializable {
    @SerializedName("country")
    private String country;
    @SerializedName("address")
    private String address;
    @SerializedName("city")
    private String city;
    @SerializedName("name")
    private String name;

    public Agency(String country, String address, String city, String name) {
        this.country = country;
        this.address = address;
        this.city = city;
        this.name = name;
    }

    public Agency() {
    }

    @Override
    public String toString() {
        return "Agency{" +
                "country=" + country +
                ", address=" + address +
                ", city=" + city +
                ", name=" + name +
                '}';
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

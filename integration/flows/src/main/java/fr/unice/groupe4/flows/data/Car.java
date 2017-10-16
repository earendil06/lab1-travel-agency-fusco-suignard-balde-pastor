package fr.unice.groupe4.flows.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Car implements Serializable {
    //private date... //todo do it...
    @JsonProperty("place") private String place;
    @JsonProperty("hours") private int hours;

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    @Override
    public String toString() {
        return "Car{" +
                "place='" + place + '\'' +
                ", hours=" + hours +
                '}';
    }
}

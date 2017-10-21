package fr.unice.groupe4.flows.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Car implements Serializable {
    //private date... //todo do it...
    @JsonProperty("place") private String place;
    @JsonProperty("hours") private String hours;

    public Car() {
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
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

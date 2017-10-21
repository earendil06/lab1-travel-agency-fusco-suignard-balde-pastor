package fr.unice.groupe4.flows.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Car implements Serializable {
    //private date... //todo do it...
//    @JsonProperty("place") private String place;
//    @JsonProperty("hours") private String hours;

    @JsonProperty("name")
    private String name;
    @JsonProperty("place")
    private String place;
    @JsonProperty("duration")
    private int duration;
    @JsonProperty("uid")
    private String uid;

    public Car() {
    }

    public Car(Car other) {
        this.name = other.name;
        this.place = other.place;
        this.duration = other.duration;
        this.uid = other.uid;
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

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", duration=" + duration +
                ", uid='" + uid + '\'' +
                '}';
    }
}

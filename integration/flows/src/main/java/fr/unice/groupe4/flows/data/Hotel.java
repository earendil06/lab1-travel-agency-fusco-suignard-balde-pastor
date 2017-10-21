package fr.unice.groupe4.flows.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Hotel implements Serializable {
    @JsonProperty("place")
    private String place;
    @JsonProperty("dateArrival")
    private String dateArrival;
    @JsonProperty("dateDeparture")
    private String dateDeparture;

    public Hotel() {
    }

    public Hotel(Hotel origin) {
        this.place = origin.place;
        this.dateArrival = origin.dateArrival;
        this.dateDeparture = origin.dateDeparture;
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

    @Override
    public String toString() {
        return "Hotel{" +
                "place='" + place + '\'' +
                ", dateArrival='" + dateArrival + '\'' +
                ", dateDeparture='" + dateDeparture + '\'' +
                '}';
    }
}

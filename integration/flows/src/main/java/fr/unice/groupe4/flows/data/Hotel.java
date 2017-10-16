package fr.unice.groupe4.flows.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Hotel implements Serializable {
    @JsonProperty("destination") private String destination;
    @JsonProperty("nbNights") private int nbNights;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getNbNights() {
        return nbNights;
    }

    public void setNbNights(int nbNights) {
        this.nbNights = nbNights;
    }
}

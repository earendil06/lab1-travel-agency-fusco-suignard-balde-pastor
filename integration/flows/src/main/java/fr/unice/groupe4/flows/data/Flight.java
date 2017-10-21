package fr.unice.groupe4.flows.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Flight implements Serializable {
    @JsonProperty("destination") private String destination;

    public Flight() {
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "destination='" + destination + '\'' +
                '}';
    }
}

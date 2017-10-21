package fr.unice.groupe4.flows.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class TravelRequest implements Serializable {
    @JsonProperty("email") private String email;
    @JsonProperty("flight") private Flight flight;
    @JsonProperty("hotel") private Hotel hotel;
    @JsonProperty("car") private Car car;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "TravelRequest{" +
                "email='" + email + '\'' +
                ", flight=" + flight +
                ", hotel=" + hotel +
                ", car=" + car +
                '}';
    }
}
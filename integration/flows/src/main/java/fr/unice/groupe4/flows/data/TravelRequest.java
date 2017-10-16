package fr.unice.groupe4.flows.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class TravelRequest implements Serializable {
    @JsonProperty("email") private String email;
    @JsonProperty("flights") private List<Flight> flights;
    @JsonProperty("hotels") private List<Hotel> hotels;
    @JsonProperty("cars") private List<Car> cars;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public String toString() {
        return "TravelRequest{" +
                "email='" + email + '\'' +
                ", flights=" + flights +
                ", hotels=" + hotels +
                ", cars=" + cars +
                '}';
    }
}

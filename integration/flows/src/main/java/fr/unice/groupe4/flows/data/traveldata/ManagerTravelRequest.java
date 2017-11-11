package fr.unice.groupe4.flows.data.traveldata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class ManagerTravelRequest implements Serializable {
    @JsonProperty("email")
    private String email;
    @JsonProperty("flights")
    private List<String> flights;
    @JsonProperty("hotels")
    private List<String> hotels;
    @JsonProperty("cars")
    private List<String> cars;

    public ManagerTravelRequest(String email, List<String> flights, List<String> hotels, List<String> cars) {
        this.email = email;
        this.flights = flights;
        this.hotels = hotels;
        this.cars = cars;
    }

    public ManagerTravelRequest(TravelRequest request) {
        this.email = request.getEmail();
        Gson gson = new Gson();
        this.flights = Collections.singletonList(gson.toJson(request.getFlight()));
        this.hotels = Collections.singletonList(gson.toJson(request.getHotel()));
        this.cars = Collections.singletonList(gson.toJson(request.getCar()));
    }

    public ManagerTravelRequest() {
    }

    @Override
    public String toString() {
        return "ManagerTravelRequest{" +
                "email='" + email + '\'' +
                ", flights=" + flights +
                ", hotels=" + hotels +
                ", cars=" + cars +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getFlights() {
        return flights;
    }

    public void setFlights(List<String> flights) {
        this.flights = flights;
    }

    public List<String> getHotels() {
        return hotels;
    }

    public void setHotels(List<String> hotels) {
        this.hotels = hotels;
    }

    public List<String> getCars() {
        return cars;
    }

    public void setCars(List<String> cars) {
        this.cars = cars;
    }
}

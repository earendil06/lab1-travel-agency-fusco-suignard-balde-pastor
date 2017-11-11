package fr.unice.groupe4.flows.data.traveldata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

import java.io.Serializable;

public class TravelRequest implements Serializable {
    @JsonProperty("email")
    private String email;
    @JsonProperty("flight")
    private TravelFlight flight;
    @JsonProperty("hotel")
    private TravelHotel hotel;
    @JsonProperty("car")
    private TravelCar car;

    public TravelRequest() {
    }

    public TravelRequest(ManagerTravelRequest managerTravelRequest) {
        this.email = managerTravelRequest.getEmail();
        Gson gson = new Gson();
        this.flight = gson.fromJson(managerTravelRequest.getFlights().get(0), TravelFlight.class);
        this.car = gson.fromJson(managerTravelRequest.getCars().get(0), TravelCar.class);
        this.hotel = gson.fromJson(managerTravelRequest.getHotels().get(0), TravelHotel.class);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TravelFlight getFlight() {
        return flight;
    }

    public void setFlight(TravelFlight flight) {
        this.flight = flight;
    }

    public TravelHotel getHotel() {
        return hotel;
    }

    public void setHotel(TravelHotel hotel) {
        this.hotel = hotel;
    }

    public TravelCar getCar() {
        return car;
    }

    public void setCar(TravelCar car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "TravelRequest{" + "\n" +
                "email='" + email + '\'' + "\n" +
                "flight=" + flight + "\n" +
                "hotel=" + hotel + "\n" +
                "car=" + car + "\n" +
                '}';
    }
}

package fr.unice.groupe4.flows.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.unice.groupe4.flows.data.otherdata.OtherFlight;
import fr.unice.groupe4.flows.data.ourdata.Flight;
import fr.unice.groupe4.flows.data.traveldata.TravelFlight;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.lang.reflect.Type;
import java.util.*;

public class FlightHelper {
    public String buildGetFlightForTravel(TravelFlight flight) {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        if (flight.getDate() != null) {
            map.put("date", flight.getDate());
        }
        map.put("to", flight.getDestination());
        map.put("event", "RETRIEVE");
        return gson.toJson(map);
    }

    //{ "event": "LIST", "filter": { "destination":"Menton", "date":"18.10.2017" } }
    public String buildGetFlightForTravelOther(TravelFlight flight) {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> filter = new HashMap<>();
        if (flight.getDestination() == null || flight.getDestination().isEmpty()) {
            return "";
        }
        filter.put("destination", flight.getDestination());
        if (flight.getDate() != null) {
            filter.put("date", flight.getDate());
        }
        map.put("event", "LIST");
        map.put("filter", filter);
        return gson.toJson(map);
    }

    public static AggregationStrategy mergeFlights = (oldExchange, newExchange) -> {
        if (oldExchange == null) {
            return newExchange;
        } else {
            TravelFlight otherFlight = newExchange.getIn().getBody(TravelFlight.class);
            TravelFlight flight = oldExchange.getIn().getBody(TravelFlight.class);
            if (flight == null && otherFlight == null) {
                System.out.println("ALL FLIGHTS NULL");
                oldExchange.getIn().setBody(null);
                return oldExchange;
            }
            if (flight == null || flight.getPrice() == 0 || otherFlight.getPrice() < flight.getPrice()) {
                oldExchange.getIn().setBody(otherFlight);
            } else {
                oldExchange.getIn().setBody(flight);
            }
        }
        return oldExchange;
    };


    public static Processor result2Flight = (Exchange exc) -> {
        String input = exc.getIn().getBody(String.class);
        if (input == null) {
            exc.getIn().setBody(null);
            return;
        }
        Type listType = new TypeToken<ArrayList<Flight>>() {
        }.getType();
        List<Flight> flights;
        try {
            flights = new Gson().fromJson(input, listType);
        } catch (com.google.gson.JsonSyntaxException e) {
            System.out.println(e + " NO FLIGHTS " + input);
            return;
        }
        if (flights == null || flights.isEmpty()) {
            System.out.println("NO FLIGHTS");
            return;
        }
        Flight flightMin = flights.stream().min(Comparator.comparingDouble(Flight::getPrice)).get();
        TravelFlight min = new TravelFlight(flightMin);
        exc.getIn().setBody(min);
    };

    public static Processor result2OtherFlight = (Exchange exc) -> {
        String input = exc.getIn().getBody(String.class);
        if (input == null) {
            exc.getIn().setBody(null);
            return;
        }
        Type mapType = new TypeToken<Map<String, Object>>() {
        }.getType();
        Type flightType = new TypeToken<List<OtherFlight>>() {
        }.getType();
        Map<String, Object> map;
        List<OtherFlight> flights;
        try {
            map = new Gson().fromJson(input, mapType);
            if (map == null) {
                System.out.println("NO Other FLIGHTS");
                return;
            }
            flights = new Gson().fromJson(map.get("vols").toString(), flightType);
        } catch (com.google.gson.JsonSyntaxException e) {
            System.out.println(e + " NO Other FLIGHTS");
            return;
        }
        if (flights == null || flights.isEmpty()) {
            System.out.println("NO Other FLIGHTS");
            return;
        }
        OtherFlight flightMin = flights.stream().min(Comparator.comparingDouble(OtherFlight::getPrice)).get();
        TravelFlight min = new TravelFlight(flightMin);
        exc.getIn().setBody(min);
    };
}

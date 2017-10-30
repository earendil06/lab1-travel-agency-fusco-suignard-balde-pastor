package fr.unice.groupe4.flows.utils;

import com.google.gson.Gson;
import fr.unice.groupe4.flows.data.TravelFlight;

import java.util.HashMap;
import java.util.Map;

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
        filter.put("destination", flight.getDestination());
        if (flight.getDate() != null) {
            filter.put("date", flight.getDate());
        }
        map.put("event", "LIST");
        map.put("filter", filter);
        return gson.toJson(map);
    }
}

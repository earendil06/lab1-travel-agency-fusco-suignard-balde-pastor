package fr.unice.groupe4.flows.utils;

import com.google.gson.Gson;
import fr.unice.groupe4.flows.data.Flight;

import java.util.Map;

public class FlightHelper {
    public String buildGetFlightForTravel(Flight flight) {
        StringBuilder builder = new StringBuilder();
        Gson gson = new Gson();
        Map<String, Object> map = flight.toMap();
        map.put("event", "RETRIEVE");
        String object = gson.toJson(map);
        builder.append(object);
        return builder.toString();
    }
}

package fr.unice.groupe4.flows;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.Gson;
import fr.unice.groupe4.flows.data.Car;
import fr.unice.groupe4.flows.data.Flight;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TravelRequestSplitter {
    public static List split(Exchange exchange) {
        List list = new ArrayList();
        String uid = UUID.randomUUID().toString();

        Map map = exchange.getIn().getBody(Map.class);
        for (Object key : map.keySet()) {
            Object value = map.get(key);
            Message msg = new DefaultMessage();
            switch (key.toString()) {
                case "email":
                    msg.setBody(value);
                    msg.setHeader("type", "email");
                    break;
                case "flights":
                    List<Flight> flights = getFlights(value);
                    msg.setBody(flights);
                    msg.setHeader("type", "flights");
                    break;
                case "cars":
                    List<Car> cars = getCars(value);
                    msg.setBody(cars);
                    msg.setHeader("type", "cars");
                    break;
                default:
                    msg.setBody("def");
            }
            msg.setHeader("uid", uid);
            list.add(msg);
        }
        return list;
    }

    private static List<Flight> getFlights(Object value) {
        return ((List<Flight>) getData(value, Flight.class));
    }

    private static List<Car> getCars(Object value) {
        return ((List<Car>) getData(value, Car.class));
    }

    private static List getData(Object value, Class clazz) {
        Gson gson = new Gson();
        ObjectMapper mapper = new ObjectMapper();
        String jstring = gson.toJson(value);
        List data = new ArrayList();
        try {
            data = mapper.readValue(
                    jstring,
                    TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
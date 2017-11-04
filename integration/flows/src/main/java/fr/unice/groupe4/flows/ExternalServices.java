package fr.unice.groupe4.flows;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.unice.groupe4.flows.data.*;
import fr.unice.groupe4.flows.utils.CarHelper;
import fr.unice.groupe4.flows.utils.FlightHelper;
import fr.unice.groupe4.flows.utils.HotelHelper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import static fr.unice.groupe4.flows.utils.Endpoints.*;

public class ExternalServices extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from(COMPARE_HOTEL_ENDPOINT)
                .routeId("retrieve hotels from services")
                .routeDescription("retrieve hotels from services")
                .multicast(mergeHotels)//.parallelProcessing().executorService(Executors.newFixedThreadPool(2))
                .to(COMPARE_OUR_HOTEL, COMPARE_OTHER_HOTEL)
                .to(COMPARE_OUR_HOTEL)
                .end()
                .log("END COMPARE HOTELS")
        ;

        from(COMPARE_OUR_HOTEL)
                .bean(HotelHelper.class, "buildGetHotelForTravel(${body})")
                .inOut(HOTEL_ENDPOINT)
                .process(result2Hotel)
        ;

        from(COMPARE_OTHER_HOTEL)
                .bean(HotelHelper.class, "buildGetHotelForTravelOther(${body})")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
//                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .setProperty("request", simple("${body}"))
                .setBody(simple(""))
//                .inOut(OTHER_HOTEL_ENDPOINT + simple("${exchangeProperty[request]}"))
                .recipientList(simple(OTHER_HOTEL_ENDPOINT + "${exchangeProperty[request]}"))
                .removeProperty("request")
                .log("FROM OTHER HOTEL request")
                .process(result2OtherHotel)
        ;

        from(COMPARE_FLIGHT_ENDPOINT)
                .routeId("retrieve flights from services")
                .routeDescription("retrieve flights from services")
                .multicast(mergeFlights).parallelProcessing().executorService(Executors.newFixedThreadPool(2))
                .to(COMPARE_OUR_FLIGHT, COMPARE_OTHER_FLIGHT)
                .end()
                .log("END COMPARE FLIGHTS")
        ;

        from(COMPARE_OUR_FLIGHT)
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .bean(FlightHelper.class, "buildGetFlightForTravel(${body})")
                .inOut(FLIGHT_ENDPOINT)
                .process(result2Flight)
        ;

        from(COMPARE_OTHER_FLIGHT)
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .bean(FlightHelper.class, "buildGetFlightForTravelOther(${body})")
                .inOut(OTHER_FLIGHT_ENDPOINT)
                .process(result2OtherFlight)
        ;


        from(COMPARE_CAR_ENDPOINT)
                .routeId("retrieve cars from services")
                .routeDescription("retrieve cars from services")
                .bean(CarHelper.class, "buildGetCarForTravel(${body})")
                .inOut(CAR_ENDPOINT)
                .process(result2Car)
        ;
    }

    private static AggregationStrategy mergeFlights = (oldExchange, newExchange) -> {
        if (oldExchange == null) {
            return newExchange;
        } else {
            TravelFlight otherFlight = newExchange.getIn().getBody(TravelFlight.class);
            TravelFlight flight = oldExchange.getIn().getBody(TravelFlight.class);
            if (flight == null && otherFlight == null) {
                System.out.println("ALL FLIGHTS NULL");
                oldExchange.getIn().setBody(new TravelFlight());
                return oldExchange;
            }
            if (flight == null || otherFlight.getPrice() < flight.getPrice()) {
                oldExchange.getIn().setBody(otherFlight);
            } else {
                oldExchange.getIn().setBody(flight);
            }
        }
        return oldExchange;
    };

    private static AggregationStrategy mergeHotels = (oldExchange, newExchange) -> {
        if (oldExchange == null) {
            return newExchange;
        } else {
            TravelHotel otherHotel = newExchange.getIn().getBody(TravelHotel.class);
            TravelHotel hotel = oldExchange.getIn().getBody(TravelHotel.class);
            if (hotel == null && otherHotel == null) {
                System.out.println("ALL HOTELS NULL");
                oldExchange.getIn().setBody(new TravelHotel());
                return oldExchange;
            }
            if (hotel == null || otherHotel.getPrice() < hotel.getPrice()) {
                oldExchange.getIn().setBody(otherHotel);
            } else {
                oldExchange.getIn().setBody(hotel);
            }
        }
        return oldExchange;
    };

    private static Processor result2Hotel = (Exchange exc) -> {
        XPath xpath = XPathFactory.newInstance().newXPath();
        String response = exc.getIn().getBody(String.class);
        InputSource source = new InputSource(new StringReader(response));
        String jsonArray = xpath.evaluate("//hotel_planner_result", source);
        Type listType = new TypeToken<ArrayList<Hotel>>() {
        }.getType();
        List<Hotel> hotels;
        try {
            hotels = new Gson().fromJson(jsonArray, listType);
        } catch (com.google.gson.JsonSyntaxException e) {
            System.out.println("NO HOTEL");
            return;
        }
        if (hotels == null || hotels.isEmpty()) {
            System.out.println("NO HOTEL");
            return;
        }
        //get the min price
        Hotel hotelMin = hotels.stream().min(Comparator.comparingInt(Hotel::getPrice)).get();
        TravelHotel min = new TravelHotel(hotelMin);
        exc.getIn().setBody(min);
    };

    private static Processor result2OtherHotel = (Exchange exc) -> {
        String input = exc.getIn().getBody(String.class);
        Type mapType = new TypeToken<Map<String, List<OtherHotel>>>() {}.getType();
        Type hotelType = new TypeToken<List<OtherHotel>>() {}.getType();
        Map<String, List<OtherHotel>> map;
        List<OtherHotel> hotels;
        System.out.println("input " + input);
        try {
            map = new Gson().fromJson(input, mapType);
            if (map == null) {
                System.out.println("NO Other HOTELS");
                return;
            }
            System.out.println("map " + map);
            //hotels = new Gson().fromJson(map.get("hotels").toString().trim(), hotelType);
            hotels = map.get("hotels");
            System.out.println("hotels " + hotels);
        } catch (com.google.gson.JsonSyntaxException e) {
            System.out.println(e + " NO Other HOTELS");
            return;
        }
        if (hotels == null || hotels.isEmpty()) {
            System.out.println("NO Other HOTELS");
            return;
        }
        OtherHotel hotelMin = hotels.stream().min(Comparator.comparingDouble(OtherHotel::getAmount)).get();
        TravelHotel min = new TravelHotel(hotelMin);
        exc.getIn().setBody(min);
    };


    private static Processor result2Flight = (Exchange exc) -> {
        String jsonArray = exc.getIn().getBody(String.class);
        Type listType = new TypeToken<ArrayList<Flight>>() {
        }.getType();
        List<Flight> flights;
        try {
            flights = new Gson().fromJson(jsonArray, listType);
        } catch (com.google.gson.JsonSyntaxException e) {
            System.out.println( e + " NO FLIGHTS");
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

    private static Processor result2OtherFlight = (Exchange exc) -> {
        String input = exc.getIn().getBody(String.class);
        Type mapType = new TypeToken<Map<String, Object>>() {
        }.getType();
        Type flightType = new TypeToken<List<OtherFlight>>() {}.getType();
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
            System.out.println("NO Other FLIGHTS");
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

    private static Processor result2Car = (Exchange exc) -> {
        XPath xpath = XPathFactory.newInstance().newXPath();
        String response = exc.getIn().getBody(String.class);
        InputSource source = new InputSource(new StringReader(response));
        String jsonArray = xpath.evaluate("//car_planner_result", source);
        Type listType = new TypeToken<ArrayList<Car>>() {
        }.getType();
        List<Car> cars;
        try {
            cars = new Gson().fromJson(jsonArray, listType);
        } catch (com.google.gson.JsonSyntaxException e) {
            System.out.println("NO CARS");
            return;
        }
        if (cars == null || cars.isEmpty()) {
            System.out.println("NO CARS");
            return;
        }
        //get the min price
        Car min = cars.stream().min(Comparator.comparingInt(Car::getPrice)).get();
        exc.getIn().setBody(min);
    };
}

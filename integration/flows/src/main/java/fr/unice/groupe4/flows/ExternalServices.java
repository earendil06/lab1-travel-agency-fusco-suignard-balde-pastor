package fr.unice.groupe4.flows;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.unice.groupe4.flows.data.Car;
import fr.unice.groupe4.flows.data.Flight;
import fr.unice.groupe4.flows.data.Hotel;
import fr.unice.groupe4.flows.utils.CarHelper;
import fr.unice.groupe4.flows.utils.FlightHelper;
import fr.unice.groupe4.flows.utils.HotelHelper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static fr.unice.groupe4.flows.utils.Endpoints.*;

public class ExternalServices extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from(COMPARE_HOTEL_ENDPOINT)
                .routeId("retrieve hotels from services")
                .log("START HOTEL ENDPOINT")
                .bean(HotelHelper.class, "buildGetHotelForTravel(${body})")
                .inOut(HOTEL_ENDPOINT)
                .process(result2Hotel)
                .log("AFTER HOTEL SERVICE: " + body());

        from(COMPARE_FLIGHT_ENDPOINT)
                .routeId("retrieve flights from services")
                .log("START FLIGHT ENDPOINT")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .bean(FlightHelper.class, "buildGetFlightForTravel(${body})")

                .inOut(FLIGHT_ENDPOINT)
//                .log("AFTER FLIGHT SERVICE: " + body())
                .process(result2Flight)
                .log("AFTER FLIGHT SERVICE: " + body());

        from(COMPARE_CAR_ENDPOINT)
                .routeId("retrieve cars from services")
                .log("START CAR ENDPOINT")
                .bean(CarHelper.class, "buildGetCarForTravel(${body})")
                .inOut(CAR_ENDPOINT)
                .process(result2Car)
                .log("AFTER CAR SERVICE: " + body());
    }

    private static Processor result2Hotel = (Exchange exc) -> {
        XPath xpath = XPathFactory.newInstance().newXPath();
        String response = exc.getIn().getBody(String.class);
        InputSource source = new InputSource(new StringReader(response));
        String jsonArray = xpath.evaluate("//hotel_planner_result", source);
        Type listType = new TypeToken<ArrayList<Hotel>>(){}.getType();
        List<Hotel> hotels = new Gson().fromJson(jsonArray, listType);
        //todo if no response go to end route
        //get the min price
        Hotel min = hotels.stream().min(Comparator.comparingInt(Hotel::getPrice)).get();
//        System.out.println("min: " + min);
        exc.getIn().setBody(min);
    };

    private static Processor result2Flight = (Exchange exc) -> {
        String jsonArray = exc.getIn().getBody(String.class);
        Type listType = new TypeToken<ArrayList<Flight>>(){}.getType();
        List<Flight> flights = new Gson().fromJson(jsonArray, listType);

        Flight min = flights.stream().min(Comparator.comparingDouble(Flight::getPrice)).get();
//        System.out.println("min: " + min);
        exc.getIn().setBody(min);
    };

    private static Processor result2Car = (Exchange exc) -> {
        XPath xpath = XPathFactory.newInstance().newXPath();
        String response = exc.getIn().getBody(String.class);
        InputSource source = new InputSource(new StringReader(response));
        String jsonArray = xpath.evaluate("//car_planner_result", source);
        Type listType = new TypeToken<ArrayList<Car>>(){}.getType();
        List<Car> cars = new Gson().fromJson(jsonArray, listType);
        //todo if no response go to end route
        //get the min price
        //Car min = cars.stream().min(Comparator.comparingInt(Car::getPrice)).get();
        //System.out.println("min: " + min);
        //exc.getIn().setBody(min);
        exc.getIn().setBody(cars.get(0));
    };
}

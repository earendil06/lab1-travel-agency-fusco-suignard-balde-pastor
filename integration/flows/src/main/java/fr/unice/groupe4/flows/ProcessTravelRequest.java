package fr.unice.groupe4.flows;

import com.google.gson.Gson;
import fr.unice.groupe4.flows.data.Car;
import fr.unice.groupe4.flows.data.Flight;
import fr.unice.groupe4.flows.data.Hotel;
import fr.unice.groupe4.flows.data.TravelRequest;
import fr.unice.groupe4.flows.utils.Endpoints;
import fr.unice.groupe4.flows.utils.TravelRequestSplitter;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fr.unice.groupe4.flows.utils.Endpoints.*;

public class ProcessTravelRequest extends RouteBuilder {
    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(3);

    @Override
    public void configure() throws Exception {
        from(Endpoints.TEST_INPUT)
                .log("Starting submit travel")
                .routeId("submitTravel")
                .log("input is " + body())
                .unmarshal().json(JsonLibrary.Jackson, Map.class)
                .split().method(TravelRequestSplitter.class, "split")
                .parallelProcessing().executorService(WORKERS)
                .choice()

                .when(header("type").isEqualTo("email"))
                .log("email is " + body())

                .when(header("type").isEqualTo("flight"))
                .log("flight is " + body())
                .process(ProcessTravelRequest::json2flight)
                .log("UNMARSHAL FLIGHT " + body())
                .inOut(COMPARE_FLIGHT_ENDPOINT)

                .when(header("type").isEqualTo("car"))
                .log("car is " + body())
                .process(ProcessTravelRequest::json2car)
                .log("UNMARSHAL CAR " + body())
                .inOut(COMPARE_CAR_ENDPOINT)

                .when(header("type").isEqualTo("hotel"))
                .log("hotel is " + body())
                .process(ProcessTravelRequest::json2hotel)
                .log("UNMARSHAL HOTEL " + body())
                .inOut(COMPARE_HOTEL_ENDPOINT)

                .end()
                .aggregate(constant(true), merge)
                .completionPredicate(exchange -> {
                    TravelRequest travelRequest = exchange.getIn().getBody(TravelRequest.class);
                    return travelRequest != null &&
                            travelRequest.getEmail() != null &&
                            travelRequest.getCar() != null &&
                            travelRequest.getHotel() != null &&
                            travelRequest.getFlight() != null;
                })
                .log("after aggregation " + body())

        //.to(HANDLE_A_TRAVEL_SUBMIT)
        ;

        from(HANDLE_A_TRAVEL_SUBMIT)
                .log("aggregation " + body())
                .aggregate(merge)
                .body()
                .completionPredicate(exchange -> {
                    TravelRequest travelRequest = exchange.getIn().getBody(TravelRequest.class);
                    //System.out.println("predicate " + travelRequest);
                    return travelRequest != null && travelRequest.getEmail() != null &&
                            travelRequest.getEmail().contains("azeazeaze");
                })
                .log("after aggregation " + body())
        ;

    }

    private static void json2car(Exchange exchange) {
        Gson gson = new Gson();
        String s = exchange.getIn().getBody(String.class);
        Car car = gson.fromJson(s, Car.class);
        exchange.getIn().setBody(car);
    }

    private static void json2flight(Exchange exchange) {
        Gson gson = new Gson();
        String s = exchange.getIn().getBody(String.class);
        Flight flight = gson.fromJson(s, Flight.class);
        exchange.getIn().setBody(flight);
    }

    private static void json2hotel(Exchange exchange) {
        Gson gson = new Gson();
        String s = exchange.getIn().getBody(String.class);
        Hotel hotel = gson.fromJson(s, Hotel.class);
        exchange.getIn().setBody(hotel);
    }

    private static AggregationStrategy merge = new AggregationStrategy() {
        @Override
        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
            Message newIn = newExchange.getIn();
            if (oldExchange == null) {
                TravelRequest travelRequest = new TravelRequest();
                add(travelRequest, newIn);
                newIn.setBody(travelRequest);
                return newExchange;
            } else {
                TravelRequest old = oldExchange.getIn().getBody(TravelRequest.class);
                add(old, newIn);
                oldExchange.getIn().setBody(old);
                //System.out.println("MERGED " + old);
                if (old != null &&
                        old.getEmail() != null &&
                        old.getCar() != null &&
                        old.getHotel() != null &&
                        old.getFlight() != null) {
                    System.out.println("EVERYTHING IS AGGREGATED\n" + old);
                }
                return oldExchange;
            }
        }

        private void add(TravelRequest old, Object o) {
            Message msg = ((Message) o);
            Gson gson = new Gson();
            switch (msg.getHeader("type").toString()) {
                case "email":
                    String email = msg.getBody(String.class);
                    old.setEmail(email);
                    break;
                case "flight":
                    Flight flight = msg.getBody(Flight.class);
                    old.setFlight(flight);
                    break;
                case "car":
                    Car car = msg.getBody(Car.class);
                    old.setCar(car);
                    break;
                case "hotel":
                    Hotel hotel = msg.getBody(Hotel.class);
                    old.setHotel(hotel);
                    break;
            }
        }
    };
}

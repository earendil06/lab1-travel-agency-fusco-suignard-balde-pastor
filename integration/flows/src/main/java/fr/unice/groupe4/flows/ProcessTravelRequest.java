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
    private static final String EMAIL = "email";
    private static final String FLIGHT = "flight";
    private static final String CAR = "car";
    private static final String HOTEL = "hotel";
    private static final String TYPE = "type";

    @Override
    public void configure() throws Exception {
        from(Endpoints.TRAVEL_REQUEST_INPUT)
                .routeId("submitTravel")
                .log("input is " + body())
                .unmarshal().json(JsonLibrary.Jackson, Map.class)
                .split().method(TravelRequestSplitter.class, "split")
//                    .parallelProcessing().executorService(WORKERS)
                    .choice()
                        .when(header(TYPE).isEqualTo(EMAIL))
                            .log("email is " + body())

                        .when(header(TYPE).isEqualTo(FLIGHT))
                            .inOut(HANDLE_FLIGHT_ENDPOINT)

                        .when(header(TYPE).isEqualTo(CAR))
                            .inOut(HANDLE_CAR_ENDPOINT)

                        .when(header(TYPE).isEqualTo(HOTEL))
                            .inOut(HANDLE_HOTEL_ENDPOINT)

                        .otherwise()
                            .to(DEATH_POOL)
                    .end()
                    .aggregate(constant(true), merge)
                    .completionPredicate(ProcessTravelRequest::matches)
                    .log("after aggregation " + body())
                .marshal().json(JsonLibrary.Jackson, TravelRequest.class)
        .to(RESULT_POOL)
        ;

        from(HANDLE_FLIGHT_ENDPOINT)
                .routeId("handle flight requests")
                .process(ProcessTravelRequest::json2flight)
                .inOut(COMPARE_FLIGHT_ENDPOINT);

        from(HANDLE_CAR_ENDPOINT)
                .routeId("handle car requests")
                .process(ProcessTravelRequest::json2car)
                .inOut(COMPARE_CAR_ENDPOINT);

        from(HANDLE_HOTEL_ENDPOINT)
                .routeId("handle hotel requests")
                .process(ProcessTravelRequest::json2hotel)
                .inOut(COMPARE_HOTEL_ENDPOINT);

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
            switch (msg.getHeader(TYPE).toString()) {
                case EMAIL:
                    String email = msg.getBody(String.class);
                    old.setEmail(email);
                    break;
                case FLIGHT:
                    Flight flight = msg.getBody(Flight.class);
                    old.setFlight(flight);
                    break;
                case CAR:
                    Car car = msg.getBody(Car.class);
                    old.setCar(car);
                    break;
                case HOTEL:
                    Hotel hotel = msg.getBody(Hotel.class);
                    old.setHotel(hotel);
                    break;
            }
        }
    };

    private static boolean matches(Exchange exchange) {
        TravelRequest travelRequest = exchange.getIn().getBody(TravelRequest.class);
        return travelRequest != null &&
                travelRequest.getEmail() != null &&
                travelRequest.getCar() != null &&
                travelRequest.getHotel() != null &&
                travelRequest.getFlight() != null;
    }
}

package fr.unice.groupe4.flows;

import com.fasterxml.jackson.core.JsonParseException;
import com.google.gson.Gson;
import fr.unice.groupe4.flows.data.traveldata.TravelCar;
import fr.unice.groupe4.flows.data.traveldata.TravelFlight;
import fr.unice.groupe4.flows.data.traveldata.TravelHotel;
import fr.unice.groupe4.flows.data.traveldata.TravelRequest;
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
    public static final String EMAIL = "email";
    public static final String FLIGHT = "flight";
    public static final String CAR = "car";
    public static final String HOTEL = "hotel";
    public static final String TYPE = "type";
    private static final String NUMBER_OF_DATA = "numberOfData";

    @Override
    public void configure() throws Exception {
        from(Endpoints.TRAVEL_REQUEST_INPUT)
                .routeId("submitTravel")
                .routeDescription("Handle a travel request from an employee")
                .log("input is ${body}")
                .doTry()
                    .unmarshal().json(JsonLibrary.Jackson, Map.class)
                    .process(exchange ->
                        exchange.setProperty(NUMBER_OF_DATA, exchange.getIn().getBody(Map.class).size()))
                    .log("numberOfData ${exchangeProperty[" + NUMBER_OF_DATA + "]}")
                .doCatch(JsonParseException.class)
                    .log("Exception in INPUT")
                    .setProperty("isFailed", simple("true"))
                    .setBody(simple("FAILED MESSAGE"))
                .end()
                .choice()
                    .when(simple("${property[isFailed]} == 'true'"))
                        .log("STOP HERE")
                        .removeProperty("isFailed")
                        .to(BAD_INPUT_QUEUE)
                    .otherwise()
                        .to(HANDLE_REQUEST)
                .end()
        ;

        from(HANDLE_REQUEST)
                .split().method(TravelRequestSplitter.class, "split")
                .parallelProcessing().executorService(WORKERS)
                .choice()
                    .when(header(TYPE).isEqualTo(EMAIL))
                        .log("email is ${body}")

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
                .completionSize(simple("${exchangeProperty[" + NUMBER_OF_DATA + "]}"))
                .log("after aggregation ${body}")
                .marshal().json(JsonLibrary.Jackson, TravelRequest.class)
                .log("TO RESULT")
                .to(RESULT_POOL)
        ;

        from(HANDLE_FLIGHT_ENDPOINT)
                .routeId("handle flight requests")
                .routeDescription("handle flight requests")
                .process(ProcessTravelRequest::json2flight)
                .inOut(COMPARE_FLIGHT_ENDPOINT);

        from(HANDLE_CAR_ENDPOINT)
                .routeId("handle car requests")
                .routeDescription("handle car requests")
                .process(ProcessTravelRequest::json2car)
                .inOut(COMPARE_CAR_ENDPOINT);

        from(HANDLE_HOTEL_ENDPOINT)
                .routeId("handle hotel requests")
                .routeDescription("handle hotel requests")
                .process(ProcessTravelRequest::json2hotel)
                .inOut(COMPARE_HOTEL_ENDPOINT);

        from(BAD_INPUT_QUEUE)
                .log("received bad input ${body}");

    }
    private static void json2car(Exchange exchange) {
        Gson gson = new Gson();
        String s = exchange.getIn().getBody(String.class);
        TravelCar car = gson.fromJson(s, TravelCar.class);
        exchange.getIn().setBody(car);
    }

    private static void json2flight(Exchange exchange) {
        Gson gson = new Gson();
        String s = exchange.getIn().getBody(String.class);
        TravelFlight flight = gson.fromJson(s, TravelFlight.class);
        exchange.getIn().setBody(flight);
    }

    private static void json2hotel(Exchange exchange) {
        Gson gson = new Gson();
        String s = exchange.getIn().getBody(String.class);
        TravelHotel hotel = gson.fromJson(s, TravelHotel.class);
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
                    TravelFlight flight = msg.getBody(TravelFlight.class);
                    old.setFlight(flight);
                    break;
                case CAR:
                    TravelCar car = msg.getBody(TravelCar.class);
                    old.setCar(car);
                    break;
                case HOTEL:
                    TravelHotel hotel = msg.getBody(TravelHotel.class);
                    old.setHotel(hotel);
                    break;
            }
        }
    };
}

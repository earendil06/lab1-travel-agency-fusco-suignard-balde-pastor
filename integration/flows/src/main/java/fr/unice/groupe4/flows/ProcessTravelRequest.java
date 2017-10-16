package fr.unice.groupe4.flows;

import fr.unice.groupe4.flows.data.Car;
import fr.unice.groupe4.flows.data.Flight;
import fr.unice.groupe4.flows.data.TravelRequest;
import fr.unice.groupe4.flows.utils.Endpoints;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fr.unice.groupe4.flows.utils.Endpoints.HANDLE_A_TRAVEL_SUBMIT;

public class ProcessTravelRequest extends RouteBuilder {
    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(5);

    @Override
    public void configure() throws Exception {
        from(Endpoints.TEST_INPUT)
                .log("Starting submit travel")
                .routeId("submitTravel")
                .log("input is " + body())
                .unmarshal().json(JsonLibrary.Gson, Map.class)
                .log("to map " + body())
                .split().method(TravelRequestSplitter.class, "split")
//                .parallelProcessing().executorService(WORKERS)
                    .log("splitted msg ${body}")
                    .choice()
                        .when(header("type").isEqualTo("email"))
                            .log("email is " + body())
                            //.to(HANDLE_A_TRAVEL_SUBMIT)
                        .when(header("type").isEqualTo("flights"))
                            .log("flights are " + body())
                            //.to("direct:flights")
                        .when(header("type").isEqualTo("cars"))
                            .log("cars are " + body())
                            //.to("direct:cars")
                    .end()
                .aggregate(constant(true), merge)
                .completionPredicate(exchange -> {
                    TravelRequest travelRequest = exchange.getIn().getBody(TravelRequest.class);
                    System.out.println("predicate " + travelRequest);
                    return travelRequest != null && travelRequest.getEmail() != null &&
                            travelRequest.getEmail().contains("toto@toto.com");
                })
                .log("after aggregation " + body())

                    //.to(HANDLE_A_TRAVEL_SUBMIT)
        ;

        from("direct:flights")
                .log("FLIGHTS PROCESSING " + body())
                //.to(HANDLE_A_TRAVEL_SUBMIT)
        ;

        from("direct:cars")
                .log("CARS PROCESSING " + body())
                //.to(HANDLE_A_TRAVEL_SUBMIT)
        ;

        from(HANDLE_A_TRAVEL_SUBMIT)
                .log("aggregation " + body())
                .aggregate(merge)
                .body()
                .completionPredicate(exchange -> {
                    TravelRequest travelRequest = exchange.getIn().getBody(TravelRequest.class);
                    System.out.println("predicate " + travelRequest);
                    return travelRequest != null && travelRequest.getEmail() != null &&
                            travelRequest.getEmail().contains("azeazeaze");
                })
                .log("after aggregation " + body())
        ;

    }

    private static AggregationStrategy merge = new AggregationStrategy() {
        @Override
        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
            System.out.println();
            //System.out.println("oldExchange " + oldExchange);
            //System.out.println("newExchange " + newExchange);
            Message newIn = newExchange.getIn();
            Object newBody = newIn.getBody();
            ArrayList list = null;
            if (oldExchange == null) {
//                list = new ArrayList();
                TravelRequest travelRequest = new TravelRequest();
                //list.add(newBody);
                add(travelRequest, newIn);
//                newIn.setBody(list);
                newIn.setBody(travelRequest);
                return newExchange;
            } else {
                TravelRequest old = oldExchange.getIn(TravelRequest.class);
                if (old == null) {
                    System.out.println("IS NULL");
                    return oldExchange;
                }
                add(old, newIn);
//                list = in.getBody(ArrayList.class);
//                list.add(newBody);
                System.out.println("MERGED " + old);
                return oldExchange;
            }
        }

        private void add(TravelRequest old, Message msg) {
            if (msg.getBody() == null) {
                return;
            }
            switch (msg.getHeader("type").toString()) {
                case "email":
                    String email = msg.getBody(String.class);
                    old.setEmail(email);
                    System.out.println("email " + email);
                    break;
                case "flights":
                    List<Flight> flights = msg.getBody(List.class);
                    System.out.println("flights " + flights);
                    old.setFlights(flights);
                    break;
                case "cars":
                    List<Car> cars = msg.getBody(List.class);
                    System.out.println("cars " + cars);
                    old.setCars(cars);
                    break;
            }
        }
    };

}

package fr.unice.groupe4.flows;

import com.google.gson.Gson;
import fr.unice.groupe4.flows.data.Car;
import fr.unice.groupe4.flows.data.Flight;
import fr.unice.groupe4.flows.data.TravelRequest;
import fr.unice.groupe4.flows.utils.Endpoints;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fr.unice.groupe4.flows.utils.Endpoints.HANDLE_A_TRAVEL_SUBMIT;

public class ProcessTravelRequest extends RouteBuilder {
    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(3);

    @Override
    public void configure() throws Exception {
        from(Endpoints.TEST_INPUT)
                .log("Starting submit travel")
                .routeId("submitTravel")
                .log("input is " + body())
                .unmarshal().json(JsonLibrary.Gson, Map.class)
                .log("to map " + body())
                .split().method(TravelRequestSplitter.class, "split")
                //.parallelProcessing().executorService(WORKERS)
                    .log("split msg ${body}")
                    .choice()
                        .when(header("type").isEqualTo("email"))
                            .log("email is " + body())
                            //.to(HANDLE_A_TRAVEL_SUBMIT)
                        .when(header("type").isEqualTo("flight"))
                            .log("flight is " + body())
                            //.to("direct:flights")
                        .when(header("type").isEqualTo("car"))
                            .log("car is " + body())
                            //.to("direct:cars")
                    .end()
                .aggregate(constant(true), merge)
                .completionPredicate(exchange -> {
                    TravelRequest travelRequest = exchange.getIn().getBody(TravelRequest.class);
                    //System.out.println("predicate " + travelRequest);
                    return travelRequest != null && travelRequest.getEmail() != null &&
                            travelRequest.getEmail().contains("aaaaaaaaaaa"/*"toto@toto.com"*/);
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
                    //System.out.println("predicate " + travelRequest);
                    return travelRequest != null && travelRequest.getEmail() != null &&
                            travelRequest.getEmail().contains("azeazeaze");
                })
                .log("after aggregation " + body())
        ;

    }

    private static AggregationStrategy merge = new AggregationStrategy() {
        @Override
        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
            Message newIn = newExchange.getIn();
            if (oldExchange == null) {
//                list = new ArrayList();
                TravelRequest travelRequest = new TravelRequest();
                //list.add(newBody);
                add(travelRequest, newIn);
//                newIn.setBody(list);
                newIn.setBody(travelRequest);
                //newExchange.setIn(newIn);
                //list.add(newIn.getBody());
                return newExchange;
            } else {
                TravelRequest old = oldExchange.getIn().getBody(TravelRequest.class);
                /*if (old == null) {
                    System.out.println("IS NULL");
                    //return oldExchange;
                    old = new TravelRequest();
                    add(old, oldExchange.getIn());
                }*/
                add(old, newIn);
//                list = in.getBody(ArrayList.class);
//                list.add(newBody);
                oldExchange.getIn().setBody(old);
                System.out.println("MERGED " + old);
                return oldExchange;

                /*Object obj = oldExchange.getIn().getBody();
                System.out.println("FROM OLD " + obj);
                list.add(obj);
                list.add(newIn.getBody());
                TravelRequest request = new TravelRequest();
                if (list.size() == 3) {
                    for (Object o : list) {
                        add(request, o);
                    }
                } else {
                    oldExchange.getIn().setBody(list);
                    return oldExchange;
                }
                System.out.println("FINAL " + request);
                return oldExchange;*/
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
                    Flight flight = gson.fromJson(msg.getBody(String.class), Flight.class);
                    old.setFlight(flight);
                    break;
                case "car":
                    Car car = gson.fromJson(msg.getBody(String.class), Car.class);
                    old.setCar(car);
                    break;
            }
            /*if (msg.getBody() == null) {
                System.out.println("NULL IN ADD");
                return;
            }*/
            /*switch (msg.getHeader("type").toString()) {
                case "email":
                    String email = msg.getBody(String.class);
                    old.setEmail(email);
                    System.out.println("email " + email + "\ntravel " + old);
                    break;
                case "flights":
                    List<Flight> flights = msg.getBody(List.class);
                    old.setFlights(flights);
                    System.out.println("flights " + flights + "\ntravel " + old);
                    break;
                case "cars":
                    List<Car> cars = msg.getBody(List.class);
                    old.setCars(cars);
                    System.out.println("cars " + cars + "\ntravel " + old);
                    break;
            }*/
        }
    };

}

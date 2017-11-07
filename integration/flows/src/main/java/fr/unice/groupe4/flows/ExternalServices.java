package fr.unice.groupe4.flows;

import fr.unice.groupe4.flows.data.traveldata.TravelCar;
import fr.unice.groupe4.flows.utils.CarHelper;
import fr.unice.groupe4.flows.utils.FlightHelper;
import fr.unice.groupe4.flows.utils.HotelHelper;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import java.io.IOException;
import java.util.concurrent.Executors;

import static fr.unice.groupe4.flows.utils.Endpoints.*;

public class ExternalServices extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        onException(IOException.class)
                .log("EXCEPTION")
                .maximumRedeliveries(2)
//                .redeliveryDelay(5000)
                .redeliveryDelay(500)
                .process(exchange -> exchange.getIn().setBody(null))
                .continued(true);

        from(COMPARE_HOTEL_ENDPOINT)
                .routeId("retrieve hotels from services")
                .routeDescription("retrieve hotels from services")
                .multicast(HotelHelper.mergeHotels)
                .parallelProcessing().executorService(Executors.newFixedThreadPool(2))
                .to(COMPARE_OUR_HOTEL, COMPARE_OTHER_HOTEL)
                .end()
        ;

        from(COMPARE_OUR_HOTEL)
                .bean(HotelHelper.class, "buildGetHotelForTravel(${body})")
                .inOut(HOTEL_ENDPOINT.replace("http:", "http://"))
                .process(HotelHelper.result2Hotel)
        ;

        from(COMPARE_OTHER_HOTEL)
                .bean(HotelHelper.class, "buildGetHotelForTravelOther(${body})")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setHeader("Accept", constant("application/json"))
                .setProperty("request", simple("${body}"))
                .setBody(simple(""))
                .recipientList(simple(OTHER_HOTEL_ENDPOINT.replace("http:", "http://")
                        + "${exchangeProperty[request]}"))
                .end()
                .removeProperty("request")
                .process(HotelHelper.result2OtherHotel)
        ;

        from(COMPARE_FLIGHT_ENDPOINT)
                .routeId("retrieve flights from services")
                .routeDescription("retrieve flights from services")
                .multicast(FlightHelper.mergeFlights)
                .parallelProcessing().executorService(Executors.newFixedThreadPool(2))
                .to(COMPARE_OUR_FLIGHT, COMPARE_OTHER_FLIGHT)
                .end()
        ;

        from(COMPARE_OUR_FLIGHT)
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .bean(FlightHelper.class, "buildGetFlightForTravel(${body})")
                .inOut(FLIGHT_ENDPOINT.replace("http:", "http://"))
                .process(FlightHelper.result2Flight)
        ;

        from(COMPARE_OTHER_FLIGHT)
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .bean(FlightHelper.class, "buildGetFlightForTravelOther(${body})")
                .inOut(OTHER_FLIGHT_ENDPOINT.replace("http:", "http://"))
                .process(FlightHelper.result2OtherFlight)

        ;

        from(COMPARE_CAR_ENDPOINT)
                .routeId("retrieve cars from services")
                .routeDescription("retrieve cars from services")
                .multicast(CarHelper.mergeCars)
                .parallelProcessing().executorService(Executors.newFixedThreadPool(2))
                .to(COMPARE_OUR_CAR, COMPARE_OTHER_CAR)
                .end()
        ;

        from(COMPARE_OUR_CAR)
                .bean(CarHelper.class, "buildGetCarForTravel(${body})")
                .inOut(CAR_ENDPOINT.replace("http:", "http://"))
                .process(CarHelper.result2Car)
        ;

        from(COMPARE_OTHER_CAR)
                .process(exchange ->
                        exchange.setProperty("duration", exchange.getIn().getBody(TravelCar.class).getDuration()))
                .bean(CarHelper.class, "buildGetCarForTravelOther(${body})")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setHeader("Accept", constant("application/json"))
                .setProperty("request", simple("${body}"))
                .setBody(simple(""))
                .recipientList(simple(OTHER_CAR_ENDPOINT.replace("http:", "http://")
                        + "${exchangeProperty[request]}"))
                .end()
                .removeProperty("request")
                .process(CarHelper.result2OtherCar)
                .removeProperty("duration")
        ;
    }
}

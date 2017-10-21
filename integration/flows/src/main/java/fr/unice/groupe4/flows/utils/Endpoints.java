package fr.unice.groupe4.flows.utils;

public class Endpoints {
    //    public static final String TEST_INPUT    = "file:camel/input";
    public static final String TEST_INPUT = "file:camel/input";
    public static final String HANDLE_A_TRAVEL_SUBMIT = "activemq:handleTravelSubmit";

    public static final String COMPARE_HOTEL_ENDPOINT = "direct:compare-hotel";
    public static final String HOTEL_ENDPOINT = "http://localhost:9090/service-hotel-planner/HotelPlannerService";

    public static final String COMPARE_FLIGHT_ENDPOINT = "direct:compare-flight";
    public static final String FLIGHT_ENDPOINT = "http://localhost:9080/tcs-service-doc/flights";

    public static final String COMPARE_CAR_ENDPOINT = "direct:compare-car";
    public static final String CAR_ENDPOINT = "http://localhost:9060/service-car-planner/CarPlannerService";
}

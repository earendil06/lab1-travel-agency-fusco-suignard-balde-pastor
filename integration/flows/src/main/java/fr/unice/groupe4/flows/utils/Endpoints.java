package fr.unice.groupe4.flows.utils;

public class Endpoints {

    public static final String HANDLE_A_TRAVEL_SUBMIT = "activemq:handleTravelSubmit";

    public static final String TEST_INPUT = "file:camel/input";

    public static final String MAIL_INPUT = "file:camel/mail";


    public static final String EXPENSE_TO_REFUND = "activemq:refund-report";
    public static final String EXPENSE_NOT_REFUND = "activemq:not-refund";
    public static final String TRAVEL_REQUEST_INPUT = "file:camel/input";

    public static final String HANDLE_HOTEL_ENDPOINT = "direct:handle-hotel";
    public static final String HANDLE_CAR_ENDPOINT = "direct:handle-car";
    public static final String HANDLE_FLIGHT_ENDPOINT = "direct:handle-flight";

    public static final String COMPARE_HOTEL_ENDPOINT = "activemq:compare-hotel";
    //    public static final String HOTEL_ENDPOINT = "http:hotel-planner:8080/service-hotel-planner/HotelPlannerService";
    public static final String HOTEL_ENDPOINT = "http://localhost:9090/service-hotel-planner/HotelPlannerService";

    public static final String COMPARE_FLIGHT_ENDPOINT = "activemq:compare-flight";
    //    public static final String FLIGHT_ENDPOINT = "http:tcs-flight:8080/tcs-service-doc/flights";
    public static final String FLIGHT_ENDPOINT = "http://localhost:9080/tcs-service-doc/flights";

    //    public static final String OTHER_FLIGHT_ENDPOINT = "http:other-flights:8080/flightreservation-service-document/registry";
    public static final String OTHER_FLIGHT_ENDPOINT = "http://localhost:9180/flightreservation-service-document/registry";
    //    public static final String OTHER_HOTEL_ENDPOINT = "http:other-hotels:8080/tta-car-and-hotel/hotels/city/";
    public static final String OTHER_HOTEL_ENDPOINT = "http://localhost:9380/tta-car-and-hotel/hotels";
    public static final String OTHER_CAR_ENDPOINT = "http://localhost:9280/cars";

    public static final String COMPARE_CAR_ENDPOINT = "activemq:compare-car";
    //    public static final String CAR_ENDPOINT = "http:car-planner:8080/service-car-planner/CarPlannerService";
    public static final String CAR_ENDPOINT = "http://localhost:9060/service-car-planner/CarPlannerService";

    public static final String DEATH_POOL = "activemq:global:dead";

    public static final String RESULT_POOL = "activemq:result";

    public static final String COMPARE_OUR_FLIGHT = "direct:ourFlight";
    public static final String COMPARE_OTHER_FLIGHT = "direct:otherFlight";
    public static final String COMPARE_OUR_HOTEL = "direct:ourHotel";
    public static final String COMPARE_OTHER_HOTEL = "direct:otherHotel";

}

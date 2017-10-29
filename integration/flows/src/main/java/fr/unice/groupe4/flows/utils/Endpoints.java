package fr.unice.groupe4.flows.utils;

public class Endpoints {

    public static final String HANDLE_A_TRAVEL_SUBMIT = "activemq:handleTravelSubmit";

    public static final String TEST_INPUT = "file:camel/input";

    public static final String MAIL_INPUT = "file:camel/mail";
    public static final String MAIL_REPORT = "file:camel/report";
    public static final String MAIL_ANALYSIS = "file:camel/analysis";
    public static final String MESSAGE_GENERATION = "activemq:message-generation";

    public static final String TRAVEL_REQUEST_INPUT = "file:camel/input";

    public static final String HANDLE_HOTEL_ENDPOINT = "direct:handle-hotel";
    public static final String HANDLE_CAR_ENDPOINT = "direct:handle-car";
    public static final String HANDLE_FLIGHT_ENDPOINT = "direct:handle-flight";

    public static final String COMPARE_HOTEL_ENDPOINT = "activemq:compare-hotel";
//    public static final String HOTEL_ENDPOINT = "http:hotel-planner:8080/service-hotel-planner/HotelPlannerService";//use when in docker
    public static final String HOTEL_ENDPOINT = "http://localhost:9090/service-hotel-planner/HotelPlannerService";

    public static final String COMPARE_FLIGHT_ENDPOINT = "activemq:compare-flight";
//    public static final String FLIGHT_ENDPOINT = "http:flight-planner:8080/tcs-service-doc/flights";//use when in docker
    public static final String FLIGHT_ENDPOINT = "http://localhost:9080/tcs-service-doc/flights";

    public static final String COMPARE_CAR_ENDPOINT = "activemq:compare-car";
//    public static final String CAR_ENDPOINT = "http:car-planner:8080/service-car-planner/CarPlannerService";//use when in docker
    public static final String CAR_ENDPOINT = "http://localhost:9060/service-car-planner/CarPlannerService";

    public static final String DEATH_POOL = "activemq:global:dead";

    public static final String RESULT_POOL = "activemq:result";

}

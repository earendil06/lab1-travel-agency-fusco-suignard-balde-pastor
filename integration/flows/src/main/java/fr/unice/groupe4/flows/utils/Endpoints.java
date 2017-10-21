package fr.unice.groupe4.flows.utils;

public class Endpoints {
    //    public static final String TEST_INPUT    = "file:camel/input";

    public static final String TEST_INPUT = "file:camel/input";
    public static final String MAIL_INPUT = "file:camel/mail";
    public static final String MAIL_ANALYSIS = "file:camel/analysis";


    public static final String HANDLE_A_TRAVEL_SUBMIT = "activemq:handleTravelSubmit";

    public static final String COMPARE_HOTEL_ENDPOINT = "direct:compare-hotel";
    public static final String HOTEL_ENDPOINT = "http://localhost:9090/service-hotel-planner/HotelPlannerService";
}

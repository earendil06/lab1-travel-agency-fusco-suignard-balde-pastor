package fr.unice.groupe4.flows;

import fr.unice.groupe4.flows.data.traveldata.TravelCar;
import fr.unice.groupe4.flows.data.traveldata.TravelFlight;
import fr.unice.groupe4.flows.data.traveldata.TravelHotel;
import fr.unice.groupe4.flows.data.traveldata.TravelRequest;
import fr.unice.groupe4.flows.utils.Endpoints;
import org.apache.activemq.broker.BrokerService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public abstract class ActiveMQTest extends CamelTestSupport {

    public static double CHEAPEST_FLIGHT_PRICE = 42;
    public static double CHEAPEST_CAR_PRICE = 10;
    public static double CHEAPEST_HOTEL_PRICE = 299;

    public static double HIGHEST_FLIGHT_PRICE = 100;
    public static double HIGHEST_CAR_PRICE = 200;
    public static double HIGHEST_HOTEL_PRICE = 300;

    /**
     * Handling ActiveMQ
     */
    private static BrokerService brokerSvc;

    @BeforeClass
    public static void setUpClass() throws Exception {
        brokerSvc = new BrokerService();
        brokerSvc.setBrokerName("TestBroker");
        brokerSvc.addConnector("tcp://localhost:61616");
        brokerSvc.start();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        brokerSvc.stop();
    }


    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        DeathPool deathPool = new DeathPool();
//        IOErrorHandler ioErrorHandler = new IOErrorHandler();
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
//                this.includeRoutes(ioErrorHandler);
                this.includeRoutes(deathPool);
                this.setErrorHandlerBuilder(deathPool.getErrorHandlerBuilder());
                this.includeRoutes(new ProcessTravelRequest());
                this.includeRoutes(new ExternalServices());
                this.includeRoutes(new SendToManager());
                this.includeRoutes(new SupportingRouter());
            }
        };
    }


    /**
     * Handling Mocks endpoints automatically
     */
    private static Map<String, String> mocks = new HashMap<>();

    @BeforeClass
    public static void loadEndpointsAsMocks() throws Exception {
        // Automatically loads all the constants defined in the Endpoints class
        Field[] fields = Endpoints.class.getDeclaredFields();
        for (Field f : fields) {
            if (Modifier.isStatic(f.getModifiers()) && f.getType().equals(String.class))
                mocks.put("" + f.get(""), "mock://" + f.get(""));
        }
    }

    protected void isAvailableAndMocked(String name) {
        assertNotNull(context.hasEndpoint(name));
        assertNotNull(context.hasEndpoint(mocks.get(name)));
    }

    protected MockEndpoint mock(String name) {
        return getMockEndpoint(mocks.get(name));
    }

    TravelRequest travelRequest;
    @Before
    public void buildNewRequest() {
        travelRequest = new TravelRequest();
        TravelFlight flight = new TravelFlight();
        flight.setDestination("Menton");
//        flight.setTo("Menton");
        TravelCar car = new TravelCar();
        car.setPlace("Menton");
        car.setDuration(10);
        TravelHotel hotel = new TravelHotel();
        hotel.setPlace("Menton");
        hotel.setDateArrival("10.10.1010");
        hotel.setDateDeparture("11.11.1111");
        travelRequest.setEmail("tata@toto.com");
        travelRequest.setFlight(flight);
        travelRequest.setCar(car);
        travelRequest.setHotel(hotel);
    }

    TravelRequest finalRequest;
    @Before
    public void buildFinalRequest() {
        TravelHotel hotel = new TravelHotel();
        hotel.setPlace("Menton");
        hotel.setDateArrival("10.10.1010");
        hotel.setDateDeparture("11.11.1111");
        hotel.setPrice((int) CHEAPEST_HOTEL_PRICE);
        hotel.setUid("hoteluid");
        hotel.setName("Pastor Hotel");

        TravelCar car = new TravelCar();
        car.setPlace("Menton");
        car.setDuration(10);
        car.setName("Taxi Pastor");
        car.setUid("1");
        car.setPrice(CHEAPEST_CAR_PRICE * 10);

        TravelFlight flight = new TravelFlight();
        flight.setDestination("Menton");
        flight.setDate("10.10.1010");
        flight.setIsDirect(true);
        flight.setPrice(CHEAPEST_FLIGHT_PRICE);
        flight.setId("714"); //best reference ever, +9999 point.

        finalRequest = new TravelRequest();
        finalRequest.setHotel(hotel);
        finalRequest.setCar(car);
        finalRequest.setFlight(flight);
        finalRequest.setEmail("tata@toto.com");
    }



}

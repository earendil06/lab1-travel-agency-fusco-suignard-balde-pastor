package fr.unice.groupe4.flows;

import fr.unice.groupe4.flows.data.Car;
import fr.unice.groupe4.flows.data.Hotel;
import fr.unice.groupe4.flows.data.TravelFlight;
import fr.unice.groupe4.flows.data.TravelRequest;
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
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                this.includeRoutes(deathPool);
                this.setErrorHandlerBuilder(deathPool.getErrorHandlerBuilder());
                this.includeRoutes(new ProcessTravelRequest());
                this.includeRoutes(new ExternalServices());
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
        Car car = new Car();
        car.setPlace("Menton");
        car.setDuration(10);
        Hotel hotel = new Hotel();
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
        Hotel hotel = new Hotel();
        hotel.setPlace("Menton");
        hotel.setDateArrival("10.10.1010");
        hotel.setDateDeparture("11.11.1111");
        hotel.setPrice(100);
        hotel.setUid("hoteluid");
        hotel.setName("Pastor Hotel");

        Car car = new Car();
        car.setPlace("Menton");
        car.setDuration(10);
        car.setName("Taxi Pastor");
        car.setUid("caruid");

        TravelFlight flight = new TravelFlight();
        flight.setDestination("Menton");
        flight.setDate("10.10.1010");
        flight.setIsDirect(true);
        flight.setPrice(42);
        flight.setId("714"); //best reference ever, +9999 point.

        finalRequest = new TravelRequest();
        finalRequest.setHotel(hotel);
        finalRequest.setCar(car);
        finalRequest.setFlight(flight);
        finalRequest.setEmail("tata@toto.com");
    }

}

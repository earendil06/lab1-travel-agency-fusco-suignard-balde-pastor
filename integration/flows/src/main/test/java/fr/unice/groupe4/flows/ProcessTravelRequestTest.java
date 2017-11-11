package fr.unice.groupe4.flows;

import com.google.gson.Gson;
import org.apache.camel.Exchange;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static fr.unice.groupe4.flows.utils.Endpoints.*;

/**
 * General tests, normal cases and special input cases.
 */
public class ProcessTravelRequestTest extends ActiveMQTest {

    @Override
    public String isMockEndpointsAndSkip() {
        return COMPARE_FLIGHT_ENDPOINT +
                "|" + COMPARE_CAR_ENDPOINT +
                "|" + COMPARE_HOTEL_ENDPOINT +
                "|" + HANDLE_FLIGHT_ENDPOINT +
                "|" + HANDLE_HOTEL_ENDPOINT +
                "|" + HANDLE_CAR_ENDPOINT
                ;
    }

    @Override
    public String isMockEndpoints() {
        return TRAVEL_REQUEST_INPUT +
                "|" + DEATH_POOL +
                "|" + BAD_INPUT_QUEUE +
                "|" + HANDLE_REQUEST +
                "|" + RESULT_POOL
                ;
    }

    @Before
    public void initMocks() {
        resetMocks();

        mock(HANDLE_HOTEL_ENDPOINT).whenAnyExchangeReceived((Exchange exc) -> {
            exc.getIn().setBody(finalRequest.getHotel());
        });

        mock(HANDLE_CAR_ENDPOINT).whenAnyExchangeReceived((Exchange exc) -> {
            exc.getIn().setBody(finalRequest.getCar());
        });

        mock(HANDLE_FLIGHT_ENDPOINT).whenAnyExchangeReceived((Exchange exc) -> {
            exc.getIn().setBody(finalRequest.getFlight());
        });
    }

    @Test
    public void testAllFlowWithoutExternal() throws Exception {
        mock(TRAVEL_REQUEST_INPUT).expectedMessageCount(1);
        mock(HANDLE_HOTEL_ENDPOINT).expectedMessageCount(1);

        Gson gson = new Gson();
        String travelRequestJson = gson.toJson(travelRequest);
        template.sendBody(TRAVEL_REQUEST_INPUT, travelRequestJson);

        mock(RESULT_POOL).expectedBodiesReceived(gson.toJson(finalRequest));

        assertMockEndpointsSatisfied(10, TimeUnit.SECONDS);
    }

    @Test
    public void testAllFlowFail() throws Exception {
        mock(TRAVEL_REQUEST_INPUT).expectedMessageCount(1);
        mock(BAD_INPUT_QUEUE).expectedMessageCount(1);
        mock(HANDLE_HOTEL_ENDPOINT).expectedMessageCount(0);
        mock(HANDLE_CAR_ENDPOINT).expectedMessageCount(0);
        mock(HANDLE_FLIGHT_ENDPOINT).expectedMessageCount(0);

        template.sendBody(TRAVEL_REQUEST_INPUT, "fail");

        assertMockEndpointsSatisfied(1, TimeUnit.SECONDS);
    }

    //[MULTIPLE UNEXPECTED DATA INPUT CHECK]

    //[FLIGHT]

    /**
     * Send a travel request that does not contains a flight. <br />
     * The program should end well and return a TravelRequest that has the flight field null.
     *
     * @throws Exception ignore
     */
    @Test
    public void testAllFlowWithoutFlight() throws Exception {
        mock(TRAVEL_REQUEST_INPUT).expectedMessageCount(1);
        mock(RESULT_POOL).expectedMessageCount(1);
        mock(HANDLE_HOTEL_ENDPOINT).expectedMessageCount(1);
        mock(HANDLE_CAR_ENDPOINT).expectedMessageCount(1);

        mock(HANDLE_FLIGHT_ENDPOINT).expectedMessageCount(0);

        String json =
                "{" +
                        "\"email\" : \"tata@toto.com\"," +
                        "\"car\" : { \"place\" : \"Menton\", \"duration\": 2}," +
                        "\"hotel\" : { \"place\" : \"Menton\", \"dateArrival\" : \"11.11.2016\", \"dateDeparture\" : \"18.11.2016\" }" +
                        "}";

        Gson gson = new Gson();
        String expected =
                "{" +
                        "\"email\":" + gson.toJson(finalRequest.getEmail()) + "," +
                        "\"flight\":null," +
                        "\"hotel\":" + gson.toJson(finalRequest.getHotel()) + "," +
                        "\"car\":" + gson.toJson(finalRequest.getCar()) +
                        "}";

        template.sendBody(TRAVEL_REQUEST_INPUT, json);

        mock(RESULT_POOL).expectedBodiesReceived(expected);

        assertMockEndpointsSatisfied(10, TimeUnit.SECONDS);
    }

    /**
     * Send a travel request that contains a flight property but that is empty.<br />
     * The program should end well and return a TravelRequest that has the flight field null.
     *
     * @throws Exception ignore
     */
    @Test
    public void testAllFlowWithFlightEmpty() throws Exception {
        mock(TRAVEL_REQUEST_INPUT).expectedMessageCount(1);
        mock(RESULT_POOL).expectedMessageCount(1);
        mock(HANDLE_HOTEL_ENDPOINT).expectedMessageCount(1);
        mock(HANDLE_CAR_ENDPOINT).expectedMessageCount(1);

        mock(HANDLE_FLIGHT_ENDPOINT).expectedMessageCount(1);
        mock(HANDLE_FLIGHT_ENDPOINT).whenAnyExchangeReceived((Exchange exc) -> exc.getIn().setBody(null));

        String json =
                "{" +
                        "\"flight\": {}," +
                        "\"email\" : \"tata@toto.com\"," +
                        "\"car\" : { \"place\" : \"Menton\", \"duration\": 2}," +
                        "\"hotel\" : { \"place\" : \"Menton\", \"dateArrival\" : \"11.11.2016\", \"dateDeparture\" : \"18.11.2016\" }" +
                        "}";

        Gson gson = new Gson();
        String expected =
                "{" +
                        "\"email\":" + gson.toJson(finalRequest.getEmail()) + "," +
                        "\"flight\":null," +
                        "\"hotel\":" + gson.toJson(finalRequest.getHotel()) + "," +
                        "\"car\":" + gson.toJson(finalRequest.getCar()) +
                        "}";

        template.sendBody(TRAVEL_REQUEST_INPUT, json);

        mock(RESULT_POOL).expectedBodiesReceived(expected);

        assertMockEndpointsSatisfied(10, TimeUnit.SECONDS);
    }

    //[HOTEL]

    /**
     * Send a travel request that does not contains a hotel. <br />
     * The program should end well and return a TravelRequest that has the hotel field null.
     *
     * @throws Exception ignore
     */
    @Test
    public void testAllFlowWithoutHotel() throws Exception {
        mock(TRAVEL_REQUEST_INPUT).expectedMessageCount(1);
        mock(RESULT_POOL).expectedMessageCount(1);
        mock(HANDLE_FLIGHT_ENDPOINT).expectedMessageCount(1);
        mock(HANDLE_CAR_ENDPOINT).expectedMessageCount(1);

        mock(HANDLE_HOTEL_ENDPOINT).expectedMessageCount(0);

        String json =
                "{" +
                        "\"flight\" : { \"destination\" : \"Menton\" }," +
                        "\"email\" : \"tata@toto.com\"," +
                        "\"car\" : { \"place\" : \"Menton\", \"duration\": 2}" +
                        "}";

        Gson gson = new Gson();
        String expected =
                "{" +
                        "\"email\":" + gson.toJson(finalRequest.getEmail()) + "," +
                        "\"flight\":" + gson.toJson(finalRequest.getFlight()) + "," +
                        "\"hotel\":null," +
                        "\"car\":" + gson.toJson(finalRequest.getCar()) +
                        "}";

        template.sendBody(TRAVEL_REQUEST_INPUT, json);

        mock(RESULT_POOL).expectedBodiesReceived(expected);

        assertMockEndpointsSatisfied(10, TimeUnit.SECONDS);
    }

    /**
     * Send a travel request that contains a hotel property but that is empty.<br />
     * The program should end well and return a TravelRequest that has the hotel field null.
     *
     * @throws Exception ignore
     */
    @Test
    public void testAllFlowWithHotelEmpty() throws Exception {
        mock(TRAVEL_REQUEST_INPUT).expectedMessageCount(1);
        mock(RESULT_POOL).expectedMessageCount(1);
        mock(HANDLE_FLIGHT_ENDPOINT).expectedMessageCount(1);
        mock(HANDLE_CAR_ENDPOINT).expectedMessageCount(1);

        mock(HANDLE_HOTEL_ENDPOINT).expectedMessageCount(1);
        mock(HANDLE_HOTEL_ENDPOINT).whenAnyExchangeReceived((Exchange exc) -> exc.getIn().setBody(null));

        String json =
                "{" +
                        "\"flight\" : { \"destination\" : \"Menton\" }," +
                        "\"email\" : \"tata@toto.com\"," +
                        "\"car\" : { \"place\" : \"Menton\", \"duration\": 2}," +
                        "\"hotel\" : {}" +
                        "}";

        Gson gson = new Gson();
        String expected =
                "{" +
                        "\"email\":" + gson.toJson(finalRequest.getEmail()) + "," +
                        "\"flight\":" + gson.toJson(finalRequest.getFlight()) + "," +
                        "\"hotel\":null," +
                        "\"car\":" + gson.toJson(finalRequest.getCar()) +
                        "}";

        template.sendBody(TRAVEL_REQUEST_INPUT, json);

        mock(RESULT_POOL).expectedBodiesReceived(expected);

        assertMockEndpointsSatisfied(10, TimeUnit.SECONDS);
    }

    //[CAR]

    /**
     * Send a travel request that does not contains a car. <br />
     * The program should end well and return a TravelRequest that has the car field null.
     *
     * @throws Exception ignore
     */
    @Test
    public void testAllFlowWithoutCar() throws Exception {
        mock(TRAVEL_REQUEST_INPUT).expectedMessageCount(1);
        mock(RESULT_POOL).expectedMessageCount(1);
        mock(HANDLE_FLIGHT_ENDPOINT).expectedMessageCount(1);
        mock(HANDLE_HOTEL_ENDPOINT).expectedMessageCount(1);

        mock(HANDLE_CAR_ENDPOINT).expectedMessageCount(0);

        String json =
                "{" +
                        "\"flight\" : { \"destination\" : \"Menton\" }," +
                        "\"email\" : \"tata@toto.com\"," +
                        "\"hotel\" : { \"place\" : \"Menton\", \"dateArrival\" : \"11.11.2016\", \"dateDeparture\" : \"18.11.2016\" }" +
                        "}";

        Gson gson = new Gson();
        String expected =
                "{" +
                        "\"email\":" + gson.toJson(finalRequest.getEmail()) + "," +
                        "\"flight\":" + gson.toJson(finalRequest.getFlight()) + "," +
                        "\"hotel\":" + gson.toJson(finalRequest.getHotel()) + "," +
                        "\"car\":null" +
                        "}";

        template.sendBody(TRAVEL_REQUEST_INPUT, json);

        mock(RESULT_POOL).expectedBodiesReceived(expected);

        assertMockEndpointsSatisfied(10, TimeUnit.SECONDS);
    }

    /**
     * Send a travel request that contains a car property but that is empty.<br />
     * The program should end well and return a TravelRequest that has the car field null.
     *
     * @throws Exception ignore
     */
    @Test
    public void testAllFlowWithCarEmpty() throws Exception {
        mock(TRAVEL_REQUEST_INPUT).expectedMessageCount(1);
        mock(RESULT_POOL).expectedMessageCount(1);
        mock(HANDLE_FLIGHT_ENDPOINT).expectedMessageCount(1);
        mock(HANDLE_HOTEL_ENDPOINT).expectedMessageCount(1);

        mock(HANDLE_CAR_ENDPOINT).expectedMessageCount(1);
        mock(HANDLE_CAR_ENDPOINT).whenAnyExchangeReceived((Exchange exc) -> exc.getIn().setBody(null));

        String json =
                "{" +
                        "\"flight\" : { \"destination\" : \"Menton\" }," +
                        "\"email\" : \"tata@toto.com\"," +
                        "\"hotel\" : { \"place\" : \"Menton\", \"dateArrival\" : \"11.11.2016\", \"dateDeparture\" : \"18.11.2016\" }," +
                        "\"car\" : {}" +
                        "}";

        Gson gson = new Gson();
        String expected =
                "{" +
                        "\"email\":" + gson.toJson(finalRequest.getEmail()) + "," +
                        "\"flight\":" + gson.toJson(finalRequest.getFlight()) + "," +
                        "\"hotel\":" + gson.toJson(finalRequest.getHotel()) + "," +
                        "\"car\":null" +
                        "}";

        template.sendBody(TRAVEL_REQUEST_INPUT, json);

        mock(RESULT_POOL).expectedBodiesReceived(expected);

        assertMockEndpointsSatisfied(10, TimeUnit.SECONDS);
    }
    //[END : MULTIPLE UNEXPECTED DATA INPUT CHECK]
}

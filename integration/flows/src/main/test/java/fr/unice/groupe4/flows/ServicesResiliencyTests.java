package fr.unice.groupe4.flows;

import com.google.gson.Gson;
import org.apache.camel.Exchange;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static fr.unice.groupe4.flows.utils.Endpoints.*;

/**
 * Mock the services endpoints to return IOException and check the resiliency of the system. <br />
 * The system is expected to retry the requests 2 times before returning a null result that will be passed until the
 * final result. <br />
 * A good test would be to mock a response from a service while the bus is retrying but it doesn't seem possible.
 */
public class ServicesResiliencyTests extends ActiveMQTest {

    @Override
    public String isMockEndpointsAndSkip() {
        return CAR_ENDPOINT
                + "|" + OTHER_CAR_ENDPOINT
                + "|" + HOTEL_ENDPOINT
                + "|" + OTHER_HOTEL_ENDPOINT
                + "|" + FLIGHT_ENDPOINT
                + "|" + OTHER_FLIGHT_ENDPOINT
                ;
    }

    @Override
    public String isMockEndpoints() {
        return COMPARE_FLIGHT_ENDPOINT
                + "|" + COMPARE_CAR_ENDPOINT
                + "|" + COMPARE_HOTEL_ENDPOINT
                + "|" + COMPARE_OUR_FLIGHT
                + "|" + COMPARE_OTHER_FLIGHT
                + "|" + COMPARE_OUR_CAR
                + "|" + COMPARE_OTHER_CAR
                + "|" + COMPARE_OUR_HOTEL
                + "|" + COMPARE_OTHER_HOTEL

                + "|" + TRAVEL_REQUEST_INPUT
                + "|" + DEATH_POOL
                + "|" + RESULT_POOL
                ;
    }

    @Test
    public void testExecutionContext() throws Exception {
        isAvailableAndMocked(CAR_ENDPOINT);
        isAvailableAndMocked(OTHER_CAR_ENDPOINT);
        isAvailableAndMocked(HOTEL_ENDPOINT);
        isAvailableAndMocked(OTHER_HOTEL_ENDPOINT);
        isAvailableAndMocked(FLIGHT_ENDPOINT);
        isAvailableAndMocked(OTHER_FLIGHT_ENDPOINT);

        isAvailableAndMocked(COMPARE_CAR_ENDPOINT);
        isAvailableAndMocked(COMPARE_HOTEL_ENDPOINT);
        isAvailableAndMocked(COMPARE_FLIGHT_ENDPOINT);
        isAvailableAndMocked(COMPARE_OUR_FLIGHT);
        isAvailableAndMocked(COMPARE_OTHER_FLIGHT);
        isAvailableAndMocked(COMPARE_OUR_CAR);
        isAvailableAndMocked(COMPARE_OTHER_CAR);
        isAvailableAndMocked(COMPARE_OUR_HOTEL);
        isAvailableAndMocked(COMPARE_OTHER_HOTEL);
    }

    @Before
    public void initMocks() {
        resetMocks();
        mock(HOTEL_ENDPOINT).whenAnyExchangeReceived((Exchange exc) -> {
            throw new IOException();
        });
        mock(CAR_ENDPOINT).whenAnyExchangeReceived((Exchange exc) -> {
            throw new IOException();
        });
        mock(FLIGHT_ENDPOINT).whenAnyExchangeReceived((Exchange exc) -> {
            throw new IOException();
        });
        mock(OTHER_HOTEL_ENDPOINT).whenAnyExchangeReceived((Exchange exc) -> {
            throw new IOException();
        });
        mock(OTHER_CAR_ENDPOINT).whenAnyExchangeReceived((Exchange exc) -> {
            throw new IOException();
        });
        mock(OTHER_FLIGHT_ENDPOINT).whenAnyExchangeReceived((Exchange exc) -> {
            throw new IOException();
        });
    }

    /**
     * Check that the message is send 3 times when the service throw an IOException. <br />
     * The expected result for this route is null.
     *
     * @throws Exception ignore
     */
    @Test
    public void hotelResiliencyTest() throws Exception {
        mock(COMPARE_OUR_HOTEL).expectedMessageCount(1);
        mock(HOTEL_ENDPOINT).expectedMessageCount(3);

        Object out = template.requestBody(COMPARE_OUR_HOTEL, travelRequest.getHotel());
        assertMockEndpointsSatisfied(1, TimeUnit.SECONDS);

        assertEquals(null, out);
    }

    /**
     * Check that the message is send 3 times when the service throw an IOException. <br />
     * The expected result for this route is null.
     *
     * @throws Exception ignore
     */
    @Test
    public void carResiliencyTest() throws Exception {
        mock(COMPARE_OUR_CAR).expectedMessageCount(1);
        mock(CAR_ENDPOINT).expectedMessageCount(3);

        Object out = template.requestBody(COMPARE_OUR_CAR, travelRequest.getCar());
        assertMockEndpointsSatisfied(1, TimeUnit.SECONDS);

        assertEquals(null, out);
    }

    /**
     * Check that the message is send 3 times when the service throw an IOException. <br />
     * The expected result for this route is null.
     *
     * @throws Exception ignore
     */
    @Test
    public void flightResiliencyTest() throws Exception {
        mock(COMPARE_OUR_FLIGHT).expectedMessageCount(1);
        mock(FLIGHT_ENDPOINT).expectedMessageCount(3);

        Object out = template.requestBody(COMPARE_OUR_FLIGHT, travelRequest.getFlight());
        assertMockEndpointsSatisfied(1, TimeUnit.SECONDS);

        assertEquals(null, out);
    }

    /**
     * All services endpoints return an IOException. <br />
     * We send a correct request, the expected results is only the mail and every other fields at null.
     *
     * @throws Exception ignore
     */
    @Test
    public void allFlowResiliencyTest() throws Exception {
        mock(TRAVEL_REQUEST_INPUT).expectedMessageCount(1);

        mock(COMPARE_OUR_FLIGHT).expectedMessageCount(1);
        mock(COMPARE_OTHER_FLIGHT).expectedMessageCount(1);
        mock(FLIGHT_ENDPOINT).expectedMessageCount(3);
        mock(OTHER_FLIGHT_ENDPOINT).expectedMessageCount(3);

        mock(COMPARE_OUR_CAR).expectedMessageCount(1);
        mock(COMPARE_OTHER_CAR).expectedMessageCount(1);
        mock(CAR_ENDPOINT).expectedMessageCount(3);
        mock(OTHER_CAR_ENDPOINT).expectedMessageCount(3);

        mock(COMPARE_OUR_HOTEL).expectedMessageCount(1);
        mock(COMPARE_OTHER_HOTEL).expectedMessageCount(1);
        mock(HOTEL_ENDPOINT).expectedMessageCount(3);
        mock(OTHER_HOTEL_ENDPOINT).expectedMessageCount(3);

        mock(RESULT_POOL).expectedMessageCount(1);

        Gson gson = new Gson();
        String travelRequestJson = gson.toJson(travelRequest);
        template.sendBody(TRAVEL_REQUEST_INPUT, travelRequestJson);

        String expected = "{\"email\":\"tata@toto.com\",\"flight\":null,\"hotel\":null,\"car\":null}";
        mock(RESULT_POOL).expectedBodiesReceived(expected);

        assertMockEndpointsSatisfied(10, TimeUnit.SECONDS);
    }

    private void mockFlights() {
        mock(FLIGHT_ENDPOINT).whenAnyExchangeReceived((Exchange exc) -> {
            String template = "[ {\n" +
                    "    \"date\": \"10.10.1010\",\n" +
                    "    \"duration\": 12,\n" +
                    "    \"uid\": \"714\",\n" +
                    "    \"hour\": \"10.10\",\n" +
                    "    \"price\": " + HIGHEST_FLIGHT_PRICE + ",\n" +
                    "    \"direct\": true,\n" +
                    "    \"from\": \"Cogolin\",\n" +
                    "    \"to\": \"Menton\"\n" +
                    "  }\n ]";
            exc.getIn().setBody(template);
        });

        mock(OTHER_FLIGHT_ENDPOINT).whenAnyExchangeReceived((Exchange exc) -> {
            String template = "{\n" +
                    "    \"size\": 1,\n" +
                    "    \"vols\": [\n" +
                    "        {\n" +
                    "            \"date\": \"10.10.1010\",\n" +
                    "            \"price\": \"" + CHEAPEST_FLIGHT_PRICE + "\",\n" +
                    "            \"destination\": \"Menton\",\n" +
                    "            \"id\": \"714\",\n" +
                    "            \"stops\": [],\n" +
                    "            \"isDirect\": true\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";
            exc.getIn().setBody(template);
        });
    }

    private void mockCars() {
        mock(CAR_ENDPOINT).whenAnyExchangeReceived((Exchange exc) -> {
            String template = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                    "    <soap:Body>\n" +
                    "        <ns2:getCarByPlaceResponse xmlns:ns2=\"http://service.planner/\">\n" +
                    "            <car_planner_result>[{\"duration\":10,\"uid\":\"caruid\"," +
                    "\"name\":\"Taxi Pastor\",\"place\":\"Menton\",\"price\":" + HIGHEST_CAR_PRICE + "}]</car_planner_result>\n" +
                    "        </ns2:getCarByPlaceResponse>\n" +
                    "    </soap:Body>\n" +
                    "</soap:Envelope>";
            exc.getIn().setBody(template);
        });

        mock(OTHER_CAR_ENDPOINT).whenAnyExchangeReceived((Exchange exc) -> {
            String template = "[\n" +
                    "    {\n" +
                    "        \"agency\": {\n" +
                    "            \"country\": \"France\",\n" +
                    "            \"address\": \"7 Boyd Circle\",\n" +
                    "            \"city\": \"Menton\",\n" +
                    "            \"name\": \"Taxi Pastor\"\n" +
                    "        },\n" +
                    "        \"year\": 2005,\n" +
                    "        \"priceperday\": " + CHEAPEST_CAR_PRICE + ",\n" +
                    "        \"model\": \"Town & Country\",\n" +
                    "        \"id\": 1,\n" +
                    "        \"bookings\": [],\n" +
                    "        \"make\": \"Chrysler\"\n" +
                    "    }]";
            exc.getIn().setBody(template);
        });
    }

    /**
     * Actually only one hotel service.
     */
    private void mockHotels() {
        mock(HOTEL_ENDPOINT).whenAnyExchangeReceived((Exchange exc) -> {
            String template = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                    "    <soap:Body>\n" +
                    "        <ns2:getHotelsForTravelResponse xmlns:ns2=\"http://service.planner/\">\n" +
                    "            <hotel_planner_result>[{\"uid\":\"hoteluid\",\"dateArrival\":\"10.10.1010\"," +
                    "\"price\":" + CHEAPEST_HOTEL_PRICE + ",\"name\":\"Pastor Hotel\",\"place\":\"Menton\",\"dateDeparture\":\"11.11.1111\"}]</hotel_planner_result>\n" +
                    "        </ns2:getHotelsForTravelResponse>\n" +
                    "    </soap:Body>\n" +
                    "</soap:Envelope>";
            exc.getIn().setBody(template);
        });
    }

    /**
     * Mock a result on Car and Flight endpoints but not on Hotel. <br />
     * Should finish with hotel = null and other with a value.
     *
     * @throws Exception ignore
     */
    @Test
    public void allFlowWithoutHotelResiliencyTest() throws Exception {
        mockFlights();
        mockCars();

        mock(TRAVEL_REQUEST_INPUT).expectedMessageCount(1);

        mock(COMPARE_OUR_FLIGHT).expectedMessageCount(1);
        mock(COMPARE_OTHER_FLIGHT).expectedMessageCount(1);
        mock(FLIGHT_ENDPOINT).expectedMessageCount(1);
        mock(OTHER_FLIGHT_ENDPOINT).expectedMessageCount(1);

        mock(COMPARE_OUR_CAR).expectedMessageCount(1);
        mock(COMPARE_OTHER_CAR).expectedMessageCount(1);
        mock(CAR_ENDPOINT).expectedMessageCount(1);
        mock(OTHER_CAR_ENDPOINT).expectedMessageCount(1);

        //hotel is expected to try his request 3 times
        mock(COMPARE_OUR_HOTEL).expectedMessageCount(1);
        mock(COMPARE_OTHER_HOTEL).expectedMessageCount(1);
        mock(HOTEL_ENDPOINT).expectedMessageCount(3);
        mock(OTHER_HOTEL_ENDPOINT).expectedMessageCount(3);

        mock(RESULT_POOL).expectedMessageCount(1);

        Gson gson = new Gson();
        String travelRequestJson = gson.toJson(travelRequest);
        template.sendBody(TRAVEL_REQUEST_INPUT, travelRequestJson);

        String expected =
                "{" +
                        "\"email\":" + gson.toJson(finalRequest.getEmail()) + "," +
                        "\"flight\":" + gson.toJson(finalRequest.getFlight()) + "," +
                        "\"hotel\":null," +
                        "\"car\":" + gson.toJson(finalRequest.getCar()) +
                        "}";
        mock(RESULT_POOL).expectedBodiesReceived(expected);

        assertMockEndpointsSatisfied(10, TimeUnit.SECONDS);
    }

    /**
     * Mock a result on Car and Hotel endpoints but not on Flight. <br />
     * Should finish with flight = null and other with a value.
     *
     * @throws Exception ignore
     */
    @Test
    public void allFlowWithoutFlightResiliencyTest() throws Exception {
        mockHotels();
        mockCars();

        mock(TRAVEL_REQUEST_INPUT).expectedMessageCount(1);

        //flight is expected to try his request 3 times
        mock(COMPARE_OUR_FLIGHT).expectedMessageCount(1);
        mock(COMPARE_OTHER_FLIGHT).expectedMessageCount(1);
        mock(FLIGHT_ENDPOINT).expectedMessageCount(3);
        mock(OTHER_FLIGHT_ENDPOINT).expectedMessageCount(3);

        mock(COMPARE_OUR_CAR).expectedMessageCount(1);
        mock(COMPARE_OTHER_CAR).expectedMessageCount(1);
        mock(CAR_ENDPOINT).expectedMessageCount(1);
        mock(OTHER_CAR_ENDPOINT).expectedMessageCount(1);

        mock(COMPARE_OUR_HOTEL).expectedMessageCount(1);
        mock(COMPARE_OTHER_HOTEL).expectedMessageCount(1);
        mock(HOTEL_ENDPOINT).expectedMessageCount(1);
        //hotel is expected to try his request 3 times
        mock(OTHER_HOTEL_ENDPOINT).expectedMessageCount(3);

        mock(RESULT_POOL).expectedMessageCount(1);

        Gson gson = new Gson();
        String travelRequestJson = gson.toJson(travelRequest);
        template.sendBody(TRAVEL_REQUEST_INPUT, travelRequestJson);

        String expected =
                "{" +
                        "\"email\":" + gson.toJson(finalRequest.getEmail()) + "," +
                        "\"flight\":null," +
                        "\"hotel\":" + gson.toJson(finalRequest.getHotel()) + "," +
                        "\"car\":" + gson.toJson(finalRequest.getCar()) +
                        "}";
        mock(RESULT_POOL).expectedBodiesReceived(expected);

        assertMockEndpointsSatisfied(10, TimeUnit.SECONDS);
    }

    /**
     * Mock a result on Flight and Hotel endpoints but not on Car. <br />
     * Should finish with car = null and other with a value.
     *
     * @throws Exception ignore
     */
    @Test
    public void allFlowWithoutCarResiliencyTest() throws Exception {
        mockHotels();
        mockFlights();

        mock(TRAVEL_REQUEST_INPUT).expectedMessageCount(1);

        mock(COMPARE_OUR_FLIGHT).expectedMessageCount(1);
        mock(COMPARE_OTHER_FLIGHT).expectedMessageCount(1);
        mock(FLIGHT_ENDPOINT).expectedMessageCount(1);
        mock(OTHER_FLIGHT_ENDPOINT).expectedMessageCount(1);

        //cars is expected to try his request 3 times
        mock(COMPARE_OUR_CAR).expectedMessageCount(1);
        mock(COMPARE_OTHER_CAR).expectedMessageCount(1);
        mock(CAR_ENDPOINT).expectedMessageCount(3);
        mock(OTHER_CAR_ENDPOINT).expectedMessageCount(3);

        mock(COMPARE_OUR_HOTEL).expectedMessageCount(1);
        mock(COMPARE_OTHER_HOTEL).expectedMessageCount(1);
        mock(HOTEL_ENDPOINT).expectedMessageCount(1);
        //hotel is expected to try his request 3 times
        mock(OTHER_HOTEL_ENDPOINT).expectedMessageCount(3);

        mock(RESULT_POOL).expectedMessageCount(1);

        Gson gson = new Gson();
        String travelRequestJson = gson.toJson(travelRequest);
        template.sendBody(TRAVEL_REQUEST_INPUT, travelRequestJson);

        String expected =
                "{" +
                        "\"email\":" + gson.toJson(finalRequest.getEmail()) + "," +
                        "\"flight\":" + gson.toJson(finalRequest.getFlight()) + "," +
                        "\"hotel\":" + gson.toJson(finalRequest.getHotel()) + "," +
                        "\"car\":null" +
                        "}";
        mock(RESULT_POOL).expectedBodiesReceived(expected);

        assertMockEndpointsSatisfied(10, TimeUnit.SECONDS);
    }
}

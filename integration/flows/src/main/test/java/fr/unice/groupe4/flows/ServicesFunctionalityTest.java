package fr.unice.groupe4.flows;

import com.google.gson.Gson;
import fr.unice.groupe4.flows.data.traveldata.TravelCar;
import fr.unice.groupe4.flows.data.traveldata.TravelFlight;
import org.apache.camel.Exchange;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static fr.unice.groupe4.flows.utils.Endpoints.*;

public class ServicesFunctionalityTest extends ActiveMQTest {

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
                + "|" + COMPARE_FLIGHT_ENDPOINT
                + "|" + COMPARE_OUR_FLIGHT
                + "|" + COMPARE_OTHER_FLIGHT
                + "|" + COMPARE_OUR_CAR
                + "|" + COMPARE_OTHER_CAR
                + "|" + COMPARE_OUR_HOTEL
                + "|" + COMPARE_OTHER_HOTEL

                + "|" + HANDLE_CAR_ENDPOINT
                + "|" + HANDLE_FLIGHT_ENDPOINT
                + "|" + HANDLE_HOTEL_ENDPOINT

                + "|" + TRAVEL_REQUEST_INPUT
                + "|" + DEATH_POOL
                + "|" + RESULT_POOL
                ;
    }

    @Test
    public void testExecutionContext() throws Exception {
        System.out.println();
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

        isAvailableAndMocked(HANDLE_CAR_ENDPOINT);
        isAvailableAndMocked(HANDLE_FLIGHT_ENDPOINT);
        isAvailableAndMocked(HANDLE_HOTEL_ENDPOINT);
    }

    @Before
    public void initMocks() {
        resetMocks();
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

        //todo other hotel tests
        mock(OTHER_HOTEL_ENDPOINT).whenAnyExchangeReceived((Exchange exc) -> {
            String template = "{\n" +
                    "    \"hotels\": [\n" +
                    "        {\n" +
                    "            \"city\": \"Menton\",\n" +
                    "            \"price_per_night\": "+HIGHEST_HOTEL_PRICE+",\n" +
                    "            \"hotel_type\": \"Chambre simple\",\n" +
                    "            \"id\": 0,\n" +
                    "            \"hotel_name\": \"Pastor Hotel\"\n" +
                    "        }]}";
            exc.getIn().setBody(template);
        });
    }


    @Test
    public void testOurHotelExternal() throws Exception {
        mock(COMPARE_OUR_HOTEL).expectedMessageCount(1);
        mock(HOTEL_ENDPOINT).expectedMessageCount(1);

        Object out = template.requestBody(COMPARE_OUR_HOTEL, travelRequest.getHotel());
        assertMockEndpointsSatisfied(1, TimeUnit.SECONDS);

        assertEquals(finalRequest.getHotel().toString(), out.toString());
    }

    //@Test
    public void testOtherHotelExternal() throws Exception {
        //hard to test because of integration specificity
    }

    @Test
    public void testOurCarExternal() throws Exception {
        mock(COMPARE_OUR_CAR).expectedMessageCount(1);
        mock(CAR_ENDPOINT).expectedMessageCount(1);

        Object out = template.requestBody(COMPARE_OUR_CAR, travelRequest.getCar());
        assertMockEndpointsSatisfied(1, TimeUnit.SECONDS);
        TravelCar car = finalRequest.getCar();
        car.setPrice(HIGHEST_CAR_PRICE);
        car.setUid("caruid");

        assertEquals(car.toString(), out.toString());
    }

    @Test
    public void testOtherCarExternal() throws Exception {
        mock(COMPARE_OTHER_CAR).expectedMessageCount(1);
        mock(OTHER_CAR_ENDPOINT).expectedMessageCount(1);

        Object out = template.requestBody(COMPARE_OTHER_CAR, travelRequest.getCar());
        assertMockEndpointsSatisfied(10, TimeUnit.SECONDS);

        assertEquals(finalRequest.getCar().toString(), out.toString());
    }

    @Test
    public void testOurFlightExternal() throws Exception {
        mock(COMPARE_OUR_FLIGHT).expectedMessageCount(1);
        mock(FLIGHT_ENDPOINT).expectedMessageCount(1);

        Object out = template.requestBody(COMPARE_OUR_FLIGHT, travelRequest.getFlight());
        assertMockEndpointsSatisfied(1, TimeUnit.SECONDS);

        TravelFlight flight = finalRequest.getFlight();
        flight.setPrice(HIGHEST_FLIGHT_PRICE);
        assertEquals(flight.toString(), out.toString());
    }

    @Test
    public void testOtherFlightExternal() throws Exception {
        mock(COMPARE_OTHER_FLIGHT).expectedMessageCount(1);
        mock(OTHER_FLIGHT_ENDPOINT).expectedMessageCount(1);

        Object out = template.requestBody(COMPARE_OTHER_FLIGHT, travelRequest.getFlight());
        assertMockEndpointsSatisfied(2, TimeUnit.SECONDS);

        assertEquals(finalRequest.getFlight().toString(), out.toString());
    }

    @Test
    public void testMergeFlightExternal() throws Exception {
        mock(COMPARE_OUR_FLIGHT).expectedMessageCount(1);
        mock(FLIGHT_ENDPOINT).expectedMessageCount(1);
        mock(COMPARE_OTHER_FLIGHT).expectedMessageCount(1);
        mock(OTHER_FLIGHT_ENDPOINT).expectedMessageCount(1);
        mock(COMPARE_FLIGHT_ENDPOINT).expectedMessageCount(1);

        Object out = template.requestBody(COMPARE_FLIGHT_ENDPOINT, travelRequest.getFlight());
        assertMockEndpointsSatisfied(5, TimeUnit.SECONDS);

        assertEquals(finalRequest.getFlight().toString(), out.toString());
    }

    //[START : MINIMUM PRICE TESTS]

    @Test
    public void testMinimumPriceFlight() throws Exception {
        mock(HANDLE_FLIGHT_ENDPOINT).expectedMessageCount(1);

        mock(COMPARE_OTHER_FLIGHT).expectedMessageCount(1);
        mock(OTHER_FLIGHT_ENDPOINT).expectedMessageCount(1);
        mock(COMPARE_OUR_FLIGHT).expectedMessageCount(1);
        mock(FLIGHT_ENDPOINT).expectedMessageCount(1);

        Object out = template.requestBody(HANDLE_FLIGHT_ENDPOINT, new Gson().toJson(travelRequest.getFlight()));
        assertMockEndpointsSatisfied(2, TimeUnit.SECONDS);

        //final price is the price of the cheapest flight
        assertEquals(CHEAPEST_FLIGHT_PRICE, finalRequest.getFlight().getPrice(), 0.01);
        assertEquals(finalRequest.getFlight().toString(), out.toString());
    }

    @Test
    public void testMinimumPriceCar() throws Exception {
        mock(HANDLE_CAR_ENDPOINT).expectedMessageCount(1);

        mock(COMPARE_OTHER_CAR).expectedMessageCount(1);
        mock(OTHER_CAR_ENDPOINT).expectedMessageCount(1);
        mock(COMPARE_OUR_CAR).expectedMessageCount(1);
        mock(CAR_ENDPOINT).expectedMessageCount(1);

        Object out = template.requestBody(HANDLE_CAR_ENDPOINT, new Gson().toJson(travelRequest.getCar()));
        assertMockEndpointsSatisfied(2, TimeUnit.SECONDS);

        //final price is the price of the cheapest car
        assertEquals(CHEAPEST_CAR_PRICE * finalRequest.getCar().getDuration(),
                finalRequest.getCar().getPrice(), 0.01);
        assertEquals(finalRequest.getCar().toString(), out.toString());
    }

    //todo hotel minimum price test

    //[END : MINIMUM PRICE TESTS]
}

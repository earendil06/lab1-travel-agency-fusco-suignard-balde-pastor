package fr.unice.groupe4.flows;

import fr.unice.groupe4.flows.data.traveldata.TravelFlight;
import org.apache.camel.Exchange;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static fr.unice.groupe4.flows.utils.Endpoints.*;

public class ExternalServicesTest extends ActiveMQTest {

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
                ;
    }

    @Test
    public void testExecutionContext() throws Exception {
        isAvailableAndMocked(CAR_ENDPOINT);
//        isAvailableAndMocked(OTHER_CAR_ENDPOINT);
        isAvailableAndMocked(HOTEL_ENDPOINT);
//        isAvailableAndMocked(OTHER_HOTEL_ENDPOINT);
        isAvailableAndMocked(FLIGHT_ENDPOINT);
        isAvailableAndMocked(OTHER_FLIGHT_ENDPOINT);

        isAvailableAndMocked(COMPARE_CAR_ENDPOINT);
        isAvailableAndMocked(COMPARE_HOTEL_ENDPOINT);
        isAvailableAndMocked(COMPARE_FLIGHT_ENDPOINT);
        isAvailableAndMocked(COMPARE_OUR_FLIGHT);
        isAvailableAndMocked(COMPARE_OTHER_FLIGHT);
        isAvailableAndMocked(COMPARE_OUR_CAR);
    }

    @Before
    public void initMocks() {
        resetMocks();
        mock(HOTEL_ENDPOINT).whenAnyExchangeReceived((Exchange exc) -> {
            String template = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                    "    <soap:Body>\n" +
                    "        <ns2:getHotelsForTravelResponse xmlns:ns2=\"http://service.planner/\">\n" +
                    "            <hotel_planner_result>[{\"uid\":\"hoteluid\",\"dateArrival\":\"10.10.1010\",\"price\":100,\"name\":\"Pastor Hotel\",\"place\":\"Menton\",\"dateDeparture\":\"11.11.1111\"}]</hotel_planner_result>\n" +
                    "        </ns2:getHotelsForTravelResponse>\n" +
                    "    </soap:Body>\n" +
                    "</soap:Envelope>";
            exc.getIn().setBody(template);
        });

        mock(CAR_ENDPOINT).whenAnyExchangeReceived((Exchange exc) -> {
            String template = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                    "    <soap:Body>\n" +
                    "        <ns2:getCarByPlaceResponse xmlns:ns2=\"http://service.planner/\">\n" +
                    "            <car_planner_result>[{\"duration\":10,\"uid\":\"caruid\",\"name\":\"Taxi Pastor\",\"place\":\"Menton\",\"price\":142}]</car_planner_result>\n" +
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
                    "    \"price\": 43,\n" +
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
                    "            \"price\": \"42\",\n" +
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
                    "            \"name\": \"Green, Sauer and Durgan\"\n" +
                    "        },\n" +
                    "        \"year\": 2005,\n" +
                    "        \"priceperday\": 137.1,\n" +
                    "        \"model\": \"Town & Country\",\n" +
                    "        \"id\": 1,\n" +
                    "        \"bookings\": [],\n" +
                    "        \"make\": \"Chrysler\"\n" +
                    "    }]";
            exc.getIn().setBody(template);
        });

        mock(OTHER_HOTEL_ENDPOINT).whenAnyExchangeReceived((Exchange exc) -> {
            String template = "";
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

    @Test
    public void testOurCarExternal() throws Exception {
        mock(COMPARE_OUR_CAR).expectedMessageCount(1);
        mock(CAR_ENDPOINT).expectedMessageCount(1);

        Object out = template.requestBody(COMPARE_OUR_CAR, travelRequest.getCar());
        assertMockEndpointsSatisfied(1, TimeUnit.SECONDS);

        assertEquals(finalRequest.getCar().toString(), out.toString());
    }

//    @Test
//    public void testOtherCarExternal() throws Exception {
//        mock(COMPARE_OTHER_CAR).expectedMessageCount(1);
//        mock(OTHER_CAR_ENDPOINT).expectedMessageCount(1);
//
//        Object out = template.requestBody(COMPARE_OTHER_CAR, travelRequest.getCar());
//        assertMockEndpointsSatisfied(10, TimeUnit.SECONDS);
//
//        TravelCar car = finalRequest.getCar();
//        car.setUid("caruid");
//        assertEquals(finalRequest.getCar().toString(), out.toString());
//    }

    @Test
    public void testOurFlightExternal() throws Exception {
        mock(COMPARE_OUR_FLIGHT).expectedMessageCount(1);
        mock(FLIGHT_ENDPOINT).expectedMessageCount(1);

        Object out = template.requestBody(COMPARE_OUR_FLIGHT, travelRequest.getFlight());
        assertMockEndpointsSatisfied(1, TimeUnit.SECONDS);

        TravelFlight flight = finalRequest.getFlight();
        flight.setPrice(43);
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
}

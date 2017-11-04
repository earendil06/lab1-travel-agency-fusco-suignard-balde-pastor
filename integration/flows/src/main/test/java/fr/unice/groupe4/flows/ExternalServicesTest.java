package fr.unice.groupe4.flows;

import org.apache.camel.Exchange;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static fr.unice.groupe4.flows.utils.Endpoints.*;

public class ExternalServicesTest extends ActiveMQTest {

    @Override
    public String isMockEndpointsAndSkip() {
        return CAR_ENDPOINT + "|" + HOTEL_ENDPOINT + "|" + FLIGHT_ENDPOINT + "|" + OTHER_FLIGHT_ENDPOINT
                ;
    }

    @Override
    public String isMockEndpoints() {
        return COMPARE_FLIGHT_ENDPOINT + "|" + COMPARE_CAR_ENDPOINT + "|" + COMPARE_HOTEL_ENDPOINT +
         "|" + COMPARE_OUR_FLIGHT + "|" + COMPARE_OTHER_FLIGHT
                ;
    }

    @Test
    public void testExecutionContext() throws Exception {
        isAvailableAndMocked(COMPARE_FLIGHT_ENDPOINT);
        isAvailableAndMocked(COMPARE_CAR_ENDPOINT);
        isAvailableAndMocked(COMPARE_HOTEL_ENDPOINT);
        isAvailableAndMocked(FLIGHT_ENDPOINT);
        isAvailableAndMocked(CAR_ENDPOINT);
        isAvailableAndMocked(HOTEL_ENDPOINT);
        isAvailableAndMocked(COMPARE_OUR_FLIGHT);
        isAvailableAndMocked(COMPARE_OTHER_FLIGHT);
        isAvailableAndMocked(OTHER_FLIGHT_ENDPOINT);
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
                    "            <car_planner_result>[{\"duration\":24,\"uid\":\"caruid\",\"name\":\"Taxi Pastor\",\"place\":\"Menton\"}]</car_planner_result>\n" +
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
                    "    \"price\": 42,\n" +
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
    }
    @Test
    public void testOurFlightExternal() throws Exception {
        mock(FLIGHT_ENDPOINT).expectedMessageCount(1);

        Object out = template.requestBody(COMPARE_OUR_FLIGHT, travelRequest.getFlight());
        assertMockEndpointsSatisfied(1, TimeUnit.SECONDS);

        assertEquals(finalRequest.getFlight().toString(), out.toString());
    }

    @Test
    public void testOtherFlightExternal() throws Exception {
        mock(OTHER_FLIGHT_ENDPOINT).expectedMessageCount(1);

        Object out = template.requestBody(COMPARE_OTHER_FLIGHT, travelRequest.getFlight());
        assertMockEndpointsSatisfied(1, TimeUnit.SECONDS);

        assertEquals(finalRequest.getFlight().toString(), out.toString());
    }
}

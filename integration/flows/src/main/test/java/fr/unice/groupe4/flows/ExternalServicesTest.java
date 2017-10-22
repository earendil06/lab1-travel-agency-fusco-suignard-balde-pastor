package fr.unice.groupe4.flows;

import com.google.gson.Gson;
import org.apache.camel.Exchange;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static fr.unice.groupe4.flows.utils.Endpoints.*;

public class ExternalServicesTest extends ActiveMQTest {

    @Override
    public String isMockEndpointsAndSkip() {
        return CAR_ENDPOINT + "|" + HOTEL_ENDPOINT
                ;
    }

    @Override
    public String isMockEndpoints() {
        return COMPARE_FLIGHT_ENDPOINT + "|" + COMPARE_CAR_ENDPOINT + "|" + COMPARE_HOTEL_ENDPOINT + "|" + FLIGHT_ENDPOINT
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
    }

    @Before
    public void initMocks() {
        resetMocks();
        mock(HOTEL_ENDPOINT).whenAnyExchangeReceived((Exchange exc) -> {
            String template = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                    "    <soap:Body>\n" +
                    "        <ns2:getHotelsForTravelResponse xmlns:ns2=\"http://service.planner/\">\n" +
                    "            <hotel_planner_result>[{\"uid\":\"b9b05899-7f09-4ddc-900f-9e4ed36e1974\",\"dateArrival\":\"09/12/2017\",\"price\":188,\"name\":\"Heaney, Walsh and Roberts\",\"place\":\"Pangkalan\",\"dateDeparture\":\"19/12/2017\"}]</hotel_planner_result>\n" +
                    "        </ns2:getHotelsForTravelResponse>\n" +
                    "    </soap:Body>\n" +
                    "</soap:Envelope>";
            exc.getIn().setBody(template);
        });

        mock(CAR_ENDPOINT).whenAnyExchangeReceived((Exchange exc) -> {
            String template = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                    "    <soap:Body>\n" +
                    "        <ns2:getCarByPlaceResponse xmlns:ns2=\"http://service.planner/\">\n" +
                    "            <car_planner_result>[{\"duration\":24,\"uid\":\"d97db42f-ab89-4d58-9b7c-5b9798c664f2\",\"name\":\"peugeot\",\"place\":\"Pangkalan\"}]</car_planner_result>\n" +
                    "        </ns2:getCarByPlaceResponse>\n" +
                    "    </soap:Body>\n" +
                    "</soap:Envelope>";
            exc.getIn().setBody(template);
        });

        mock(FLIGHT_ENDPOINT).whenAnyExchangeReceived((Exchange exc) -> {
            String template = "[\n" +
                    "    {\n" +
                    "        \"date\": \"09.12.2017\",\n" +
                    "        \"duration\": 12,\n" +
                    "        \"hour\": \"10.10\",\n" +
                    "        \"price\": 12,\n" +
                    "        \"direct\": true,\n" +
                    "        \"from\": \"Paris\",\n" +
                    "        \"to\": \"Pangkalan\"\n" +
                    "    }\n" +
                    "]";
            exc.getIn().setBody(template);
        });
    }

//    @Test
//    public void testFlightExternal() throws Exception {
//        mock(COMPARE_FLIGHT_ENDPOINT).expectedMessageCount(1);
////        mock(DEATH_POOL).expectedMessageCount(1);
//        mock(FLIGHT_ENDPOINT).expectedMessageCount(3);
//
//        Gson gson = new Gson();
//        String travelRequestJson = gson.toJson(travelRequest);
//
//        Object out = template.requestBody(COMPARE_FLIGHT_ENDPOINT, travelRequest.getFlight());
//        System.out.println();
//        System.out.println(out);
//        System.out.println();
//        assertMockEndpointsSatisfied(1, TimeUnit.SECONDS);
//    }
}

package fr.unice.groupe4.flows;

import com.google.gson.Gson;
import org.apache.camel.Exchange;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static fr.unice.groupe4.flows.utils.Endpoints.*;

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
                "|" + RESULT_POOL
                ;
    }

    @Test
    public void testExecutionContext() throws Exception {
        isAvailableAndMocked(TRAVEL_REQUEST_INPUT);
        isAvailableAndMocked(RESULT_POOL);
        isAvailableAndMocked(DEATH_POOL);
        isAvailableAndMocked(HANDLE_FLIGHT_ENDPOINT);
        isAvailableAndMocked(HANDLE_HOTEL_ENDPOINT);
        isAvailableAndMocked(HANDLE_CAR_ENDPOINT);
        isAvailableAndMocked(COMPARE_FLIGHT_ENDPOINT);
        isAvailableAndMocked(COMPARE_CAR_ENDPOINT);
        isAvailableAndMocked(COMPARE_HOTEL_ENDPOINT);
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
        mock(HANDLE_CAR_ENDPOINT).expectedMessageCount(1);
        mock(HANDLE_FLIGHT_ENDPOINT).expectedMessageCount(1);
        mock(RESULT_POOL).expectedMessageCount(1);

        Gson gson = new Gson();
        String travelRequestJson = gson.toJson(travelRequest);
        template.sendBody(TRAVEL_REQUEST_INPUT, travelRequestJson);

        mock(RESULT_POOL).expectedBodiesReceived(gson.toJson(finalRequest));

        assertMockEndpointsSatisfied(1, TimeUnit.SECONDS);
    }

/*    @Test public void testAllFlowFail() throws Exception {
        mock(TRAVEL_REQUEST_INPUT).expectedMessageCount(1);
        mock(DEATH_POOL).expectedMessageCount(0);
        mock(HANDLE_HOTEL_ENDPOINT).expectedMessageCount(0);
        mock(HANDLE_CAR_ENDPOINT).expectedMessageCount(0);
        mock(HANDLE_FLIGHT_ENDPOINT).expectedMessageCount(0);

        template.sendBody(TRAVEL_REQUEST_INPUT, "fail");

        assertMockEndpointsSatisfied(1, TimeUnit.SECONDS);
    }*/
}

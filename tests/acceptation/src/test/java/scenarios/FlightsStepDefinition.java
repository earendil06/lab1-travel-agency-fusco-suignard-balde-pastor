package scenarios;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import flights.Flight;
import flights.Handler;
import org.apache.cxf.jaxrs.client.WebClient;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class FlightsStepDefinition {

    private String host = "localhost";
    private int port = 8080;

    private JSONArray answer;

    private JSONArray call(JSONObject request) {
        String raw =
                WebClient.create("http://" + host + ":" + port + "/tcs-service-doc/flights")
                        .accept(MediaType.APPLICATION_JSON_TYPE)
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .post(request.toString(), String.class);
        return new JSONArray(raw);
    }

    private JSONObject createRetrieveRequest(){
        JSONObject request = new JSONObject();
        request.put("event", "RETRIEVE");
        return request;
    }


    @Given("^an empty registry deployed on (.*):(\\d+)$")
    public void set_clean_registry(String host, int port) {
        this.host = host;
        this.port = port;
        Handler.purge();
    }

    @Given("^a (direct|non-direct) flight from (.*) to (.*) for (\\d+) euros the (\\d\\d/\\d\\d/\\d\\d\\d\\d)-(\\d\\d:\\d\\d) duration (\\d+) min$")
    public void upload_preregistered_citizen(String direct, String from, String to, int price, String date, String hour, int duration) {
        Flight flight = new Flight(from, to, date, hour, duration, price, direct.equals("direct"));
        Handler.create(flight);
    }


    @When("^the parameter date is set as (\\d\\d/\\d\\d/\\d\\d\\d\\d)$")
    public void call_with_date(String date){
        JSONObject request = createRetrieveRequest();
        request.put("date", date);
        answer = call(request);
        assertNotNull(answer);
    }

    @When("^the RETRIEVE message is sent$")
    public void call_registry() {
        JSONObject request = createRetrieveRequest();
        answer = call(request);
        assertNotNull(answer);
    }

    @When("^we require all flights from (.*) to (.*)")
    public void call_with_from_to(String from, String to) {
        JSONObject request = createRetrieveRequest();
        request.put("from", from);
        request.put("to", to);
        answer = call(request);
        assertNotNull(answer);
    }

    @Then("^all the results go from (.*) to (.*)$")
    public void the_locations_are_good(String from, String to) {
        for (int i = 0; i < answer.length(); i++) {
            JSONObject o = answer.getJSONObject(i);
            assertEquals(from, o.get("from"));
            assertEquals(to, o.get("to"));
        }
    }

    @Then("^the answers have as date the value (\\d\\d/\\d\\d/\\d\\d\\d\\d)$")
    public void the_dates_are_good(String expected) {
        for (int i = 0; i < answer.length(); i++) {
            JSONObject o = answer.getJSONObject(i);
            assertEquals(expected, o.get("date"));
        }
    }

    @Then("^the answer contains (\\d+) result(?:s)?$")
    public void the_size_is_good(int expected) {
        int number = answer.length();
        assertEquals(expected, number);
    }
}

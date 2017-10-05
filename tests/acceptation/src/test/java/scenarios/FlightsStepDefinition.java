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

import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


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

    @Given("^a (direct|non-direct) flight from (.*) to (.*) for (.+) euros the (\\d\\d/\\d\\d/\\d\\d\\d\\d)-(\\d\\d:\\d\\d) duration (\\d+) min$")
    public void upload_preregistered_citizen(String direct, String from, String to, double price, String date, String hour, int duration) throws ParseException {
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

    @When("^the RETRIEVE message is sent with option order by (.*)$")
    public void call_with_order_option(String attr) {
        JSONObject request = createRetrieveRequest();
        request.put("order-by", attr);
        answer = call(request);
        assertNotNull(answer);
    }

    @When("^the RETRIEVE message is sent with option only direct$")
    public void call_only_direct(){
        JSONObject request = createRetrieveRequest();
        request.put("direct", true);
        answer = call(request);
        assertNotNull(answer);
    }

    @When("^we require all flights with a (\\d+) max duration$")
    public void call_with_max_duration(int max){
        JSONObject request = createRetrieveRequest();
        JSONObject maxObject = new JSONObject();
        maxObject.put("duration", 120);
        request.put("max", maxObject);
        answer = call(request);
        assertNotNull(answer);
    }

    @Then("^all the results are less than (\\d+) min$")
    public void the_results_have_max_duration(int max){
        for (int i = 0; i < answer.length(); i++) {
            JSONObject o = answer.getJSONObject(i);
            assertTrue(o.getInt("duration") <= max);
        }
    }

    @Then("^all the results only direct$")
    public void the_result_are_only_direct(){
        for (int i = 0; i < answer.length(); i++) {
            JSONObject o = answer.getJSONObject(i);
            assertTrue(o.getBoolean("direct"));
        }
    }

    @Then("^all the results are sorted by (.*)$")
    public void the_results_are_sorted(String attr) {
        for (int i = 0; i < answer.length() - 1; i++) {
            Object o1 = answer.getJSONObject(i).get(attr);
            Object o2 = answer.getJSONObject(i + 1).get(attr);
            if (o1 instanceof String && o2 instanceof String) {
                assertTrue(((String) o1).compareToIgnoreCase((String)o2) <= 0);
            } else if (o1 instanceof Double && o2 instanceof Double){
                assertTrue(((Double) o1).compareTo((Double) o2) <= 0);
            } else {
                assertTrue(((Integer) o1).compareTo((Integer) o2) <= 0);
            }

        }
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

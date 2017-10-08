package scenarios;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import manager.data.StoragePendings;
import manager.data.StorageRefused;
import manager.data.StorageValidated;
import org.apache.cxf.jaxrs.client.WebClient;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;

import static org.junit.Assert.assertEquals;

public class TravelsStepDefinition {

    private static final String PLANNER = "TravelPlannerService";
    private static final String CONFIRM = "TravelAcceptationService";

    private String host = "localhost";
    private int port = 8080;

    private JSONArray arrayAnswer;
    private String uuid;

    private String callPost(String route, JSONObject post) {
        String raw =
                WebClient.create("http://" + host + ":" + port + "/service-travel-manager/" + route)
                        .accept(MediaType.TEXT_PLAIN)
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .post(post.toString(), String.class);
        return raw;
    }

    private String callGet(String route) {
        String raw =
                WebClient.create("http://" + host + ":" + port + "/service-travel-manager/" + route)
                        .accept(MediaType.APPLICATION_JSON_TYPE)
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .get(String.class);
        return raw;
    }

    @Given("^the service deployed on (.*):(\\d+)$")
    public void set_clean_registr(String host, int port) {
        this.host = host;
        this.port = port;
        StoragePendings.purge();
        StorageRefused.purge();
        StorageValidated.purge();
    }

    @When("^retrieving pending requests$")
    public void pendings() {
        arrayAnswer = new JSONArray(callGet(PLANNER + "/request"));
    }

    @When("^retrieving confirm requests$")
    public void confirms() {
        arrayAnswer = new JSONArray(callGet(CONFIRM + "/validatedRequest"));
    }

    @Then("^the answer have (\\d+) result(?:s)?$")
    public void the_size_is_ok(int expected) {
        int number = arrayAnswer.length();
        assertEquals(expected, number);
    }

    @When("^propose a travel by (.*)$")
    public void propose(String email) {
        JSONObject o = new JSONObject();
        o.put("email", email);
        o.put("hotels", new JSONArray().put("hotel1").put("hotel2"));
        o.put("flights", new JSONArray().put("flight1").put("flight2"));
        uuid = callPost(PLANNER + "/request", o);
    }

    @When("^retrieving pending requests by previous uid$")
    public void get_pending_uuid(){
        arrayAnswer = new JSONArray().put(callGet(PLANNER + "/request/uid/" + uuid));
    }

    @And("^(.*) have one pending request$")
    public void get_by_email(String email) {
        arrayAnswer = new JSONArray(callGet(PLANNER + "/request/email/" + email));
    }

    @When("^confirming this travel$")
    public void confirm (){
        callPost(CONFIRM + "/validatedRequest/uid/" + uuid, new JSONObject());
    }
}

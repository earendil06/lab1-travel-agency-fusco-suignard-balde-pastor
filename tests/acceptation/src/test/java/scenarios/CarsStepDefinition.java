package scenarios;

import carplanner.planner.service.CarPlanner;
import carplanner.planner.service.CarPlannerService;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import javax.xml.ws.BindingProvider;
import java.net.URL;

import static junit.framework.TestCase.assertTrue;

public class CarsStepDefinition {

    private String host = "host";
    private int port = 8080;
    private String answer = "";

    @Given("^an empty registry deployed on (.*):(\\d+)$")
    public void select_host_and_port(String host, int port) { this.host = host; this.port = port; }

    @When("^the getAllCars message is sent$")
    public void theGetAllCarsMessageIsSent() throws Throwable {
        answer = getWS().getAllCars();
    }


    private CarPlanner getWS() {
        URL wsdl = CarsStepDefinition.class.getResource("CarPlannerService.wsdl");
        CarPlannerService factory = new CarPlannerService(wsdl);
        CarPlanner ws = factory.getCarPlannerPort();
        String address = "http://"+this.host+":"+this.port+"/service-car-planner/CarPlannerService";
        ((BindingProvider) ws).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);
        return ws;
    }

    @Then("^the answer is not empty$")
    public void theAnswerIsNotEmpty() throws Throwable {
        assertTrue(answer.length() != 0);
    }

    @When("^the getCarAtPlace message is sent$")
    public void theGetCarAtPlaceMessageIsSent() throws Throwable {
        answer = getWS().getCarByPlace("Paris", 4);
    }
}

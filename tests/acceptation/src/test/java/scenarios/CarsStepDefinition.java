package scenarios;

import carplanner.planner.service.CarPlanner;
import carplanner.planner.service.CarPlannerService;

import javax.xml.ws.BindingProvider;
import java.net.URL;

public class CarsStepDefinition {


    private String host = "host";
    private int port = 8080;



    private CarPlanner getWS() {
        URL wsdl = CarsStepDefinition.class.getResource("CarPlannerService.wsdl");
        CarPlannerService factory = new CarPlannerService(wsdl);
        CarPlanner ws = factory.getCarPlannerPort();
        String address = "http://"+this.host+":"+this.port+"/service-car-planner/CarPlannerService";
        ((BindingProvider) ws).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);
        return ws;
    }

}

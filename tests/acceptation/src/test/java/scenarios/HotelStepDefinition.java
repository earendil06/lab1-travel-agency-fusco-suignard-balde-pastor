package scenarios;

import hotelplanner.planner.service.HotelPlanner;
import hotelplanner.planner.service.HotelPlannerService;

import javax.xml.ws.BindingProvider;
import java.net.URL;

public class HotelStepDefinition {

    private String host = "host";
    private int port = 8080;



    private HotelPlanner getWS() {
        URL wsdl = HotelStepDefinition.class.getResource("HotelPlannerService.wsdl");
        HotelPlannerService factory = new HotelPlannerService(wsdl);
        HotelPlanner ws = factory.getHotelPlannerPort();
        String address = "http://"+this.host+":"+this.port+"/service-hotel-planner/HotelPlannerService";
        ((BindingProvider) ws).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);
        return ws;
    }

}

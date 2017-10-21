package fr.unice.groupe4.flows;

import fr.unice.groupe4.flows.data.Hotel;
import fr.unice.groupe4.flows.utils.HotelHelper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import javax.xml.transform.Source;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.List;

import static fr.unice.groupe4.flows.utils.Endpoints.COMPARE_HOTEL_ENDPOINT;
import static fr.unice.groupe4.flows.utils.Endpoints.HOTEL_ENDPOINT;

public class ExternalService extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from(COMPARE_HOTEL_ENDPOINT)
                .routeId("retrieve hotels from services")
                .log("START HOTEL ENDPOINT")
                .bean(HotelHelper.class, "buildGetHotelForTravel(${body})")
                .inOut(HOTEL_ENDPOINT)
                //.process(result2Hotel)
                .log("AFTER HOTEL SERVICE: " + body());
    }

    private static Processor result2Hotel = (Exchange exc) -> {
        XPath xpath = XPathFactory.newInstance().newXPath();
        Source response = (Source) exc.getIn().getBody();
        List<Hotel> result = new ArrayList<>(exc.getProperty("hotel_planner_result", List.class)); //todo take less expensive
        System.out.println("LIST: " + result);
        exc.getIn().setBody(result);
    };
}

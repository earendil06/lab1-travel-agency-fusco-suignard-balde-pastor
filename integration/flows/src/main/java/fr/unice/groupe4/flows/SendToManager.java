package fr.unice.groupe4.flows;

import com.google.gson.Gson;
import fr.unice.groupe4.flows.data.traveldata.ManagerTravelRequest;
import fr.unice.groupe4.flows.data.traveldata.TravelRequest;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import java.io.IOException;

import static fr.unice.groupe4.flows.utils.Endpoints.*;

public class SendToManager extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from(RESULT_POOL)
                .routeId("Result Queue")
                .routeDescription("Queue receiving the final request and sending it to a manager")
                .loadBalance().failover(IOException.class)
                .to(MANAGER1_ENDPOINT, MANAGER2_ENDPOINT);

        from(MANAGER1_ENDPOINT)
                .routeId("manager1")
                .routeDescription("send the request to the first instance of the manager service")
                .log("MANAGER1")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .process(SendToManager::toManager)
                .inOut(TRAVEL_ENDPOINT)
                .log("MANAGER1 new ID in Travel Service is ${body}")
        ;

        from(MANAGER2_ENDPOINT)
                .routeId("manager2")
                .routeDescription("send the request to the second instance of the manager service")
                .onException(IOException.class)
                    .log("LAST SERVICE UNREACHABLE")
                    .maximumRedeliveries(2)
                    .redeliveryDelay(500)
                    .handled(true)
                    .process(SendToManager::toTravel)
                    .to(RETRY_MESSAGE_QUEUE)
                .end()
                .log("MANAGER2")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .process(SendToManager::toManager)
                .inOut(TRAVEL_ENDPOINT2)
                .log("MANAGER2 new ID in Travel Service is ${body}")
        ;

        from(TIMER_ENDPOINT)
                .routeId("retry timer")
                .routeDescription("route that wakes every minute to retry failed request to the manager services")
                .log("RETRYING MISSED RESULT")
                .to(RETRY_ENDPOINT)
        ;

        from(RETRY_ENDPOINT)
                .process(exchange -> {
                    ConsumerTemplate consumerTemplate = getContext().createConsumerTemplate();
                    Object receive = consumerTemplate.receiveBody(RETRY_MESSAGE_QUEUE, 100);
                    consumerTemplate.stop();
                    exchange.getIn().setBody(receive);
                })
                .choice()
                    .when(body().isNotEqualTo(null))
                        .log("RETRYING A MISSED REQUEST")
                        .to(RESULT_POOL)
                        .to(RETRY_ENDPOINT)
                    .otherwise()
                        .log("NOTHING TO RETRY")
                .end()
        ;
    }

    private static void toManager(Exchange exchange) {
        Gson gson = new Gson();
        String s = exchange.getIn().getBody(String.class);
        TravelRequest travelRequest = gson.fromJson(s, TravelRequest.class);
        ManagerTravelRequest managerTravelRequest = new ManagerTravelRequest(travelRequest);
        String json = gson.toJson(managerTravelRequest);
        exchange.getIn().setBody(json);
    }

    private static void toTravel(Exchange exchange) {
        Gson gson = new Gson();
        String s = exchange.getIn().getBody(String.class);
        ManagerTravelRequest managerTravelRequest = gson.fromJson(s, ManagerTravelRequest.class);
        TravelRequest travelRequest = new TravelRequest(managerTravelRequest);
        String json = gson.toJson(travelRequest);
        exchange.getIn().setBody(json);
    }

}

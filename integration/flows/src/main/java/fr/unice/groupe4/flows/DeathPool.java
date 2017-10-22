package fr.unice.groupe4.flows;

import org.apache.camel.builder.RouteBuilder;

import static fr.unice.groupe4.flows.utils.Endpoints.DEATH_POOL;

public class DeathPool extends RouteBuilder {
    private static final int REDELIVERIES = 2;

    @Override
    public void configure() throws Exception {

        errorHandler(
                deadLetterChannel(DEATH_POOL)
                        .useOriginalMessage()
                        .maximumRedeliveries(REDELIVERIES)
                        .redeliveryDelay(500)
        );

    }
}

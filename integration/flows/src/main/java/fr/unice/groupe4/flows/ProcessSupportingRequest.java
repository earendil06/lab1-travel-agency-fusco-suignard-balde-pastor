package fr.unice.groupe4.flows;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.unice.groupe4.flows.utils.SupportingRequestSplitter;
import fr.unice.groupe4.flows.utils.ExpenseRequestSplitter;

import static fr.unice.groupe4.flows.utils.Endpoints.*;

//import static fr.unice.groupe4.flows.utils.Endpoints.MAIL_INPUT;


public class ProcessSupportingRequest extends RouteBuilder{
    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(5);

    @Override
    public void configure() throws Exception {
        from(MAIL_INPUT)
                .log("***************************test *********************************")
                .log("Starting submit mail for living expenses")
                .routeId("LivingExpenses")
                .log("input is " + body())
                .unmarshal().json(JsonLibrary.Jackson, Map.class)
                .split().method(SupportingRequestSplitter.class, "split")
                    .choice()
                        .when(header("type").isEqualTo("livingExpenses"))
                        .log("livingExpenses is " + body())
                        .when(header("type").isEqualTo("employer"))
                        .log("employer id is " + body())
                        .when(header("type").isEqualTo("travel"))
                        .log("travel id is " + body())
                        .when(header("type").isEqualTo("expenses"))
                        .log("all expenses are " + body())
                        .split(body())
                        .split().method(ExpenseRequestSplitter.class,"split")
                            .choice()
                            .when(header("type").isEqualTo("Type"))
                            .log("The Type of Expenses is " + body())
                            .when(header("type").isEqualTo("Motif"))
                            .log("The Motif of this Expense is " + body())
                            .when(header("type").isEqualTo("Date"))
                            .log("The Date is " + body())
                            .when(header("type").isEqualTo("Price"))
                            .log("The Price is " + body() + "euros")

        ;

    }
}

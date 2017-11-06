package fr.unice.groupe4.flows;

import fr.unice.groupe4.flows.data.ExpenseRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fr.unice.groupe4.flows.utils.Endpoints.*;

//import static fr.unice.groupe4.flows.utils.Endpoints.MAIL_INPUT;


public class ProcessSupportingRequest extends RouteBuilder{
    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(5);

    @Override
    public void configure() throws Exception {
        /*from(MAIL_INPUT)
                .routeId("mail-to-refund-expenses")
                .routeDescription("Loads a json file containing the expense for travel and proces Contents")
                .unmarshal().json(JsonLibrary.Jackson, Map.class)
                //.log("le contenu est " + body())
                //.log("${body[type]}")
                .choice()
                .when(simple("${body[type]} =~ 'restaurant'"))
                    .log("this expenses is remboursed")
                    .to(EXPENSE_TO_REFUND)
                .when(simple("${body[type]} =~ 'voiture'"))
                    .log("this expenses is remboursed")
                    .to(EXPENSE_TO_REFUND)
                .otherwise()
                    .log("this expenses is not remboursed")
                    .to(EXPENSE_NOT_REFUND)
               .end()
               //.aggregate(constant(true), mergeExpense)



        ;

        from(EXPENSE_TO_REFUND)
            .routeId("Generate-mail-report-to-refund")
            .routeDescription("Generate a report for refund all expenses for on travel")
            .log("This expense is supported by the company")
            .log("Type : ${body[type]}   ; Price : ${body[price]} ")
            .aggregate(constant(true), mergeExpense).completionTimeout(10000)
            .log("************* After Agregate ****************************")
            .log("${body}")
            .to("direct:test")

            //.setProperty("report", simple("${body}"))

            ;
        from("direct:test")
                .log("Direct test ***********************")
                .log("${body}")
                ;

        from(EXPENSE_NOT_REFUND)
                .routeId("Generate-mail-report-for-not-refund")
                .routeDescription("Generate a report to explain why this expense is not refund")
                .log("Type : ${body[type]}   ; Price : ${body[price]} ")
                //.setProperty("report", simple("${body}"))

        ;


        from(MAIL_INPUT)
                .routeId("Expense Json to object Expense")
                .routeDescription("Loads a json file containing the expense for travel and proces Contents")
                .unmarshal().json(JsonLibrary.Jackson, Map.class)
                .log("le contenu est " + body())

        ;
        */
    }

    private static AggregationStrategy mergeExpense = new AggregationStrategy() {
        @Override
        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
            Message newIn = newExchange.getIn();
            Object newBody = newIn.getBody();
            ArrayList list = null;
            if (oldExchange == null) {
                list = new ArrayList();
                list.add(newBody);
                newIn.setBody(list);
                return newExchange;
            } else {
                Message in = oldExchange.getIn();
                list = in.getBody(ArrayList.class);
                list.add(newBody);
                return oldExchange;
            }
        }
    };




}

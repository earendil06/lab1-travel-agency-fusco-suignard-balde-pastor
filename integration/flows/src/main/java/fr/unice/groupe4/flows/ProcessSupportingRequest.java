package fr.unice.groupe4.flows;

import com.google.gson.Gson;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.unice.groupe4.flows.data.SupportingRequest;
import fr.unice.groupe4.flows.data.ExpenseRequest;

import static fr.unice.groupe4.flows.utils.Endpoints.*;

//import static fr.unice.groupe4.flows.utils.Endpoints.MAIL_INPUT;


public class ProcessSupportingRequest extends RouteBuilder{
    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(5);

    @Override
    public void configure() throws Exception {
        from(MAIL_INPUT)
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


        ;

        from(EXPENSE_TO_REFUND)
            .routeId("Generate-mail-report-to-refund")
            .routeDescription("Generate a report for refund all expenses for on travel")
            .log("This expense is supported by the company")
            .log("Type : ${body[type]}   ; Price : ${body[price]} ")
            //.setProperty("report", simple("${body}"))

            ;
        from(EXPENSE_NOT_REFUND)
                .routeId("Generate-mail-report-for-not-refund")
                .routeDescription("Generate a report to explain why this expense is not refund")
                .log("Type : ${body[type]}   ; Price : ${body[price]} ")
                //.setProperty("report", simple("${body}"))

        ;

    }


    private static AggregationStrategy mergeExpense = new AggregationStrategy() {
        @Override
        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
            Message newIn = newExchange.getIn();
            if (oldExchange == null) {
                ExpenseRequest expenseRequest = new ExpenseRequest();
                add(expenseRequest, newIn);
                newIn.setBody(expenseRequest);
                return newExchange;
            } else {
                ExpenseRequest old = oldExchange.getIn().getBody(ExpenseRequest.class);
                add(old, newIn);
                oldExchange.getIn().setBody(old);
                //System.out.println("MERGED " + old);
                if (old != null &&
                        old.getType() != null &&
                        old.getMotif() != null &&
                        old.getPrice() != null) {
                    //old.getFlight() != null) {
                    System.out.println("EVERYTHING Expenses IS AGGREGATED\n" + old);
                }
                return oldExchange;
            }
        }

        private void add(ExpenseRequest old, Object o) {
            Message msg = ((Message) o);
            Gson gson = new Gson();
            switch (msg.getHeader("type").toString()) {
                case "type":
                    String type = msg.getBody(String.class);
                    old.setType(type);
                    break;
                case "motif":
                    String motif = msg.getBody(String.class);
                    old.setMotif(motif);
                    break;
                case "price":
                    String price = msg.getBody(String.class);
                    old.setPrice(price);
                    break;

            }
        }
    };

    private static AggregationStrategy merge = new AggregationStrategy() {
        @Override
        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
            Message newIn = newExchange.getIn();
            if (oldExchange == null) {
                SupportingRequest supportingRequest = new SupportingRequest();
                add(supportingRequest, newIn);
                newIn.setBody(supportingRequest);
                return newExchange;
            } else {
                SupportingRequest old = oldExchange.getIn().getBody(SupportingRequest.class);
                add(old, newIn);
                oldExchange.getIn().setBody(old);
                //System.out.println("MERGED " + old);
                if (old != null &&
                        old.getLivingExpenses() != null &&
                        old.getEmployer() != null &&
                        old.getTravel() != null) {
                    //old.getFlight() != null) {
                    System.out.println("EVERYTHING IS AGGREGATED\n" + old);
                }
                return oldExchange;
            }
        }

        private void add(SupportingRequest old, Object o) {
            Message msg = ((Message) o);
            Gson gson = new Gson();
            switch (msg.getHeader("type").toString()) {
                case "livingExpenses":
                    String livingExpenses = msg.getBody(String.class);
                    old.setLivingExpenses(livingExpenses);
                    break;
                case "employer":
                    String employer = msg.getBody(String.class);
                    old.setEmployer(employer);
                    break;
                case "travel":
                    String travel = msg.getBody(String.class);
                    old.setTravel(travel);
                    break;
            }
        }


    };
}

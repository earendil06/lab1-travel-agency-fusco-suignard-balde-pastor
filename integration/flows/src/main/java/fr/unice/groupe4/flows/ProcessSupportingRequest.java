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

import fr.unice.groupe4.flows.utils.SupportingRequestSplitter;
import fr.unice.groupe4.flows.utils.ExpenseRequestSplitter;
import fr.unice.groupe4.flows.data.SupportingRequest;
import fr.unice.groupe4.flows.data.ExpenseRequest;

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
                //.parallelProcessing().executorService(WORKERS)
                    .choice()
                    .when(header("type").isEqualTo("livingExpenses"))
                    .log("livingExpenses is " + body())
                    .when(header("type").isEqualTo("employer"))
                    .log("employer id is " + body())
                    .when(header("type").isEqualTo("travel"))
                    .log("travel id is " + body())
                    .when(header("type").isEqualTo("expenses"))
                    .split(body())
                        .choice()
                        .when(simple("${body[Type]} =~ 'restaurent'"))
                        .log("this expenses is remboursed")
                        .log("{Type : ${body[Type]} Price : ${body[Price]} }")
                        .when(simple("${body[Type]} =~ 'transport'"))
                        .log("this expenses is remboursed")
                        .log("{Type : ${body[Type]} Price : ${body[Price]} }")
                        .when(simple("${body[Type]} != 'restaurent' && ${body[Type]} != 'transport'"))
                        .log("This expenses is not Rembouresed")
                        /*
                        aggregate the expenses object
                        .aggregate(constant(true), mergeExpense)
                        .completionPredicate(exchange -> {
                         ExpenseRequest expenseRequest = exchange.getIn().getBody(ExpenseRequest.class);
                                 return expenseRequest != null &&
                                    expenseRequest.getType() != null &&
                                    expenseRequest.getMotif() != null &&
                                    expenseRequest.getPrice() != null;
                                 })
                        .log("############### after aggregation expense " + body()+ "#######################")
                    .aggregate(constant(true), merge)
                    .completionPredicate(exchange -> {
                        SupportingRequest supportingRequest = exchange.getIn().getBody(SupportingRequest.class);
                        return supportingRequest != null &&
                            supportingRequest.getLivingExpenses() != null &&
                            supportingRequest.getEmployer() != null &&
                            supportingRequest.getTravel() != null;
                    })
                    .log("############### after aggregation " + body()+ "#######################")

                    */

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

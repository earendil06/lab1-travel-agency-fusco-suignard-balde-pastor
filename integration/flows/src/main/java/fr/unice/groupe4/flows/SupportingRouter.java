package fr.unice.groupe4.flows;

import com.google.gson.Gson;
import fr.unice.groupe4.flows.data.Expense;
import fr.unice.groupe4.flows.data.SupportingTravel;
import fr.unice.groupe4.flows.utils.RefundReport;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fr.unice.groupe4.flows.utils.Endpoints.*;

//import static fr.unice.groupe4.flows.utils.Endpoints.MAIL_INPUT;


public class SupportingRouter extends RouteBuilder {
    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(5);

    @Override
    public void configure() throws Exception {
        from(MAIL_INPUT)
                .routeId("Expense Json to object Expense")
                .routeDescription("Loads a json file containing the expense for travel and proces Contents")
                .unmarshal().json(JsonLibrary.Jackson, Map.class)
                .choice()
                    .when(simple("${body[type]} =~ 'restaurant'"))
                        .log("this expenses is remboursed")
                        .to(EXPENSE_TO_REFUND)
                    .when(simple("${body[type]} =~ 'car'"))
                        .log("this expenses is remboursed")
                        .to(EXPENSE_TO_REFUND)
                    .otherwise()
                        .log("this expenses is not remboursed")
                        .to(EXPENSE_NOT_REFUND)
                .end()

        ;

        from(EXPENSE_TO_REFUND)
                .routeId("Generate-mail-report-to-refund")
                .routeDescription("Generate a report for refund all expenses for on travel")
                .setProperty("input", simple("${body}"))
                .process(exchange -> {
                    String json = exchange.getIn().getBody(String.class);
                    InputStream stream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8.name()));
                    exchange.getIn().setBody(stream);
                })
                .recipientList(simple(ARCHIVE + "${exchangeProperty[input[name]]}${exchangeProperty[input[id]]}")).end()
                .setBody(simple("${exchangeProperty[input]}"))
                .removeProperty("input")
                .aggregate(constant(true), mergeExpense).completionTimeout(500)
                .log("************* After Agregate ****************************")
                .to(COMPARE_TOTALPRICE_TO_CITY_PRICEDAY)

        //.setProperty("report", simple("${body}"))

        ;
        from(COMPARE_TOTALPRICE_TO_CITY_PRICEDAY)
                .routeId("Compare-PriceTotal-to-City-Price-Day")
                .log("${body.totalPrice}")
                .inOut(GET_PRICE_BY_CITY)
                .choice()
                    .when(exchangeProperty("priceByDay").isGreaterThanOrEqualTo(simple("${body.totalPrice}")))
                        .log("votre remboursement va etre effectuer ")
                        .setProperty("report-refund", simple("${body}"))
                        .bean(RefundReport.class,"write(${body})")
                        .process(exchange -> {
                                String json = exchange.getIn().getBody(String.class);
                                InputStream stream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8.name()));
                                exchange.getIn().setBody(stream);
                        })
                        .recipientList(simple(GENERATE_MAIL_REPORT_REFUND + "${exchangeProperty[report-refund.id]}")).end()
                        .setBody(simple("${exchangeProperty[report-refund]}"))
                        .removeProperty("report-refund")
                    .otherwise()
                        .log("vous depassez le seuil autoriser de ${exchangeProperty[priceByDay]}," +
                                " veuillez nous fournir des justificatifs ")
                .end()
                .removeProperty("priceByDay")
                .log("${body}")
        ;

        from(GET_PRICE_BY_CITY)
                .routeId("direct:getPriceByCity")
                .choice()
                    .when(simple("${body.city} == 'Nice'"))
                        .setProperty("priceByDay", simple("950"))
                    .when(simple("${body.city} == 'Berlin'"))
                        .setProperty("priceByDay", simple("1050"))
                    .otherwise()
                        .setProperty("priceByDay", simple("1000"))
                .end()
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
            Gson gson = new Gson();
            String newIn = newExchange.getIn().getBody(String.class);
            System.out.println("le message est  " + newIn);
            Expense newExp = gson.fromJson(newIn, Expense.class);
            newExchange.getIn().setBody(newExp);
            //Expense newExp = newIn.getBody(Expense.class);
            System.out.println("le new Expense est " + newExp);
            SupportingTravel supp = null;
            if (oldExchange == null) {
                supp = new SupportingTravel();
                List currentExpenses = new ArrayList();
                currentExpenses.add(newExp);
                supp.setExpenses(currentExpenses);
                supp.setId("EXP"+System.currentTimeMillis());
                newExchange.getIn().setBody(supp);
                System.out.println("Le supporting est " + supp);
                supp.setCity(newExp.getCity());
                return newExchange;
            } else {
                SupportingTravel supportingTravel = oldExchange.getIn().getBody(SupportingTravel.class);
                Expense newExpense = newExchange.getIn().getBody(Expense.class);


                supportingTravel.getExpenses().add(newExpense);


                System.out.println("Le Supporting avant le add est : " + supportingTravel.getTotalPrice());
                double totalPrice =  supportingTravel.getTotalPrice() + newExpense.getPrice();

                supportingTravel.setTotalPrice(totalPrice);
                return oldExchange;
            }
            //return oldExchange;
        }
    };

}

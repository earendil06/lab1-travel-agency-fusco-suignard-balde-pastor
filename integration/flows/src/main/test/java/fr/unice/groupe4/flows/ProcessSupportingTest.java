package fr.unice.groupe4.flows;

import com.google.gson.Gson;
import fr.unice.groupe4.flows.data.Expense;
import fr.unice.groupe4.flows.data.SupportingTravel;
import org.apache.camel.Exchange;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static fr.unice.groupe4.flows.utils.Endpoints.*;

/**
 * General tests, normal cases and special input cases.
 */

public class ProcessSupportingTest extends fr.unice.groupe4.flows.ActiveMQTest {



  @Override
  public String isMockEndpointsAndSkip() {

    return  EXPENSE_TO_REFUND +
          "|" + EXPENSE_NOT_REFUND +
          "|" + MAIL_INPUT;
  }

  @Override
  public String isMockEndpoints() {
    return COMPARE_TOTALPRICE_TO_CITY_PRICEDAY
       ;
  }

  List<Expense> expenses;
  List<Expense> expensesNotRefund;

  @Before
  public void buildTestRequest(){
    expenses = new ArrayList<>();
    expenses.add(new Expense("1","midi","Restaurant"));
    expenses.get(0).setPrice(150);
    expenses.add(new Expense("2","taxi","Deplacement"));
    expenses.get(1).setPrice(100);
    expensesNotRefund = new ArrayList<>();
    expensesNotRefund.add(new Expense("1","midi","other"));
    expensesNotRefund.get(0).setPrice(150);
    expensesNotRefund.add(new Expense("2","taxi","other"));
    expensesNotRefund.get(1).setPrice(100);

  }



  SupportingTravel supportingTravel;
  @Before
  public void builFinTesRequest(){
    supportingTravel = new SupportingTravel();
    List<Expense> exp = new ArrayList<>();
    exp.add(new Expense("1","midi","Restaurant"));
    exp.get(0).setPrice(150);
    exp.add(new Expense("2","taxi","Deplacement"));
    exp.get(1).setPrice(100);
    double totalPrice = exp.get(0).getPrice() + exp.get(1).getPrice();
    supportingTravel.setExpenses(exp);
    supportingTravel.setCity("Nice");
    supportingTravel.setTotalPrice(totalPrice);

  }


  @Before
  public void initMocks() {
    resetMocks();

    mock(MAIL_INPUT).whenAnyExchangeReceived((Exchange exc) -> {
      String expense = "{\n" +
        "  \"id\" : \"1\",\n" +
        "  \"name\" : \"taxi\",\n" +
        "  \"type\" : \"deplacement\",\n" +
        "  \"price\" : 100,\n" +
        "  \"city\" : \"Nice\"\n" +
        "}";
      exc.getIn().setBody(expense);
    });

    mock(EXPENSE_TO_REFUND).whenAnyExchangeReceived((Exchange exc) -> {
      String exp = "{\n" +
        "  \"id\" : \"1\",\n" +
        "  \"name\" : \"taxi\",\n" +
        "  \"type\" : \"deplacement\",\n" +
        "  \"price\" : 100,\n" +
        "  \"city\" : \"Nice\"\n" +
        "}";
      exc.getIn().setBody(exp);
    });

    mock(COMPARE_TOTALPRICE_TO_CITY_PRICEDAY).whenAnyExchangeReceived((Exchange exc) -> {
      boolean exp = false;
    });

  }
  public boolean verifiedType(List<Expense> exp){
    boolean res = false;
    for(int i = 0; i < exp.size(); i++){
      if(exp.get(i).getType().equalsIgnoreCase("Restaurant") || exp.get(i).getType().equalsIgnoreCase("Deplacement")){
        res = true;
      }else{
        res = false;
      }
    }
    return res;
  }
  @Test
  public void testTheFlowOfSupportingExpenses() throws Exception{
    mock(MAIL_INPUT).expectedMessageCount(1);
    //mock(COMPARE_TOTALPRICE_TO_CITY_PRICEDAY).expectedMessageCount(1);
    mock(EXPENSE_TO_REFUND).expectedMessageCount(1);

    Gson gson = new Gson();
    String expensesRequestJson = gson.toJson(expenses);
    template.sendBody(MAIL_INPUT, expensesRequestJson);
    template.requestBody(EXPENSE_TO_REFUND,gson.toJson(supportingTravel.getExpenses().get(0)));
    boolean expSuported = verifiedType(expenses);
    assertEquals(expSuported,true);
    assertMockEndpointsSatisfied(10, TimeUnit.SECONDS);
  }

  @Test
  public void testTheFlowOfNotSupportingExpenses() throws Exception{
    mock(MAIL_INPUT).expectedMessageCount(1);
    mock(EXPENSE_NOT_REFUND).expectedMessageCount(1);

    Gson gson = new Gson();
    String expensesRequestJson = gson.toJson(expenses);
    template.sendBody(MAIL_INPUT, expensesRequestJson);
    template.requestBody(EXPENSE_NOT_REFUND,gson.toJson(supportingTravel.getExpenses().get(0)));
    double pricesRequest = expenses.get(0).getPrice()+ expenses.get(1).getPrice();
    assertEquals((int)pricesRequest,(int)supportingTravel.getTotalPrice());
    assertEquals(expensesRequestJson,gson.toJson(supportingTravel.getExpenses()));

    assertMockEndpointsSatisfied(10, TimeUnit.SECONDS);
  }


  @Test
  public void testTheFlowOfSupportingExpensePrices() throws Exception{
    mock(MAIL_INPUT).expectedMessageCount(1);
    mock(EXPENSE_TO_REFUND).expectedMessageCount(1);

    Gson gson = new Gson();
    String expensesRequestJson = gson.toJson(expenses);
    template.sendBody(MAIL_INPUT, expensesRequestJson);
    template.requestBody(EXPENSE_TO_REFUND,gson.toJson(supportingTravel.getExpenses().get(0)));
    boolean expSuported = verifiedType(expenses);
    assertEquals(expSuported,true);
    double pricesRequest = expenses.get(0).getPrice()+ expenses.get(1).getPrice();
    assertEquals((int)pricesRequest,(int)supportingTravel.getTotalPrice());
    assertEquals(expensesRequestJson,gson.toJson(supportingTravel.getExpenses()));
    assertMockEndpointsSatisfied(10, TimeUnit.SECONDS);
  }

  @Test
  public void testTheComparePricesTotal() throws Exception{
    mock(EXPENSE_TO_REFUND).expectedMessageCount(1);
    mock(COMPARE_TOTALPRICE_TO_CITY_PRICEDAY).expectedMessageCount(1);
    Gson gson = new Gson();
    template.sendBody(EXPENSE_TO_REFUND,gson.toJson(supportingTravel));
    boolean expPerDay = (supportingTravel.getTotalPrice() <= 1000 && supportingTravel.getCity() == "Nice");
    assertEquals(expPerDay,true);


  }

  @Test
  public void testExpenseNotRefund() throws Exception{
    mock(MAIL_INPUT).expectedMessageCount(1);
    mock(EXPENSE_TO_REFUND).expectedMessageCount(0);
    mock(EXPENSE_NOT_REFUND).expectedMessageCount(1);

    Gson gson = new Gson();
    String expensesRequestJson = gson.toJson(expenses);
    template.sendBody(MAIL_INPUT, expensesRequestJson);
    template.requestBody(EXPENSE_NOT_REFUND,gson.toJson(supportingTravel.getExpenses().get(0)));

    assertMockEndpointsSatisfied();
  }

  @Test
  public void testExpenseToRefund() throws Exception{
    mock(MAIL_INPUT).expectedMessageCount(1);
    mock(EXPENSE_TO_REFUND).expectedMessageCount(1);
    mock(EXPENSE_NOT_REFUND).expectedMessageCount(0);

    Gson gson = new Gson();
    String expensesRequestJson = gson.toJson(expenses);
    template.sendBody(MAIL_INPUT, expensesRequestJson);
    template.requestBody(EXPENSE_TO_REFUND,gson.toJson(supportingTravel.getExpenses().get(0)));

    assertMockEndpointsSatisfied();
  }


}

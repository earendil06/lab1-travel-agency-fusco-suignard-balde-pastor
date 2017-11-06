package fr.unice.groupe4.flows.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExpenseRequest {
    @JsonProperty("livingExpenses") private String livingExpenses;
    @JsonProperty("employer") private String employer;
    @JsonProperty("travel") private String travel;
    @JsonProperty("type") private String expenses;
    @JsonProperty("prices") private String prices;


    public ExpenseRequest() {
    }

    public String getLivingExpenses() {
        return livingExpenses;
    }

    public void setLivingExpenses(String livingExpenses) {
        this.livingExpenses = livingExpenses;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getTravel() {
        return travel;
    }

    public void setTravel(String travel) {
        this.travel = travel;
    }

    public String getExpenses() {
        return expenses;
    }

    public void setExpenses(String expenses) {
        this.expenses = expenses;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    @Override
    public String toString() {
        return "ExpenseRequest{" +
                "livingExpenses='" + livingExpenses + '\'' +
                ", employer='" + employer + '\'' +
                ", travel='" + travel + '\'' +
                ", expenses='" + expenses + '\'' +
                ", prices='" + prices + '\'' +
                '}';
    }
}

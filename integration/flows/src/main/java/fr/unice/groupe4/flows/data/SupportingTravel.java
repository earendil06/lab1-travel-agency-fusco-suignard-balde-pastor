package fr.unice.groupe4.flows.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SupportingTravel {
    @JsonProperty("id") private String id;
    @JsonProperty("expenses") private List expenses;
    @JsonProperty("totalPrice") private double totalPrice;
    @JsonProperty("city") private String city;

    public SupportingTravel() {
    }

    public SupportingTravel(String id, List expenses, double totalPrice) {
        this.id = id;
        this.expenses = expenses;
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List getExpenses() {
        return expenses;
    }

    public void setExpenses(List expenses) {
        this.expenses = expenses;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void add(Expense exp){
        expenses.add(exp);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "SupportingTravel{" +
                "id='" + id + '\'' +
                ", expenses=" + expenses +
                ", city=" + city +
                ", totalPrice=" + totalPrice +
                '}';
    }
}

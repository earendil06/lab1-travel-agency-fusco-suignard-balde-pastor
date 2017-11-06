package fr.unice.groupe4.flows.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Expense {
    @JsonProperty("id") private String id;
    @JsonProperty("name") private String name;
    @JsonProperty("type")  private String type;
    @JsonProperty("price") private double price;

    public Expense(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
        //this.price = price;
    }

    public Expense() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';
    }
}

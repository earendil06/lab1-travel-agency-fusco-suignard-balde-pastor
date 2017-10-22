package fr.unice.groupe4.flows.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExpenseRequest {
    @JsonProperty("type") private String type;
    @JsonProperty("motif") private String motif;
    @JsonProperty("price") private String price;


    public ExpenseRequest() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ExpenseRequest{" +
                "type='" + type + '\'' +
                ", motif='" + motif + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}

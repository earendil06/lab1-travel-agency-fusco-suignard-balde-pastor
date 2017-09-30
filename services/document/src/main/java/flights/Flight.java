package flights;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

import java.util.Date;

public class Flight {

    private String from;
    private String to;
    private Date dateFrom;
    private Date dateTo;
    private double price;
    private boolean directFlight;

    @MongoObjectId
    String id;

    public Flight() {}

    public Flight(JSONObject data) {
        this.from = data.getString("from");
        this.to = data.getString("to");
//        this.dateFrom = (Date)data.get("dateFrom");
//        this.dateTo = (Date)data.get("dateTo");
//        this.price = data.getDouble("price");
//        this.directFlight = data.getBoolean("directFlight");
    }


    JSONObject toJson() {
        return new JSONObject()
                .put("from", this.from)
                .put("to", this.to);
/*                .put("dateFrom", this.dateFrom)
                .put("dateTo", this.dateTo)
                .put("price", this.price)
                .put("directFlight", this.directFlight);*/
    }

}

package flights;

import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.json.JSONArray;
import org.json.JSONObject;

public class Handler {

    static JSONArray retrieve(JSONObject input) {
        MongoCollection flights = getFlights();
        //String from = input.getString("from");
        MongoCursor<Flight> all = flights.find(/*"{ from : # }", from*/).as(Flight.class);

        JSONArray jArray = new JSONArray();

        for (Flight f : all) {

            jArray.put(f.toJson());
        }

        return jArray;
    }

    public static void create(Flight flight) {
        getFlights().insert(flight);
    }

    public static void purge() {
        getFlights().remove();
    }


    private static MongoCollection getFlights() {
        MongoClient client = new MongoClient(Network.HOST, Network.PORT);
        return new Jongo(client.getDB(Network.DATABASE)).getCollection(Network.COLLECTION);
    }
}

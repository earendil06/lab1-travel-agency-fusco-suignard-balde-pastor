package flights;

import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class Handler {

    static JSONObject retrieve(JSONObject input) {
        MongoCollection flights = getFlights();
        String from = input.getString("from");
        MongoCursor<Flight> all = flights.find("{ from : # }", from).as(Flight.class);

        List<JSONObject> list = new ArrayList<>();

        for (Flight f : all) {

            list.add(f.toJson());
        }

        return new JSONObject(list);
    }

    private static MongoCollection getFlights() {
        MongoClient client = new MongoClient(Network.HOST, Network.PORT);
        return new Jongo(client.getDB(Network.DATABASE)).getCollection(Network.COLLECTION);
    }
}

package manager.service;

import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Iterator;

public class StorageRefused {

    public static void create(TravelRequest travelRequest) {
        MongoCollection travels = getRefusedRequests();
        travels.insert(travelRequest);
    }

    public static JSONArray getRequestByField(String type, String field) {
        MongoCollection pendings = getRefusedRequests();
        MongoCursor<TravelRequest> all =
                pendings.find("{ " + type + " : # }", field).as(TravelRequest.class);

        JSONArray jArray = new JSONArray();

        for (TravelRequest f : all) {
            jArray.put(f.toJson());
        }
        return jArray;
    }

    public static JSONArray getRequestByEmail(String owner) {
        return getRequestByField("owner", owner);
    }

    public static JSONArray findAll() {
        MongoCollection validated = getRefusedRequests();
        Iterator<TravelRequest> iter = validated.find().as(TravelRequest.class).iterator();
        JSONArray jsonArray = new JSONArray();
        while (iter.hasNext()) {
            jsonArray.put(iter.next().toJson());
        }
        return jsonArray;
    }

    private static MongoCollection getRefusedRequests() {
        MongoClient client = new MongoClient(Network.HOST, Network.PORT);
        return new Jongo(client.getDB(Network.DATABASE_REFUSED)).getCollection(Network.COLLECTION);
    }

/*    static {
        create(new TravelRequest("Adrien", Arrays.asList("Adrien", "Adrien"), Arrays.asList("Adrien", "Adrien", "Adrien")));
    }*/


}

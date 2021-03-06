package manager.data;

import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.json.JSONArray;

import java.util.Iterator;

public class StoragePendings {

    public static void create(TravelRequest TravelRequest) {
        MongoCollection pendings = getPendingRequests();
        pendings.insert(TravelRequest);
    }

    public static void delete(TravelRequest TravelRequest) {
        MongoCollection pendings = getPendingRequests();
        pendings.remove("{ uuidRequest : # }", TravelRequest.getUuidRequest());
    }

    public static JSONArray getRequestByField(String type, String field) {
        MongoCollection pendings = getPendingRequests();
        MongoCursor<TravelRequest> all =
                pendings.find("{ " + type + " : # }", field).as(TravelRequest.class);

        JSONArray jArray = new JSONArray();
        String uri = "/service-travel-manager/TravelPlannerService/request/uid/";
        for (TravelRequest f : all) {
            jArray.put(uri + f.getUuidRequest());
        }
        return jArray;
    }

    public static JSONArray getRequestByEmail(String owner) {
        return getRequestByField("email", owner);
    }

    public static TravelRequest getRequestByUID(String uid) {
        MongoCollection pendings = getPendingRequests();
        MongoCursor<TravelRequest> all =
                pendings.find("{ uuidRequest : # }", uid).as(TravelRequest.class);
        if (all == null) return null;
        return all.next();
    }

    public static JSONArray findAll() {
        MongoCollection pendings = getPendingRequests();
        Iterator<TravelRequest> iter = pendings.find().as(TravelRequest.class).iterator();
        JSONArray jsonArray = new JSONArray();
        String uri = "/service-travel-manager/TravelPlannerService/request/uid/";
        while (iter.hasNext()) {
            jsonArray.put(uri + iter.next().getUuidRequest());
        }
        return jsonArray;
    }

    public static TravelRequest getAndDelete(String uid) {
        TravelRequest travelRequest = getRequestByUID(uid);
        if (travelRequest != null) {
            delete(travelRequest);
        }
        return travelRequest;
    }

    private static MongoCollection getPendingRequests() {
        MongoClient client = new MongoClient(Network.HOST, Network.PORT);
        return new Jongo(client.getDB(Network.DATABASE_PENDING)).getCollection(Network.COLLECTION);
    }

    public static void purge() {
        getPendingRequests().drop();
    }

/*    static {
        create(new TravelRequest("aaa", Arrays.asList("_(_l(", "dfgngf"), Arrays.asList("iiii", "dfgdfg", "nnn")));
        create(new TravelRequest("bbb", Arrays.asList("_èlè_l", "tazhzh"), Arrays.asList("Etienne", "(-j(-j")));
        create(new TravelRequest("ccc", Arrays.asList("ibis", "zfezef"), Arrays.asList("zefzef", "erherh", "çm_çm")));
    }*/

}

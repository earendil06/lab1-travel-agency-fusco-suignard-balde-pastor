package travel;

import com.mongodb.MongoClient;
import org.apache.openjpa.util.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.json.JSONArray;
import travel.data.TravelSubmitRequest;

import java.util.Iterator;

public class Storage {

    public static String create(TravelSubmitRequest travelRequest) {
        MongoCollection travels = getTravels();
        travels.insert(travelRequest);
        return travelRequest.id;
    }

    /*public static JSONArray findAll() {
        MongoCollection cars = getTravels();
        Iterator<CarRental> iter = cars.find().as(CarRental.class).iterator();
        JSONArray jsonArray = new JSONArray();
        while (iter.hasNext()) {
            jsonArray.put(iter.next().toJson());
        }
        return jsonArray;
    }*/


    private static MongoCollection getTravels() {
        MongoClient client = new MongoClient(Network.HOST, Network.PORT);
        return new Jongo(client.getDB(Network.DATABASE)).getCollection(Network.COLLECTION);
    }
}

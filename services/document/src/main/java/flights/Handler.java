package flights;

import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Handler {

    static JSONArray retrieve(JSONObject input) {
        MongoCollection flights = getFlights();

        Map<String, Object> parameters = new HashMap<>();
        for (Field field : Flight.class.getDeclaredFields()) {
            if (field.isAnnotationPresent(AttributeQueryable.class)){
                try {
                    Object value = input.get(field.getName());
                    if (field.getType().isAssignableFrom(String.class)) value = "\"" + value + "\"";
                    parameters.put(field.getName(), value);
                }catch (Exception e){

                }
            }
        }
        String filter = filter(parameters);
        MongoCursor<Flight> all = flights.find(filter).as(Flight.class);

        JSONArray jArray = new JSONArray();

        for (Flight f : all) {

            jArray.put(f.toJson());
        }

        return jArray;
    }

    public static String filter(Map<String, Object> map) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        Iterator<String> listeKeys = map.keySet().iterator();
        while(listeKeys.hasNext()) {
            String key = listeKeys.next();
            builder.append(key + ":" + map.get(key));
            if (listeKeys.hasNext()) builder.append(",");
        }
        builder.append("}");
        return builder.toString();
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

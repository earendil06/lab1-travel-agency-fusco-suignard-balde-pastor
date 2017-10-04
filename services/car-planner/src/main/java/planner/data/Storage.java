package planner.data;

import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.json.JSONArray;

import java.util.Iterator;

public class Storage {

    public static void create(CarRental carsRental) {
        MongoCollection cars = getCars();
        cars.insert(carsRental);
    }

    //todo reformat, separation of concern
    public static JSONArray getCarsAtPlaceAndDuration(String place, String duration) {
        MongoCollection cars = getCars();
        MongoCursor<CarRental> all =
                cars.find("{ place : #, duration : # }", place, duration).as(CarRental.class);

        JSONArray jArray = new JSONArray();

        for (CarRental f : all) {
            jArray.put(f.toJson());
        }

        return jArray;
    }

    public static JSONArray findAll() {
        MongoCollection cars = getCars();
        Iterator<CarRental> iter = cars.find().as(CarRental.class).iterator();
        JSONArray jsonArray = new JSONArray();
        while (iter.hasNext()) {
            jsonArray.put(iter.next().toJson());
        }
        return jsonArray;
    }


    private static MongoCollection getCars() {
        MongoClient client = new MongoClient(Network.HOST, Network.PORT);
        return new Jongo(client.getDB(Network.DATABASE)).getCollection(Network.COLLECTION);
    }

/*    static {
        create(new CarRental("first", "Madrid", "2"));
        create(new CarRental("second", "Paris", "85"));
        create(new CarRental("third", "New York", "42"));
        create(new CarRental("4", "toto", "10"));
    }*/

}

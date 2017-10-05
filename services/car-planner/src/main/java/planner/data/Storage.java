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

    public static JSONArray getCarsForTravel(String place, int duration) {
        MongoCollection cars = getCars();
        MongoCursor<CarRental> all =
                cars.find("{ place : #, duration : { $gt : # } }", place, duration).as(CarRental.class);

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

    static {
        create(new CarRental("peugeot", "Paris", 4));
        create(new CarRental("renault", "Paris", 24));
        create(new CarRental("first", "Madrid", 2));
        create(new CarRental("second", "Paris", 85));
        create(new CarRental("third", "Tunis", 24));
    }

}

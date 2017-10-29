package planner.data;

import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Scanner;

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

    public static void initialize() throws ParseException {
        File file = new File(Storage.class.getClassLoader().getResource("cars.csv").getFile());
        try (Scanner scanner = new Scanner(file)) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] objects = line.split(",");
                CarRental car = new CarRental(objects[0], objects[1], objects[2], Integer.parseInt(objects[3]), Integer.parseInt(objects[4]));
                create(car);
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            Storage.getCars().remove();
            Storage.initialize();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}

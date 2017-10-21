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
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

public class Storage {

    static {
        try {
            Storage.purge();
            Storage.initialize();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void createHotel(Hotel hotel) {
        MongoCollection hotels = getHotels();
        hotels.insert(hotel);
    }

    public static void purge() {
        getHotels().remove();
    }

    private static MongoCollection getHotels() {
        MongoClient client = new MongoClient(Network.HOST, Network.PORT);
        return new Jongo(client.getDB(Network.DATABASE)).getCollection(Network.COLLECTION);
    }



    public static JSONArray getHotelsForTravel(String place, Date from, Date to) {
        MongoCollection hotels = getHotels();
        MongoCursor<Hotel> all =
                hotels.find("{ place : # , dateArrival: {$lte: #}, dateDeparture: {$gte: #} }", place, from, to)
                        .sort("{ price : 1 }")
                        .as(Hotel.class);

        JSONArray jArray = new JSONArray();

        for (Hotel f : all) {
            jArray.put(f.toJson());
        }

        return jArray;
    }

    public static JSONArray findAllHotels() {
        MongoCollection hotels = getHotels();
        Iterator<Hotel> iter = hotels.find().sort("{ price : 1 }").as(Hotel.class).iterator();
        JSONArray jsonArray = new JSONArray();
        while (iter.hasNext()) {
            jsonArray.put(iter.next().toJson());
        }
        return jsonArray;
    }

    public static void initialize() throws ParseException {
        DateFormat format = new SimpleDateFormat(Hotel.DATE_PATTERN);

        File file = new File(Storage.class.getClassLoader().getResource("hotels.csv").getFile());
        try (Scanner scanner = new Scanner(file)) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] objects = line.split(";");
                Hotel hotel = new Hotel(objects[0], objects[1], Integer.parseInt(objects[2]), format.parse(objects[3]), format.parse(objects[4]));
                createHotel(hotel);
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

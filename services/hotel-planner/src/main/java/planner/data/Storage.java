package planner.data;

import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.json.JSONArray;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class Storage {

    public static void create(HotelRental hotelRental) {
        MongoCollection hotels = getHotels();
        hotels.insert(hotelRental);
    }

    public static JSONArray getHotelsForTravel(String place, int day, int month, int year) {
        MongoCollection hotels = getHotels();
        MongoCursor<HotelRental> all =
                hotels.find("{ place : # , day : # , month : # , year : # }", place, day, month, year)
                        .sort("{ price : 1 }")
                        .as(HotelRental.class);

        JSONArray jArray = new JSONArray();

        for (HotelRental f : all) {
            jArray.put(f.toJson());
        }

        return jArray;
    }

    public static JSONArray findAll() {
        MongoCollection hotels = getHotels();
        Iterator<HotelRental> iter = hotels.find().sort("{ price : 1 }").as(HotelRental.class).iterator();
        JSONArray jsonArray = new JSONArray();
        while (iter.hasNext()) {
            jsonArray.put(iter.next().toJson());
        }
        return jsonArray;
    }


    private static MongoCollection getHotels() {
        MongoClient client = new MongoClient(Network.HOST, Network.PORT);
        return new Jongo(client.getDB(Network.DATABASE)).getCollection(Network.COLLECTION);
    }

//    static {
//        create(new HotelRental("first", "Tokyo", 10, 5, 2017, 100));
//
//        create(new HotelRental("second", "Paris", 17, 8, 2001, 500));
//
//        create(new HotelRental("third", "Bordeaux", 20, 12, 2012, 420));
//    }

}

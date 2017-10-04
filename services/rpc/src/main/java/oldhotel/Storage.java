package oldhotel;

import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.json.JSONArray;

import java.util.Iterator;

public class Storage {
    // this mocks a database.
    //private static HashMap<String, Collection<HotelRental>> contents = new HashMap<>();

    public static void create(HotelRental hotelRental) {
        MongoCollection hotels = getHotels();
        hotels.insert(hotelRental);
    }

    //todo reformat, separation of concern
    public static JSONArray getHotelAtPlaceAndDate(String destination, String day, String month, String year) {
        MongoCollection hotels = getHotels();
        String date = day + "/" + month + "/" + year;
        MongoCursor<HotelRental> all =
                hotels.find("{ place : #, date : # }", destination, date).as(HotelRental.class);

        JSONArray jArray = new JSONArray();

        for (HotelRental f : all) {
            jArray.put(f.toJson());
        }

        return jArray;
    }

    public static JSONArray findAll() {
        MongoCollection hotels = getHotels();
        Iterator<HotelRental> iter = hotels.find().as(HotelRental.class).iterator();
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

/*    static {
        create(new HotelRental("first", "Tokyo", "02/09/2010"));
        create(new HotelRental("second", "Paris", "04/06/2017"));
        create(new HotelRental("third", "Bordeaux", "12/12/1012"));
    }*/

}

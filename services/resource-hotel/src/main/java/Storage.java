import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Storage {
    // this mocks a database.
    private static HashMap<String, Collection<HotelRental>> contents = new HashMap<>();

    public static void create(HotelRental carRental) {
        List l = (ArrayList<HotelRental>) contents.get(carRental.getPlace());
        if (l == null) {
            List<HotelRental> toStore = new ArrayList<>();
            toStore.add(carRental);
            contents.put(carRental.getPlace(), toStore);
        } else {
            contents.get(carRental.getPlace()).add(carRental);
        }
    }

    //todo add date
    public static Collection<HotelRental> getHotelAtPlace(String place) {
        return contents.get(place);
    }

    public static Collection<HotelRental> findAll() {
        return contents.values().stream().collect(ArrayList::new, List::addAll, List::addAll);
    }

    static {
        create(new HotelRental("first", "Tokyo", "2"));
        create(new HotelRental("second", "Londre", "85"));
        create(new HotelRental("third", "Bordeaux", "42"));
    }

}

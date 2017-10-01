import java.util.*;

public class Storage {
    // this mocks a database.
    private static HashMap<String, Collection<CarRental>> contents = new HashMap<>();

    public static void create(CarRental carRental) {
        List l = (ArrayList<CarRental>) contents.get(carRental.getPlace());
        if (l == null) {
            List<CarRental> toStore = new ArrayList<>();
            toStore.add(carRental);
            contents.put(carRental.getPlace(), toStore);
        } else {
            contents.get(carRental.getPlace()).add(carRental);
        }
    }

    //todo add duration
    public static Collection<CarRental> getCarAtPlace(String place) {
        return contents.get(place);
    }

    public static Collection<CarRental> findAll() {
        return contents.values().stream().collect(ArrayList::new, List::addAll, List::addAll);
    }

    static {
        create(new CarRental("first", "Madrid", "2"));
        create(new CarRental("second", "Paris", "85"));
        create(new CarRental("third", "New York", "42"));
    }

}

package flights;

import com.mongodb.MongoClient;
import org.apache.velocity.runtime.directive.Parse;
import org.jongo.Find;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Handler {

    static {
        JSONObject o = new JSONObject();
        o.put("from", "Paris")
                .put("to", "Pangkalan")
                .put("date", "09.12.2017")
                .put("hour", "10.10")
                .put("duration", 12)
                .put("price", 12.0)
                .put("direct", true);
        Flight f = null;
        try {
            f = new Flight(o);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        purge();
        create(f);
    }

    static JSONArray retrieve(JSONObject input) throws ParseException {
        MongoCollection flights = getFlights();

        Map<String, Double> max = getHashMapMinMax(input, "max");
        Map<String, Double> min = getHashMapMinMax(input, "min");
        Map<String, Object> attrs = getHashMap(input);
        String sort = getSortFromInput(input);
        String filters = computeFilter(attrs, min, max);
        Find all = flights.find(filters);
        try {
            all = all.sort(sort);
        } catch (Exception ignored) {

        }
        JSONArray jArray = new JSONArray();

        for (Flight f : all.as(Flight.class)) {
            jArray.put(f.toJson());
        }

        return jArray;
    }

    private static String computeFilter(Map<String, Object> attrs, Map<String, Double> min, Map<String, Double> max) {
        StringBuilder builderGeneral = new StringBuilder();
        StringBuilder builderAttrs = new StringBuilder();
        StringBuilder builderMax = new StringBuilder();
        StringBuilder builderMin = new StringBuilder();

        Iterator<String> iterator = attrs.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            builderAttrs.append(key).append(":").append(attrs.get(key));
            if (iterator.hasNext()) builderAttrs.append(",");
        }

        iterator = max.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            builderMax.append(key).append(": {$lt : ").append(max.get(key)).append("}");
            if (iterator.hasNext()) builderMax.append(",");
        }

        iterator = min.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            builderMin.append(key).append(": {$gt : ").append(min.get(key)).append("}");
            if (iterator.hasNext()) builderMin.append(",");
        }

        builderGeneral.append("{");
        String attributes = String.join(",", builderAttrs);
        String maximums = String.join(",", builderMax);
        String minimums = String.join(",", builderMin);

        List<String> str = new ArrayList<>();
        if (!attributes.equals("")) str.add(attributes);
        if (!maximums.equals("")) str.add(maximums);
        if (!minimums.equals("")) str.add(minimums);

        builderGeneral.append(String.join(",", str));
        builderGeneral.append("}");
        return builderGeneral.toString();
    }

    private static String getSortFromInput(JSONObject input) {
        try {
            String attrSort = input.getString("order-by");
            List<String> orderables = new ArrayList<>();
            for (Field field : Flight.class.getDeclaredFields()) {
                if (field.isAnnotationPresent(AttributeQueryable.class) && field.getAnnotation(AttributeQueryable.class).order()) {
                    orderables.add(field.getName());
                }
            }
            if (orderables.contains(attrSort)) {
                return "{" + attrSort + ": 1}";
            }
        } catch (Exception ignored) {

        }
        return "{}";
    }

    private static Map<String, Object> getHashMap(JSONObject input) {
        Map<String, Object> result = new HashMap<>();
        for (Field field : Flight.class.getDeclaredFields()) {
            if (field.isAnnotationPresent(AttributeQueryable.class) && field.getAnnotation(AttributeQueryable.class).filter()) {
                try {
                    Object value = input.get(field.getName());
                    if (field.getType().isAssignableFrom(String.class)) value = "\"" + value + "\"";
                    else if (field.getType().isAssignableFrom(Date.class)) {
                        DateFormat df = new SimpleDateFormat(Flight.DATE_PATTERN);
                        Date date = df.parse((String) value);
                        Calendar c = Calendar.getInstance();
                        c.setTime(date);
                        c.add(Calendar.DATE, 1);
                        Date tomorrow = c.getTime();
                        c.add(Calendar.DATE, -2);
                        Date yesterday = c.getTime();
                        value = String.format("{$lt : {'$date' : %s}, $gt : {'$date' : %s}}", tomorrow.getTime(), yesterday.getTime());
                    }
                    result.put(field.getName(), value);
                } catch (Exception ignored) {

                }
            }
        }
        return result;
    }

    private static Map<String, Double> getHashMapMinMax(JSONObject master, String key) {
        Map<String, Double> result = new HashMap<>();
        JSONObject o;
        try {
            o = master.getJSONObject(key);
        } catch (Exception e) {
            return result;
        }

        for (Field field : Flight.class.getDeclaredFields()) {
            if (field.isAnnotationPresent(AttributeQueryable.class) && field.getAnnotation(AttributeQueryable.class).minMax()) {
                try {
                    double value = o.getDouble(field.getName());
                    result.put(field.getName(), value);
                } catch (Exception ignored) {

                }
            }
        }
        return result;
    }

    public static String create(Flight flight) {

        getFlights().insert(flight);
        return "inserted";
    }

    public static String purge() {
        getFlights().remove();
        return "purged";
    }


    private static MongoCollection getFlights() {
        MongoClient client = new MongoClient(Network.HOST, Network.PORT);
        return new Jongo(client.getDB(Network.DATABASE)).getCollection(Network.COLLECTION);
    }

    public static void initialize() throws ParseException {
        DateFormat format = new SimpleDateFormat(Flight.DATE_PATTERN);

        File file = new File(Handler.class.getClassLoader().getResource("flights.csv").getFile());
        try (Scanner scanner = new Scanner(file)) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] objects = line.split(";");
                Flight f = new Flight(
                        objects[0],
                        objects[1],
                        objects[2],
                        format.parse(objects[3]),
                        format.parse(objects[4]),
                        Integer.parseInt(objects[5]),
                        Double.parseDouble(objects[6]),
                        Boolean.getBoolean(objects[7]));

                create(f);
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            purge();
            initialize();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
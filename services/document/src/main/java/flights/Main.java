package flights;

import org.json.JSONObject;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws ParseException {
        JSONObject input = new JSONObject();
        input.put("event", "CREATE");

        JSONObject o = new JSONObject();
        o.put("event", "CREATE");
        o.put("flight", new JSONObject()
                .put("from", "paris")
                .put("to", "lo")
                .put("date", "1616")
                .put("hour", "151")
                .put("duration", 12)
                .put("price", 12.0)
                .put("direct", true));
        Flight f = new Flight(o.getJSONObject("flight"));

    }

}

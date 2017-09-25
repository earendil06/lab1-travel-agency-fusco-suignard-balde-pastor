package booking;

import org.json.JSONObject;

public interface Bookable {
    JSONObject execute(JSONObject input);
}

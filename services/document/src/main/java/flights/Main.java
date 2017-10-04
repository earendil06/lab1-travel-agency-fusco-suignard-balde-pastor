package flights;

import org.json.JSONObject;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws ParseException {
        JSONObject input = new JSONObject();
        input.put("event", "RETRIEVE");

        JSONObject maxObject = new JSONObject();
        maxObject.put("duration", 120);
        input.put("max", maxObject);

        Handler.retrieve(input);
    }

}

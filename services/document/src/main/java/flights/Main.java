package flights;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws ParseException {
//        JSONObject input = new JSONObject();
//        input.put("event", "CREATE");
//
//        JSONObject o = new JSONObject();
//        o.put("event", "CREATE");
//        o.put("flight", new JSONObject()
//                .put("from", "paris")
//                .put("to", "lo")
//                .put("date", "1616")
//                .put("hour", "151")
//                .put("duration", 12)
//                .put("price", 12.0)
//                .put("direct", true));
//        Flight f = new Flight(o.getJSONObject("flight"));
        Handler.purge();
        DateFormat df = new SimpleDateFormat(Flight.DATE_PATTERN);
        DateFormat hf = new SimpleDateFormat(Flight.HOUR_PATTERN);
        Handler.create(
                new Flight("Paris", "London",
                        df.parse("23.10.2017"),
                        hf.parse("23.00"),120,120,true ));
        System.out.println(Handler.retrieve(
                new JSONObject()
                        .put("event", "RETRIEVE")
                        .put("date", "23.10.2017")
        ));
    }

}

package booking;

import booking.bookableimpl.CarBookable;
import booking.bookableimpl.FlyBookable;
import booking.bookableimpl.HotelBookable;
import org.json.JSONObject;

class Handler {

    static JSONObject carExecute(JSONObject obj) {
        return new CarBookable().execute(obj);
    }

    static JSONObject flyExecute(JSONObject obj) {
        return new FlyBookable().execute(obj);
    }

    static JSONObject hotelExecute(JSONObject obj) {
        return new HotelBookable().execute(obj);
    }

}

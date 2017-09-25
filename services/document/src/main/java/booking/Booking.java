package booking;

import org.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/booking")
@Produces(MediaType.APPLICATION_JSON)
public class Booking {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response process(String input) {
        JSONObject obj = new JSONObject(input);
        try {
            EVENT event = EVENT.valueOf(obj.getString("event"));
            switch (event) {
                case CAR:
                    return Response.ok().entity(Handler.carExecute(obj).toString()).build();
                case FLY:
                    return Response.ok().entity(Handler.flyExecute(obj).toString()).build();
                case HOTEL:
                    return Response.ok().entity(Handler.hotelExecute(obj).toString()).build();
                default:
                    break;
            }
        } catch (Exception e) {
            JSONObject error = new JSONObject().put("error", e.toString());
            return Response.status(400).entity(error.toString()).build();
        }
        return null;
    }
}

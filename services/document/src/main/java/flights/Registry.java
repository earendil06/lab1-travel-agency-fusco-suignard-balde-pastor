package flights;

import org.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/flights")
@Produces(MediaType.APPLICATION_JSON)
public class Registry {

    private static final int INDENT_FACTOR = 2;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response process(String input) {
        JSONObject obj = new JSONObject(input);
        try {
            switch (Event.valueOf(obj.getString("event"))) {
                case RETRIEVE:
                    return Response.ok().entity(Handler.retrieve(obj).toString(INDENT_FACTOR)).build();
                case CREATE:
                    Flight f = new Flight(obj.getJSONObject("flight"));
                    return Response.ok().entity(Handler.create(f)).build();
                case PURGE:
                    return Response.ok().entity(Handler.purge()).build();
            }
        } catch(Exception e) {
            JSONObject error = new JSONObject().put("error", e.toString());
            return Response.status(400).entity(error.toString(INDENT_FACTOR)).build();
        }
        return null;
    }

}

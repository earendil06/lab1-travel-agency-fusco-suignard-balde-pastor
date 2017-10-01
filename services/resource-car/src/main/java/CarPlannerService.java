import org.json.JSONArray;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("/CarPlannerService")
@Produces(MediaType.APPLICATION_JSON)
public class CarPlannerService {

    @GET
    public Response request() { //todo might need to be deleted, maybe we don't want all the db to be downloadable
        JSONArray hotelRentals = Storage.findAll();
        return Response.ok().entity(hotelRentals.toString()).build();
    }

    @Path("/{place}/{duration}")
    @GET
    public Response request(@PathParam("place") String place, @PathParam("duration") String duration) {
        //todo error message when not any
        JSONArray carRentals = Storage.getCarsAtPlaceAndDuration(place, duration);
        return Response.ok().entity(carRentals.toString()).build();
    }
}

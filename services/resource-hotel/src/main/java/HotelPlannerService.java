import org.json.JSONArray;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/HotelPlannerService")
@Produces(MediaType.APPLICATION_JSON)
public class HotelPlannerService {

    @GET
    public Response request() { //todo might need to be deleted, maybe we don't want all the db to be downloadable
        JSONArray hotelRentals = Storage.findAll();
        return Response.ok().entity(hotelRentals.toString()).build();
    }

    @Path("/{place}/{date}")
    @GET
    public Response request(@PathParam("place") String place, @PathParam("date") String date) {
        JSONArray hotelRentals = Storage.getHotelAtPlaceAndDate(place, date);
        return Response.ok().entity(hotelRentals.toString()).build();
    }

}

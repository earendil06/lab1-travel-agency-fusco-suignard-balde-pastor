import org.json.JSONArray;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("/HotelPlannerService")
@Produces(MediaType.APPLICATION_JSON)
public class HotelPlannerService {

    @GET
    public Response request() { //todo might need to be deleted, maybe we don't want all the db to be downloadable
        Collection<HotelRental> hotelRentals = Storage.findAll();
        JSONArray result = new JSONArray();
        for (HotelRental hotelRental : hotelRentals) {
            result.put(hotelRental.toString());
        }
        return Response.ok().entity(result.toString()).build();
    }

    @Path("/{place}")
    @GET
    public Response request(String place) {
        Collection<HotelRental> hotelRentals = Storage.getHotelAtPlace(place);
        JSONArray result = new JSONArray();
        for (HotelRental hotelRental : hotelRentals) {
            result.put(hotelRental.toString());
        }
        return Response.ok().entity(result.toString()).build();
    }
}

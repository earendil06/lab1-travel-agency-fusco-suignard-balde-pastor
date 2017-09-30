import org.json.JSONArray;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("/CarPlannerService")
@Produces(MediaType.APPLICATION_JSON)
public class CarPlannerService {

    @GET
    public Response request() { //todo might need to be deleted, maybe we don't want all the db to be downloadable
        Collection<CarRental> carRentals = Storage.findAll();
        JSONArray result = new JSONArray();
        for (CarRental carRental : carRentals) {
            result.put(carRental.toString());
        }
        return Response.ok().entity(result.toString()).build();
    }

    @Path("/{place}")
    @GET
    public Response request(String place) { //todo error message when not any
        Collection<CarRental> carRentals = Storage.getCarAtPlace(place);
        JSONArray result = new JSONArray();
        for (CarRental carRental : carRentals) {
            result.put(carRental.toString());
        }
        return Response.ok().entity(result.toString()).build();
    }
}

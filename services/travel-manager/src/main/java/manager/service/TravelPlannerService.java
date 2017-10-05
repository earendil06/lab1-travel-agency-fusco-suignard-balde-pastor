package manager.service;

import manager.data.StoragePendings;
import manager.data.TravelRequest;
import org.json.JSONArray;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/TravelPlannerService")
@Produces(MediaType.APPLICATION_JSON)
public class TravelPlannerService {

    @GET
    public Response getAll() {
        JSONArray pending = StoragePendings.findAll();
        return Response.ok().entity(pending.toString()).build();
    }

    @Path("/email/{email}")
    @GET
    public Response getRequestsByEmail(@PathParam("email") String email) {
        JSONArray pending = StoragePendings.getRequestByEmail(email);
        return Response.ok().entity(pending.toString()).build();
    }

    @Path("/uid/{uid}")
    @GET
    public Response getRequestsByUID(@PathParam("uid") String uid) {
        TravelRequest pending = StoragePendings.getRequestByUID(uid);
        if (pending == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(pending.toJson().toString()).build();
    }

    @POST
    @Path("/create")
    @Consumes("application/json")
    @Produces(MediaType.TEXT_PLAIN)
    public Response create(TravelRequest input) {
        String uid = UUID.randomUUID().toString();
        input.setUuidRequest(uid);
        StoragePendings.create(input);
        return Response.ok().entity(uid).build();
    }

    public static void main(String[] args) {
        JSONArray pending = StoragePendings.getRequestByEmail("a");
        System.out.println(pending);
    }
}

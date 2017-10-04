package manager.service;

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
        return Response.ok().entity(pending.toJson()).build();
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
        TravelRequest pending = StoragePendings.getRequestByUID("0f8387f8-290c-4bc9-a034-767181f805fd");
        System.out.println(pending.toJson());
    }
}

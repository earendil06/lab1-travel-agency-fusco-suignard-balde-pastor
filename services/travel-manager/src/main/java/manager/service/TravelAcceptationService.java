package manager.service;

import manager.data.StoragePendings;
import manager.data.StorageRefused;
import manager.data.StorageValidated;
import manager.data.TravelRequest;
import org.json.JSONArray;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/TravelAcceptationService")
@Produces(MediaType.APPLICATION_JSON)
public class TravelAcceptationService {

    @Path("/validate")
    @GET
    public Response getAllValidated() {
        JSONArray validated = StorageValidated.findAll();
        return Response.ok().entity(validated.toString()).build();
    }

    @Path("/refuse")
    @GET
    public Response getAllRefused() {
        JSONArray validated = StorageRefused.findAll();
        return Response.ok().entity(validated.toString()).build();
    }

    @Path("/validate/email/{email}")
    @GET
    public Response getAcceptedRequestsByUser(@PathParam("email") String email) {
        JSONArray pending = StorageValidated.getRequestByEmail(email);
        return Response.ok().entity(pending.toString()).build();
    }

    @Path("/refuse/email/{email}")
    @GET
    public Response getRefusedRequestsByUser(@PathParam("email") String email) {
        JSONArray pending = StorageRefused.getRequestByEmail(email);
        return Response.ok().entity(pending.toString()).build();
    }

    @Path("/validate/uid/{uid}")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response validate(@PathParam("uid") String uid) {
        TravelRequest request = StoragePendings.getAndDelete(uid);
        if (request == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        StorageValidated.create(request);
        System.out.println("Request validated:\n" + request);
        return Response.ok().entity("Request " + request.getUuidRequest() + " has been validated").build();
    }

    @Path("/refuse/uid/{uid}")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response refuse(@PathParam("uid") String uid) {
        TravelRequest request = StoragePendings.getAndDelete(uid);
        if (request == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        StorageRefused.create(request);
        System.out.println("Request refused:\n" + request);
        return Response.ok().entity("Request " + request.getUuidRequest() + " has been refused").build();
    }

}

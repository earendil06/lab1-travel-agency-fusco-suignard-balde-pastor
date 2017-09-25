import com.fasterxml.jackson.databind.ObjectMapper;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/FlyPlanner")
@Produces(MediaType.APPLICATION_JSON)
public class FlyPlanner {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response request(String proposalString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Parameters parameters = mapper.readValue(proposalString, Parameters.class);
            Storage.create(parameters);
            return Response.ok().build();
        } catch (IOException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("\"Object error\"")
                    .build();
        }

    }
}

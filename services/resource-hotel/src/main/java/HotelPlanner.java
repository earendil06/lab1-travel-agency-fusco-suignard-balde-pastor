import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/HotelPlanner")
@Produces(MediaType.APPLICATION_JSON)
public class HotelPlanner {

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

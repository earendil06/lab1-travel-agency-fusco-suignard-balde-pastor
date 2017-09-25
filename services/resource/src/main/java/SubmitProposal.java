import com.fasterxml.jackson.databind.ObjectMapper;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/submitTravel")
@Produces(MediaType.APPLICATION_JSON)
public class SubmitProposal {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response submitProposal(String proposalString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Proposal proposal = mapper.readValue(proposalString, Proposal.class);
            Storage.create(proposal);
            return Response.ok().build();
        } catch (IOException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("\"Object error\"")
                    .build();
        }

    }
}

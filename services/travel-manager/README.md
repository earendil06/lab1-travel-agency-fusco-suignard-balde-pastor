## Service API

The service produces `application/json` data. It defines several routes under the `generator` prefix, to create generators, get all available generators, generate a new identifier or delete a generator.

```java
@Path("/generators")
@Produces(MediaType.APPLICATION_JSON)
public class GeneratorService {

	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	public Response createNewGenerator(String name) { ... }

	@GET
	public Response getAvailableGenerators() { ... }

	@Path("/{name}")
	@GET
	public Response generateIdentifier(@PathParam("name") String name) { ... }

	@Path("/{name}")
	@DELETE
	public Response deleteGenerator(@PathParam("name") String name) { ... }

}
``` 

The service is implemented in the [GeneratorService](https://github.com/polytechnice-si/5A-Microservices-Integration/blob/master/services/resource/src/main/java/gen/GeneratorService.java) class.

## Starting the service
  * The service is available at [http://localhost:8080/tcs-service-rest/generators](http://localhost:8080/tcs-service-rest/generators)


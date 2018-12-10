package castrserver.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/service")
public class Service {
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getJson(@QueryParam("x") int x, @QueryParam("y") int y) {
		return Response.ok().build();
	}
	
	@POST
	@Path("/post")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	public Response postJson(String body) {
		return Response.ok().build();
	}
}

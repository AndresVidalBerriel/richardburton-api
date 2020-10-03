package br.edu.ifrs.canoas.richardburton.users;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public interface UserResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response retrieve();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response create(User user, @HeaderParam("RB-password") String password);

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response retrieve(@PathParam("id") Long id);
}
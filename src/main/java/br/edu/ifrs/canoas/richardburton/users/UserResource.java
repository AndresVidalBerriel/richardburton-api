package br.edu.ifrs.canoas.richardburton.users;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public interface UserResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response create(User user, @HeaderParam("rb-authentication-string") String authenticationString);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response retrieve();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response retrieve(@PathParam("id") Long id);
}
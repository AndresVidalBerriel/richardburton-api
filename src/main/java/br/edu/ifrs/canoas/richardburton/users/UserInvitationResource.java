package br.edu.ifrs.canoas.richardburton.users;

import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("/users/invitations")
public interface UserInvitationResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response retrieve();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response create(UserInvitation invitation);

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    Response retrieve(@PathParam("email") String email);

    @PATCH
    @Path("/{email}")
    Response refresh(@PathParam("email") String email);

    @POST
    @Path("/confirmed")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response confirm(
      User user,
      @HeaderParam("RB-password") String authenticationString
    );

    @POST
    @Path("/cancelled")
    @Consumes(MediaType.TEXT_PLAIN)
    Response cancel(String email);

}

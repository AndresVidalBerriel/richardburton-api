package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.auth.CredentialsGroup;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users/groups")
public interface UserGroupResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response retrieve();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response create(CredentialsGroup group);

    @DELETE
    @Path("/{name}")
    Response delete(@PathParam("name") String name);

    @POST
    @Path("/{name}/members")
    @Consumes(MediaType.TEXT_PLAIN)
    Response addMember(@PathParam("name") String groupName, Long userId);

    @DELETE
    @Path("/{name}/members/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response removeMember(@PathParam("name") String groupName, @PathParam("id") Long userId);
}

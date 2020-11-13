package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.auth.CredentialsGroup;
import br.edu.ifrs.canoas.richardburton.auth.Permissions;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

@Path("/user-groups")
public interface UserGroupResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response retrieve();

    @GET
    @Path("/names")
    @Produces(MediaType.APPLICATION_JSON)
    Response retrieveNames();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response create(CredentialsGroup group);

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    Response retrieve(@PathParam("name") String name);

    @DELETE
    @Path("/{name}")
    Response delete(@PathParam("name") String name);

    @POST
    @Path("/{name}/members")
    @Consumes(MediaType.APPLICATION_JSON)
    Response addMember(@PathParam("name") String groupName, UserIdentifier userIdentifier);

    @DELETE
    @Path("/{name}/members/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response removeMember(@PathParam("name") String groupName, @PathParam("id") String userEmail);

    @POST
    @Path("/{name}/permissions")
    @Consumes(MediaType.APPLICATION_JSON)
    Response setPermissions(@PathParam("name") String groupName, Set<Permissions> permissions);
}

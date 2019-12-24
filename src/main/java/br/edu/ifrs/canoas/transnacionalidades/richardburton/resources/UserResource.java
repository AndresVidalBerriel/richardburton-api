package br.edu.ifrs.canoas.transnacionalidades.richardburton.resources;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.edu.ifrs.canoas.transnacionalidades.richardburton.services.UserService;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.entities.User;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.exceptions.InvalidEmailFormatException;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.util.http.RBHttpStatus;

@Path("/users")
@Stateless
public class UserResource {

    @Inject
    private UserService userService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(User user) {

        try {

            user = userService.create(user);
            return Response.status(Response.Status.CREATED).entity(user).build();

        } catch (ConstraintViolationException e) {

            return Response.status(RBHttpStatus.UNPROCESSABLE_ENTITY).entity(e).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAll() {

        List<User> users = userService.retrieveAll();
        return Response.status(Response.Status.OK).entity(users).build();
    }

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieve(@PathParam("email") String email) {

        try {

            User user = userService.retrieve(email);
            return Response.status(Response.Status.OK).entity(user).build();

        } catch (InvalidEmailFormatException e) {

            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
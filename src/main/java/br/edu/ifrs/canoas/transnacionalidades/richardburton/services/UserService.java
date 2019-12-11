package br.edu.ifrs.canoas.transnacionalidades.richardburton.services;

import java.util.List;
import java.util.regex.Pattern;

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

import br.edu.ifrs.canoas.transnacionalidades.richardburton.annotations.RequiresAuthentication;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.dao.UserDAO;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.entities.User;

@Path("/users")
@Stateless
public class UserService {

    @Inject
    UserDAO userDAO;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(User user) {

        Response response;
        try {
            user = userDAO.create(user);
            response = Response.status(Response.Status.OK).entity(user).build();
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            response = Response.status(Response.Status.BAD_REQUEST).entity(e).build();
        }
        return response;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAll() {

        Response response;
        List<User> users = userDAO.retrieveAll();
        response = Response.status(Response.Status.OK).entity(users).build();

        return response;
    }

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieve(@PathParam("email") String email) {

        Response response;

        if (Pattern.matches(User.getEmailformat(), email)) {

            User user = userDAO.retrieve(email);
            response = Response.status(Response.Status.OK).entity(user).build();

        } else {

            response = Response.status(Response.Status.BAD_REQUEST).build();
        }

        return response;
    }
}
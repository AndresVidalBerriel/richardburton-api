package br.edu.ifrs.canoas.transnacionalidades.richardburton.services;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.edu.ifrs.canoas.transnacionalidades.richardburton.controllers.UserController;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.entities.User;

@Path("/users")
@Stateless
public class UserService {

    @Inject
    private UserController userController;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(User user) {

        return userController.create(user);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAll() {

        return userController.retrieveAll();
    }

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieve(@PathParam("email") String email) {

        return userController.retrieve(email);
    }
}
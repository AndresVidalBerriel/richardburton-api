package br.edu.ifrs.canoas.transnacionalidades.richardburton.services;

import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.edu.ifrs.canoas.transnacionalidades.richardburton.controllers.UserController;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.entities.User;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.util.JWT;

@Path("/session")
@Stateless
public class SessionService {

    @Inject
    private UserController userController;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response signIn(Map<String, String> parameters) {

        String email = parameters.get("email");
        String authenticationString = parameters.get("authenticationString");

        User user = userController.authenticate(email, authenticationString);

        if (user != null) {

            String token = JWT.issueToken(email, "richardburton-api", user.isAdmin());
            return Response.ok(user).header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
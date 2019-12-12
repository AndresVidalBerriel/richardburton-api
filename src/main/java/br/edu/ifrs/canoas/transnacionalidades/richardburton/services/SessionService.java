package br.edu.ifrs.canoas.transnacionalidades.richardburton.services;

import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.edu.ifrs.canoas.transnacionalidades.richardburton.controllers.SessionController;

@Path("/session")
@Stateless
public class SessionService {

    @Inject
    private SessionController sessionController;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response signIn(Map<String, String> parameters) {

        String email = parameters.get("email");
        String authenticationString = parameters.get("authenticationString");
        return sessionController.signIn(email, authenticationString);
    }
}
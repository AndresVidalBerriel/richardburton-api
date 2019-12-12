package br.edu.ifrs.canoas.transnacionalidades.richardburton.controllers;

import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import br.edu.ifrs.canoas.transnacionalidades.richardburton.dao.UserDAO;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.entities.User;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.util.JWT;

public class SessionController {

    @Inject
    private UserDAO userDAO;

    public Response signIn(String email, String authenticationString) {

        User user = userDAO.retrieve(email);

        if (user != null) {

            boolean authentic = user.getAuthenticationString().equals(authenticationString);

            if (authentic) {

                String token = JWT.issueToken(email, "richardburton-api", user.isAdmin());
                return Response.ok(user).header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
            }
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
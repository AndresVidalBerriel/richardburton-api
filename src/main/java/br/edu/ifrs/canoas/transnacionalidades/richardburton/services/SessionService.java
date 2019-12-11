package br.edu.ifrs.canoas.transnacionalidades.richardburton.services;

import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import br.edu.ifrs.canoas.transnacionalidades.richardburton.dao.UserDAO;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.entities.User;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.util.JWT;

@Path("/session")
@Stateless
public class SessionService {

    @Context
    private UriInfo uriInfo;

    @Inject
    UserDAO userDAO;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response signIn(Map<String, String> params) {

        String email = params.get("email");
        String authenticationString = params.get("authenticationString");

        User user = userDAO.retrieve(email);

        if (user != null) {

            boolean authentic = user.getAuthenticationString().equals(authenticationString);

            if (authentic) {

                String issuer = uriInfo.getAbsolutePath().toString();
                String token = JWT.issueToken(email, issuer, user.isAdmin());
                return Response.ok(user).header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
            }
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
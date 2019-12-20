package br.edu.ifrs.canoas.transnacionalidades.richardburton.resources;

import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import br.edu.ifrs.canoas.transnacionalidades.richardburton.services.UserService;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.entities.User;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.util.JWT;

@Path("/session")
@Stateless
public class SessionResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private UserService userService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response signIn(@Context HttpHeaders headers) {

        String authorizationHeader = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null) {

            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        String[] credentials = authorizationHeader.substring("Basic".length()).trim().split(":", -1);
        String email = credentials[0];
        String authenticationString = credentials[1];

        User user = userService.authenticate(email, authenticationString);

        if (user != null) {

            String issuer = uriInfo.getAbsolutePath().toString();
            String token = JWT.issueToken(email, issuer, user.isAdmin());

            return Response.ok(user).header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
package br.edu.ifrs.canoas.richardburton.session;

import java.util.Base64;

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

import br.edu.ifrs.canoas.richardburton.users.EmailFormatException;
import br.edu.ifrs.canoas.richardburton.users.User;
import br.edu.ifrs.canoas.richardburton.users.UserService;

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

        String email;
        String authenticationString;

        try {

            String token = authorizationHeader.substring("Basic".length()).trim();
            String[] credentials = new String(Base64.getDecoder().decode(token)).split(":", -1);
            email = credentials[0];
            authenticationString = credentials[1];

        } catch (ArrayIndexOutOfBoundsException e) {

            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {

            User user = userService.authenticate(email, authenticationString);

            if (user != null) {

                String issuer = uriInfo.getAbsolutePath().toString();
                String token = JWT.issueToken(email, issuer, user.isAdmin());
                user.setToken(token);
                return Response.ok(user).build();
            }

        } catch (EmailFormatException e) {

            return Response.status(Response.Status.BAD_REQUEST).build();

        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
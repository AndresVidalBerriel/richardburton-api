package br.edu.ifrs.canoas.richardburton.session;

import br.edu.ifrs.canoas.richardburton.auth.*;
import br.edu.ifrs.canoas.richardburton.users.UserNotFoundException;
import br.edu.ifrs.canoas.richardburton.users.UserValidationException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

@Stateless
public class SessionResourceImpl implements SessionResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private SessionService sessionService;

    @Inject
    private AuthenticationService authenticationService;

    public Response create(String auth) {

        try {

            Session session = sessionService.create(AuthenticationParser.parseBasic(auth));
            return Response.ok(session.getUser()).header("rb-authorization", session.getToken()).build();

        } catch (AuthenticationParseException | UserValidationException e) {

            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();

        } catch (AuthenticationFailedException e) {

            return Response.status(Status.UNAUTHORIZED).header("WWW-Authenticate", "Basic").build();

        } catch (UserNotFoundException e) {

            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @Override
    public Response refresh(String auth) {

        try {
            String token = AuthenticationParser.parseBearer(auth);
            Credentials credentials = new CredentialsBuilder().token(token).build();
            String refreshed = authenticationService.refreshToken(credentials).getToken();

            return Response.ok(refreshed).build();

        } catch (AuthenticationFailedException e) {

            return Response.status(Status.UNAUTHORIZED).entity(e.getMessage()).build();

        } catch (AuthenticationParseException e) {

            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();

        }
    }

    @Override
    public Response verify(String auth) {

        try {
            String token = AuthenticationParser.parseBearer(auth);
            Credentials credentials = new CredentialsBuilder().token(token).build();
            authenticationService.authenticate(credentials);

            return Response.status(Status.OK).build();

        } catch (AuthenticationFailedException e) {

            return Response.status(Status.UNAUTHORIZED).build();

        } catch (AuthenticationParseException e) {

            return Response.status(Status.BAD_REQUEST).build();
        }
    }
}
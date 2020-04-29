package br.edu.ifrs.canoas.richardburton.session;

import br.edu.ifrs.canoas.richardburton.users.User;
import br.edu.ifrs.canoas.richardburton.users.UserValidationException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Stateless
public class SessionResourceImpl implements SessionResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private SessionService sessionService;

    public Response create(String auth) {

        try {

            Session session = sessionService.create(AuthenticationParser.parseBasic(auth));
            return Response.ok(session.getUser()).header("rb-authorization", session.getToken()).build();

        } catch (AuthenticationParseException | UserValidationException e) {

            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();

        } catch (AuthenticationFailedException e) {

            return Response.status(Response.Status.UNAUTHORIZED).header("WWW-Authenticate", "Basic").build();
        }
    }

    @Override
    public Response refresh(String auth) {

        try {

            String refreshed = sessionService.refresh(AuthenticationParser.parseBearer(auth));
            return Response.ok(refreshed).build();

        } catch (AuthenticationFailedException e) {

            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();

        } catch (AuthenticationParseException e) {

            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();

        }
    }
}
package br.edu.ifrs.canoas.richardburton.session;

import br.edu.ifrs.canoas.richardburton.auth.*;
import br.edu.ifrs.canoas.richardburton.util.ServicePrimitive;
import br.edu.ifrs.canoas.richardburton.util.ServiceResponse;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
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

        ServiceResponse response = AuthenticationParser.parseBasic(auth);
        if (!response.ok())
            return Response
              .status(Response.Status.BAD_REQUEST)
              .entity("Invalid basic auth header.")
              .build();

        response = sessionService.create((Credentials) response);
        switch (response.status()) {
            case NOT_FOUND:
                return Response
                  .status(Response.Status.NOT_FOUND)
                  .entity("User not found.")
                  .build();

            case UNAUTHORIZED:
                return Response
                  .status(Response.Status.UNAUTHORIZED)
                  .header("WWW-Authenticate", "Basic")
                  .build();

            case EXPIRED_ENTITY:
                return Response
                  .status(Response.Status.UNAUTHORIZED)
                  .entity("Token expired.")
                  .header("WWW-Authenticate", "Basic")
                  .build();

            case INVALID_ENTITY:
                return Response
                  .status(Response.Status.BAD_REQUEST)
                  .entity("Invalid email address.")
                  .build();

            case OK:
                Session session = (Session) response;
                return Response
                  .ok(session.getUser())
                  .header("RB-authorization", session.getToken())
                  .build();

            default:
                return Response
                  .status(Response.Status.INTERNAL_SERVER_ERROR)
                  .entity(response.descriptor())
                  .build();
        }

    }

    @Override
    public Response refresh(String auth) {

        ServiceResponse response = AuthenticationParser.parseBearer(auth);
        if (!response.ok())
            return Response
              .status(Response.Status.BAD_REQUEST)
              .entity("Invalid basic auth header.")
              .build();

        String token = ((ServicePrimitive<String>) response).unwrap();
        Credentials credentials = new CredentialsBuilder()
          .token(token)
          .build();

        response = authenticationService.refreshToken(credentials);
        switch (response.status()) {
            case EXPIRED_ENTITY:
                return Response
                  .status(Response.Status.UNAUTHORIZED)
                  .entity("Token expired.")
                  .header("WWW-Authenticate", "Basic")
                  .build();

            case INVALID_ENTITY:
                return Response
                  .status(Response.Status.BAD_REQUEST)
                  .entity("Invalid token.")
                  .build();

            case OK:
                return Response
                  .ok()
                  .header("RB-authorization", credentials.getToken())
                  .build();

            default:
                return Response
                  .status(Response.Status.INTERNAL_SERVER_ERROR)
                  .entity(response.descriptor())
                  .build();
        }
    }

    @Override
    public Response verify(String auth) {

        ServiceResponse response = AuthenticationParser.parseBearer(auth);
        if (!response.ok())
            return Response
              .status(Response.Status.BAD_REQUEST)
              .entity("Invalid basic auth header.")
              .build();

        String token = ((ServicePrimitive<String>) response).unwrap();
        Credentials credentials = new CredentialsBuilder()
          .token(token)
          .build();

        response = authenticationService.authenticate(credentials);
        switch (response.status()) {
            case EXPIRED_ENTITY:
                return Response
                  .status(Response.Status.UNAUTHORIZED)
                  .entity("Token expired.")
                  .header("WWW-Authenticate", "Basic")
                  .build();

            case INVALID_ENTITY:
                return Response
                  .status(Response.Status.BAD_REQUEST)
                  .entity("Invalid token.")
                  .build();

            case OK:
                return Response
                  .ok()
                  .build();

            default:
                return Response
                  .status(Response.Status.INTERNAL_SERVER_ERROR)
                  .entity(response.descriptor())
                  .build();
        }
    }
}
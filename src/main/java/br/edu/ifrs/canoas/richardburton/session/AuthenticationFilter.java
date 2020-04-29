package br.edu.ifrs.canoas.richardburton.session;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;

@Provider
@RequiresAuthentication
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    @Context
    private HttpHeaders headers;

    @Inject
    private SessionService sessionService;

    @Override
    public void filter(ContainerRequestContext requestContext) {

        Response response;

        try {

            String token = AuthenticationParser.parseBearer(headers.getHeaderString(HttpHeaders.AUTHORIZATION));
            Claims claims = sessionService.authenticate(token);

            Method method = resourceInfo.getResourceMethod();
            RequiresAuthentication annotation = method.getAnnotation(RequiresAuthentication.class);
            Privileges privileges = annotation.privileges();

            sessionService.authorize(claims, privileges);
            return;

        } catch (AuthenticationParseException | AuthorizationParseException e) {

            response = Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();

        } catch (AuthenticationFailedException e) {

            response = Response.status(Status.UNAUTHORIZED).entity(e.getMessage()).build();

        } catch (AuthorizationFailedException e) {

            response = Response.status(Status.FORBIDDEN).entity(e.getMessage()).build();

        }

        requestContext.abortWith(response);
    }
}
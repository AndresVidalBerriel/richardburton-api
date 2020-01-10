package br.edu.ifrs.canoas.transnacionalidades.richardburton.filters.providers;

import java.lang.reflect.Method;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import br.edu.ifrs.canoas.transnacionalidades.richardburton.filters.annotations.RequiresAuthentication;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.filters.annotations.RequiresAuthentication.Privileges;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.util.JWT;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.util.http.RBHttpStatus;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

@Provider
@RequiresAuthentication
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) {

        Response response = Response.status(Response.Status.UNAUTHORIZED).build();

        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null) {
            response = Response.status(Response.Status.BAD_REQUEST).build();
            requestContext.abortWith(response);
            return;
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();

        boolean unathorized = false;

        try {

            Claims claims = JWT.decodeToken(token);

            Method method = resourceInfo.getResourceMethod();
            RequiresAuthentication annotation = method.getAnnotation(RequiresAuthentication.class);
            Privileges privileges = annotation.privileges();
            boolean adminPrivilegesRequired = privileges == Privileges.ADMIN;
            boolean userIsAdmin = Boolean.parseBoolean((String) claims.get("admin"));

            unathorized = adminPrivilegesRequired && !userIsAdmin;

        } catch (ExpiredJwtException e) {

            unathorized = true;
            response = Response.status(RBHttpStatus.SESSION_EXPIRED).build();

        } catch (JwtException e) {

            unathorized = true;
        }

        if (unathorized) {

            requestContext.abortWith(response);
        }
    }
}
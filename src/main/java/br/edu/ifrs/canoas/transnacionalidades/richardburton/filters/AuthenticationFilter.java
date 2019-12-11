package br.edu.ifrs.canoas.transnacionalidades.richardburton.filters;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import br.edu.ifrs.canoas.transnacionalidades.richardburton.annotations.RequiresAuthentication;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.util.JWT;
import io.jsonwebtoken.JwtException;

@Provider
@RequiresAuthentication
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) {

        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null) {
            abort(requestContext);
            return;
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {

            JWT.decodeToken(token);

        } catch (JwtException e) {

            abort(requestContext);
        }
    }

    private void abort(ContainerRequestContext requestContext) {

        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }
}
package br.edu.ifrs.canoas.richardburton.auth;


import br.edu.ifrs.canoas.richardburton.util.ServicePrimitive;
import br.edu.ifrs.canoas.richardburton.util.ServiceResponse;
import br.edu.ifrs.canoas.richardburton.util.ServiceStatus;

import javax.ws.rs.core.HttpHeaders;
import java.util.Base64;

public class AuthenticationParser {

    private static String BEARER_PREFIX = "Bearer ";
    private static String BASIC_PREFIX = "Basic ";

    public static ServiceResponse parseBearer(HttpHeaders headers) {
        return parseBearer(headers.getHeaderString(HttpHeaders.AUTHORIZATION));
    }

    public static ServiceResponse parseBearer(String auth) {
        return auth == null || !auth.startsWith(BEARER_PREFIX)
          ? ServiceStatus.INVALID_ENTITY
          : new ServicePrimitive<>(auth.substring(BEARER_PREFIX.length()));
    }

    public static ServiceResponse parseBasic(HttpHeaders headers) {
        return parseBasic(headers.getHeaderString(HttpHeaders.AUTHORIZATION));
    }

    public static ServiceResponse parseBasic(String auth) {

        if(auth == null || !auth.startsWith(BASIC_PREFIX))
            return ServiceStatus.INVALID_ENTITY;

        String encoded = auth.substring(BASIC_PREFIX.length());
        String[] decoded = new String(Base64.getDecoder().decode(encoded)).split(":", -1);

        if (decoded.length != 2)
            return ServiceStatus.INVALID_ENTITY;

        return new CredentialsBuilder()
                .identifier(decoded[0])
                .secret(decoded[1])
                .build();
    }
}

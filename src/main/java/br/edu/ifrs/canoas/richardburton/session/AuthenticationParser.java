package br.edu.ifrs.canoas.richardburton.session;

import java.util.Base64;

public class AuthenticationParser {

    private static String BEARER_PREFIX = "Bearer ";
    private static String BASIC_PREFIX = "Basic ";

    public static String parseBearer(String auth) throws AuthenticationParseException {

        if (auth == null)
            throw new AuthenticationParseException("Missing authorization header.");

        if (!auth.startsWith(BEARER_PREFIX))
            throw new AuthenticationParseException("Invalid Basic authorization header.");

        return auth.substring(BEARER_PREFIX.length());
    }

    public static Credentials parseBasic(String auth) throws AuthenticationParseException {

        if (auth == null)
            throw new AuthenticationParseException("Missing authorization header.");

        if (!auth.startsWith(BASIC_PREFIX))
            throw new AuthenticationParseException("Invalid Bearer authorization header.");

        String encoded = auth.substring(BASIC_PREFIX.length());
        String[] decoded = new String(Base64.getDecoder().decode(encoded)).split(":", -1);

        if (decoded.length != 2)
            throw new AuthenticationParseException("Invalid Bearer authorization header.");

        return new Credentials(decoded[0], decoded[1]);
    }
}

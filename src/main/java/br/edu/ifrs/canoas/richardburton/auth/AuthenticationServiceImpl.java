package br.edu.ifrs.canoas.richardburton.auth;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.jboss.resteasy.util.Hex;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Stateless
public class AuthenticationServiceImpl implements AuthenticationService {

    @Inject
    private CredentialsDAO credentialsDAO;

    @Inject
    private CredentialsGroupDAO credentialsGroupDAO;

    @Override
    public void register(Credentials credentials) {
        credentialsDAO.create(digest(credentials));
    }

    @Override
    public void register(CredentialsGroup credentialsGroup) {
        credentialsGroupDAO.create(credentialsGroup);
    }

    @Override
    public Credentials authenticate(Credentials credentials) throws AuthenticationFailedException {

        Credentials registered = credentialsDAO.retrieve(credentials.getIdentifier());

        if(registered == null)
            throw new AuthenticationFailedException("Credentials not registered.");

        if(credentials.getSecret() == null) {
            if (!digest(credentials).equals(registered))
                throw new AuthenticationFailedException("Secret is not correct.");

        } else authenticateBearer(credentials.getToken());

        return registered;
    }

    @Override
    public Credentials refreshToken(Credentials credentials) throws AuthenticationFailedException {
        credentials = authenticate(credentials);
        credentials.setToken(JWT.issueToken(credentials.getIdentifier()));
        return credentialsDAO.update(credentials);
    }

    private void authenticateBearer(String token) throws AuthenticationFailedException {

        try {

            JWT.decodeToken(token);

        } catch (ExpiredJwtException e) {

            throw new AuthenticationFailedException("Token expired.");

        } catch (JwtException e) {

            throw new AuthenticationFailedException("Invalid token");
        }
    }

    private static Credentials digest(Credentials credentials) {

        Credentials digested = credentials.clone();
        digested.setIdentifier(digest(credentials.getSecret()));
        return digested;
    }

    private static String digest(String authenticationString) {

        try {

            MessageDigest md = MessageDigest.getInstance("SHA3-512");
            byte[] bytes = Hex.decodeHex(authenticationString);
            byte[] hashed = md.digest(bytes);
            return Hex.encodeHex(hashed);

        } catch (NoSuchAlgorithmException e) {

            throw new RuntimeException(e);
        }

    }
}

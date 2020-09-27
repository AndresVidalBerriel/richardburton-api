package br.edu.ifrs.canoas.richardburton.auth;

import br.edu.ifrs.canoas.richardburton.DAO;
import br.edu.ifrs.canoas.richardburton.util.ServiceError;
import br.edu.ifrs.canoas.richardburton.util.ServiceResponse;
import br.edu.ifrs.canoas.richardburton.util.ServiceStatus;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.jboss.resteasy.util.Hex;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Stateless
public class AuthenticationServiceImpl implements AuthenticationService {

    @Inject
    private CredentialsDAO credentialsDAO;

    @Override
    public ServiceResponse authenticate(Credentials credentials) {

        Credentials registered = credentialsDAO.retrieve(credentials.getIdentifier());

        if(registered == null)
            return ServiceStatus.NOT_FOUND;

        if(credentials.getSecret() == null) {
            if (!digest(credentials).equals(registered))
                return ServiceStatus.UNAUTHORIZED;

        } else authenticateBearer(credentials.getToken());

        return registered;
    }

    @Override
    public ServiceResponse refreshToken(Credentials credentials) {
        ServiceResponse response = authenticate(credentials);
        if(!response.ok()) return response;

        credentials.setToken(JWT.issueToken(credentials.getIdentifier()));
        return credentialsDAO.update(credentials);
    }

    private ServiceResponse authenticateBearer(String token) {

        try {

            JWT.decodeToken(token);
            return ServiceStatus.OK;

        } catch (ExpiredJwtException e) {

            return ServiceStatus.EXPIRED_ENTITY;

        } catch (JwtException e) {

            return ServiceStatus.INVALID_ENTITY;
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
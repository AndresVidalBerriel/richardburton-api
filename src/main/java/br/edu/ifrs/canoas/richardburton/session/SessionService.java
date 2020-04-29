package br.edu.ifrs.canoas.richardburton.session;

import br.edu.ifrs.canoas.richardburton.users.UserValidationException;
import io.jsonwebtoken.Claims;

import javax.ejb.Local;

@Local
public interface SessionService {

    Session create(Credentials credentials) throws UserValidationException, AuthenticationFailedException;

    String refresh(String token) throws AuthenticationFailedException, AuthenticationParseException;

    Claims authenticate(String token) throws AuthenticationFailedException;

    void authorize(Claims claims, Privileges privileges) throws AuthorizationParseException, AuthorizationFailedException;
}

package br.edu.ifrs.canoas.richardburton.session;

import br.edu.ifrs.canoas.richardburton.users.User;
import br.edu.ifrs.canoas.richardburton.users.UserNotFoundException;
import br.edu.ifrs.canoas.richardburton.users.UserService;
import br.edu.ifrs.canoas.richardburton.users.UserValidationException;
import io.jsonwebtoken.*;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class SessionServiceImpl implements SessionService {

    @Inject
    private UserService userService;

    @Override
    public Session create(Credentials credentials) throws UserValidationException, AuthenticationFailedException {

        try {

            User user = userService.retrieve(credentials.getId());

            if(user.getAuthenticationString().equals(Session.digest(credentials.getAuthenticationString()))) {

                return new Session(JWT.issueToken(user.getEmail(), user.isAdmin()), user);

            } else {

                throw new AuthenticationFailedException("Invalid credentials.");
            }

        } catch (UserNotFoundException e) {

            throw new AuthenticationFailedException("Invalid credentials.");

        }
    }

    @Override
    public String refresh(String token) throws AuthenticationFailedException, AuthenticationParseException {

        try {

            Claims claims = authenticate(token);
            User user = userService.retrieve(claims.getSubject());
            return JWT.issueToken(user.getEmail(), (boolean) claims.get("admin"));

        } catch (UserNotFoundException | UserValidationException e) {

            throw new AuthenticationFailedException("Invalid token.");

        } catch (MissingClaimException e) {

            throw new AuthenticationParseException("Malformed token.");
        }
    }

    @Override
    public Claims authenticate(String token) throws AuthenticationFailedException {

        try {

            return JWT.decodeToken(token);

        } catch (ExpiredJwtException e) {

            throw new AuthenticationFailedException("Token expired.");

        } catch (JwtException e) {

            throw new AuthenticationFailedException("Invalid token");

        }
    }

    @Override
    public void authorize(Claims claims, Privileges privileges) throws AuthorizationParseException, AuthorizationFailedException {

        try {

            boolean admin = (boolean) claims.get("admin");
            boolean requireAdmin = privileges == Privileges.ADMINISTRATOR;

            if(requireAdmin && !admin) throw new AuthorizationFailedException("User does not have access privileges.");

        } catch(MissingClaimException e) {

            throw new AuthorizationParseException("Malformed token.");
        }
    }


}

package br.edu.ifrs.canoas.richardburton.session;

import br.edu.ifrs.canoas.richardburton.auth.AuthenticationFailedException;
import br.edu.ifrs.canoas.richardburton.auth.AuthenticationParseException;
import br.edu.ifrs.canoas.richardburton.auth.Credentials;
import br.edu.ifrs.canoas.richardburton.users.UserNotFoundException;
import br.edu.ifrs.canoas.richardburton.users.UserValidationException;

import javax.ejb.Local;

@Local
public interface SessionService {

    Session create(Credentials credentials) throws UserValidationException, AuthenticationFailedException, UserNotFoundException;

}

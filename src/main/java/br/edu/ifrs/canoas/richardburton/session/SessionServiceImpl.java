package br.edu.ifrs.canoas.richardburton.session;

import br.edu.ifrs.canoas.richardburton.auth.*;
import br.edu.ifrs.canoas.richardburton.users.User;
import br.edu.ifrs.canoas.richardburton.users.UserNotFoundException;
import br.edu.ifrs.canoas.richardburton.users.UserService;
import br.edu.ifrs.canoas.richardburton.users.UserValidationException;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class SessionServiceImpl implements SessionService {

    @Inject
    private UserService userService;

    @Inject
    private AuthenticationService authenticationService;

    @Override
    public Session create(Credentials credentials) throws AuthenticationFailedException, UserValidationException, UserNotFoundException {

        credentials = authenticationService.authenticate(credentials);
        User user = userService.retrieve(credentials.getIdentifier());
        return new Session(credentials.getToken(), user);
    }
}

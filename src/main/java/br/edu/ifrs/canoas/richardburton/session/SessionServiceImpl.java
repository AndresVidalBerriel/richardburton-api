package br.edu.ifrs.canoas.richardburton.session;

import br.edu.ifrs.canoas.richardburton.auth.*;
import br.edu.ifrs.canoas.richardburton.users.User;
import br.edu.ifrs.canoas.richardburton.users.UserService;
import br.edu.ifrs.canoas.richardburton.util.ServiceResponse;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.security.Provider;

@Stateless
public class SessionServiceImpl implements SessionService {

    @Inject
    private UserService userService;

    @Inject
    private AuthenticationService authenticationService;

    @Override
    public ServiceResponse create(Credentials credentials) {

        ServiceResponse response = authenticationService.authenticate(credentials);

        if(response.ok()) {

            credentials = (Credentials) response;
            response = userService.retrieve(credentials.getIdentifier());
            return response.ok()
              ? new Session(credentials.getToken(), (User) response)
              : response;

        } else return response;
    }
}

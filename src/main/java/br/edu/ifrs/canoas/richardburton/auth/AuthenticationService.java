package br.edu.ifrs.canoas.richardburton.auth;

import br.edu.ifrs.canoas.richardburton.util.ServiceResponse;

import javax.ejb.Local;
import java.util.List;

@Local
public interface AuthenticationService {

    ServiceResponse authenticate(Credentials credentials);

    ServiceResponse refreshToken(Credentials credentials);
}

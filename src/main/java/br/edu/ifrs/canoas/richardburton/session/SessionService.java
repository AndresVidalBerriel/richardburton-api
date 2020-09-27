package br.edu.ifrs.canoas.richardburton.session;

import br.edu.ifrs.canoas.richardburton.auth.Credentials;
import br.edu.ifrs.canoas.richardburton.util.ServiceResponse;

import javax.ejb.Local;

@Local
public interface SessionService {

    ServiceResponse create(Credentials credentials);

}

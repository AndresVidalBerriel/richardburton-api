package br.edu.ifrs.canoas.richardburton.auth;

import br.edu.ifrs.canoas.richardburton.util.ServiceResponse;

import javax.ejb.Local;
import java.util.List;

@Local
public interface AuthenticationService {

    ServiceResponse register(Credentials credentials);

    ServiceResponse register(CredentialsGroup group);

    ServiceResponse deleteCredentials(String identifier);

    ServiceResponse deleteCredentialsGroup(String name);

    ServiceResponse authenticate(Credentials credentials);

    ServiceResponse refreshToken(Credentials credentials);

    List<CredentialsGroup> retrieveGroups();
}

package br.edu.ifrs.canoas.richardburton.auth;

import javax.ejb.Local;

@Local
public interface AuthenticationService {

    void register(Credentials credentials);

    void register(CredentialsGroup credentialsGroup);

    Credentials authenticate(Credentials credentials) throws AuthenticationFailedException;

    Credentials refreshToken(Credentials credentials) throws AuthenticationFailedException;
}

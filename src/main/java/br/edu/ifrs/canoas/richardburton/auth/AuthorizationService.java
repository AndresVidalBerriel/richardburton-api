package br.edu.ifrs.canoas.richardburton.auth;

import javax.ejb.Local;
import java.util.EnumSet;

@Local
public interface AuthorizationService {

    void authorize(Credentials credentials, EnumSet<Permissions> requiredPermissions) throws AuthorizationException;
}

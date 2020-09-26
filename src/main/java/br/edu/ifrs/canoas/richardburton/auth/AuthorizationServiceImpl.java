package br.edu.ifrs.canoas.richardburton.auth;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.EnumSet;

@Stateless
public class AuthorizationServiceImpl implements AuthorizationService {

    @Inject
    private CredentialsDAO credentialsDAO;

    @Override
    public void authorize(Credentials credentials, EnumSet<Permissions> requiredPermissions) throws AuthorizationException {

        EnumSet<Permissions> grantedPermissions = credentialsDAO.getPermissions(credentials);
        EnumSet<Permissions> missingPermissions = EnumSet.copyOf(requiredPermissions);
        missingPermissions.removeAll(grantedPermissions);

        if(!missingPermissions.isEmpty())
            throw new AuthorizationException(missingPermissions);
    }
}

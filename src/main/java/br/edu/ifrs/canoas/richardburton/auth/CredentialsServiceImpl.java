package br.edu.ifrs.canoas.richardburton.auth;

import br.edu.ifrs.canoas.richardburton.EntityServiceImpl;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class CredentialsServiceImpl extends EntityServiceImpl<Credentials, String> implements CredentialsService {

    @Inject
    private CredentialsDAO credentialsDAO;

    @Override
    protected CredentialsDAO getDAO() {
        return credentialsDAO;
    }
}

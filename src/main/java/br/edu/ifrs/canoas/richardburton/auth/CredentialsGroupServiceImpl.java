package br.edu.ifrs.canoas.richardburton.auth;

import br.edu.ifrs.canoas.richardburton.EntityServiceImpl;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class CredentialsGroupServiceImpl extends EntityServiceImpl<CredentialsGroup, String> implements CredentialsGroupService{

    @Inject
    private CredentialsGroupDAO credentialsGroupDAO;

    @Override
    protected CredentialsGroupDAO getDAO() {
        return credentialsGroupDAO;
    }
}

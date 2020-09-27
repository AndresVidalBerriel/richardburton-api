package br.edu.ifrs.canoas.richardburton.auth;

import br.edu.ifrs.canoas.richardburton.EntityService;

import javax.ejb.Local;

@Local
public interface CredentialsGroupService extends EntityService<CredentialsGroup, String> {
}

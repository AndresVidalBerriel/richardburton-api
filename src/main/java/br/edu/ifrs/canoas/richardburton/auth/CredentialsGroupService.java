package br.edu.ifrs.canoas.richardburton.auth;

import br.edu.ifrs.canoas.richardburton.EntityService;
import br.edu.ifrs.canoas.richardburton.util.ServiceResponse;

import javax.ejb.Local;

@Local
public interface CredentialsGroupService extends EntityService<CredentialsGroup, String> {

    ServiceResponse addMember(String groupName, String memberId);

    ServiceResponse removeMember(String groupName, String memberId);
}

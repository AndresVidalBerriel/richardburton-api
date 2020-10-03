package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.auth.CredentialsGroupService;
import br.edu.ifrs.canoas.richardburton.util.ServiceResponse;

import javax.ejb.Local;

@Local
public interface UserGroupService extends CredentialsGroupService {

    ServiceResponse addMember(String groupName, Long userId);

    ServiceResponse removeMember(String groupName, Long userId);
}

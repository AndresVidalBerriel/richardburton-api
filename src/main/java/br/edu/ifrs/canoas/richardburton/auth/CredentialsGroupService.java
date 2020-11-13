package br.edu.ifrs.canoas.richardburton.auth;

import br.edu.ifrs.canoas.richardburton.EntityService;
import br.edu.ifrs.canoas.richardburton.util.ServiceResponse;

import javax.ejb.Local;
import java.util.List;
import java.util.Set;

@Local
public interface CredentialsGroupService extends EntityService<CredentialsGroup, String> {

    ServiceResponse addMember(String groupName, String memberId);

    ServiceResponse removeMember(String groupName, String memberId);

    List<String> getNames();

    ServiceResponse setPermissions(String groupName, Set<Permissions> permissions);
}

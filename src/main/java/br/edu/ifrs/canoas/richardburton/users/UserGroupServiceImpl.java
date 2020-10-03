package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.auth.CredentialsGroupServiceImpl;
import br.edu.ifrs.canoas.richardburton.util.ServiceResponse;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UserGroupServiceImpl extends CredentialsGroupServiceImpl implements UserGroupService {

    @Inject
    private UserService userService;

    @Override
    public ServiceResponse addMember(String groupName, Long userId) {

        ServiceResponse response = userService.getEmail(userId);
        return response.ok()
          ? super.addMember(groupName, response.unwrap(String.class))
          : response;
    }

    @Override
    public ServiceResponse removeMember(String groupName, Long userId) {

        ServiceResponse response = userService.getEmail(userId);
        return response.ok()
          ? super.removeMember(groupName, response.unwrap(String.class))
          : response;
    }
}

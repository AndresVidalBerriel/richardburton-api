package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.auth.AuthenticationService;
import br.edu.ifrs.canoas.richardburton.auth.CredentialsGroup;
import br.edu.ifrs.canoas.richardburton.auth.CredentialsGroupService;
import br.edu.ifrs.canoas.richardburton.util.ServiceResponse;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

public class UserGroupResourceImpl implements UserGroupResource {

    @Inject
    private UserGroupService userGroupService;

    @Override
    public Response retrieve() {
        List<CredentialsGroup> groups = userGroupService.retrieve();
        return Response.ok(groups).build();
    }

    @Override
    public Response create(CredentialsGroup group) {

        ServiceResponse response = userGroupService.create(group);
        return Response.status(response.status().toHttpStatus()).build();
    }

    @Override
    public Response delete(String name) {
        ServiceResponse response = userGroupService.delete(name);
        return Response.status(response.status().toHttpStatus()).build();
    }

    @Override
    public Response addMember(String groupName, Long userId) {
        ServiceResponse response = userGroupService.addMember(groupName, userId);
        return Response.status(response.status().toHttpStatus()).build();
    }

    @Override
    public Response removeMember(String groupName, Long userId) {
        ServiceResponse response = userGroupService.removeMember(groupName, userId);
        return Response.status(response.status().toHttpStatus()).build();
    }
}

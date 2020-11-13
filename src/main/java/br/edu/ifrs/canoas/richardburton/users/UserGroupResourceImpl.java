package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.auth.CredentialsGroup;
import br.edu.ifrs.canoas.richardburton.auth.Permissions;
import br.edu.ifrs.canoas.richardburton.util.ServiceResponse;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

public class UserGroupResourceImpl implements UserGroupResource {

    @Inject
    private UserGroupService userGroupService;

    @Override
    public Response retrieve() {
        List<CredentialsGroup> groups = userGroupService.retrieve();
        return Response.ok(groups).build();
    }

    @Override
    public Response retrieveNames() {
        List<String> names = userGroupService.getNames();
        return Response.ok(names).build();
    }

    @Override
    public Response create(CredentialsGroup group) {

        ServiceResponse response = userGroupService.create(group);
        return Response.status(response.status().toHttpStatus()).build();
    }

    @Override
    public Response retrieve(String name) {
        ServiceResponse response = userGroupService.retrieve(name);
        return response.ok()
          ? Response.ok(response).build()
          : Response.status(response.status().toHttpStatus()).build();
    }

    @Override
    public Response delete(String name) {
        ServiceResponse response = userGroupService.delete(name);
        return Response.status(response.status().toHttpStatus()).build();
    }

    @Override
    public Response addMember(String groupName, UserIdentifier userIdentifier) {

        ServiceResponse response = userGroupService.addMember(
          groupName,
          userIdentifier.getEmail()
        );

        return response.ok()
          ? Response.status(Response.Status.NO_CONTENT).build()
          : Response.status(response.status().toHttpStatus()).build();
    }

    @Override
    public Response removeMember(String groupName, String userEmail) {
        ServiceResponse response = userGroupService.removeMember(groupName, userEmail);
        return response.ok()
          ? Response.status(Response.Status.NO_CONTENT).build()
          : Response.status(response.status().toHttpStatus()).build();
    }

    @Override
    public Response setPermissions(String groupName, Set<Permissions> permissions) {
        ServiceResponse response = userGroupService.setPermissions(groupName, permissions);
        return response.ok()
          ? Response.status(Response.Status.NO_CONTENT).build()
          : Response.status(response.status().toHttpStatus()).build();
    }
}

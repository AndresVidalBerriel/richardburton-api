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
    private CredentialsGroupService credentialsGroupService;

    @Override
    public Response retrieve() {
        List<CredentialsGroup> groups = credentialsGroupService.retrieve();
        return Response.ok(groups).build();
    }

    @Override
    public Response create(CredentialsGroup group) {

        ServiceResponse response = credentialsGroupService.create(group);
        return Response.status(response.status().toHttpStatus()).build();
    }

    @Override
    public Response delete(String name) {
        ServiceResponse response = credentialsGroupService.delete(name);
        return Response.status(response.status().toHttpStatus()).build();
    }
}

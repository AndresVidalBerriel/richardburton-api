package br.edu.ifrs.canoas.richardburton.auth;

import javax.ws.rs.core.Response;

public class PermissionsResourceImpl implements PermissionsResource {

    @Override
    public Response retrieve() {
        return Response.ok(Permissions.values()).build();
    }
}

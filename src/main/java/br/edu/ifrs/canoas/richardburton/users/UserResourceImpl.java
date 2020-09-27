package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.util.ServiceResponse;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
@Stateless
public class UserResourceImpl implements UserResource {

    @Inject
    private UserService userService;

    @Override
    public Response retrieve() {

        List<User> users = userService.retrieve();
        return Response.status(Response.Status.OK).entity(users).build();
    }

    @Override
    public Response retrieve(Long id) {

        ServiceResponse response = userService.retrieve(id);

        switch(response.status()) {
            case NOT_FOUND:
                return Response
                  .status(Response.Status.NOT_FOUND)
                  .build();

            case OK:
                return Response
                  .ok(response.entity())
                  .build();

            default:
                return Response
                  .status(response.status().toHttpStatus())
                  .build();
        }
    }
}
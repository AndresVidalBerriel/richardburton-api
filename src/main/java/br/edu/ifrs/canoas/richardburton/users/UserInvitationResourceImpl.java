package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.util.ServiceResponse;
import br.edu.ifrs.canoas.richardburton.util.ServiceStatus;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

public class UserInvitationResourceImpl implements UserInvitationResource {

    @Inject
    private UserInvitationService userInvitationService;

    @Override
    public Response retrieve() {
        List<UserInvitation> invitations = userInvitationService.retrieve();
        return Response.ok(invitations).build();
    }

    @Override
    public Response create(UserInvitation invitation) {
        ServiceResponse response = userInvitationService.create(invitation);
        return Response
          .status(response.status().toHttpStatus())
          .entity(response.entity())
          .build();
    }

    @Override
    public Response retrieve(String email) {
        ServiceResponse response = userInvitationService.retrieve(email);

        if(response.ok()) {
            UserInvitation invitation = (UserInvitation) response;
            if(invitation.expired()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
        return Response
          .status(response.status().toHttpStatus())
          .entity(response.entity())
          .build();
    }

    @Override
    public Response refresh(String email) {
        ServiceResponse response = userInvitationService.refresh(email);
        return Response
          .status(response.status().toHttpStatus())
          .entity(response.entity())
          .build();
    }

    @Override
    public Response confirm(User user, String password) {
        ServiceResponse response = userInvitationService.confirm(user, password);

        if(response.status() == ServiceStatus.EXPIRED_ENTITY) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        return Response
          .status(response.status().toHttpStatus())
          .entity(response.entity())
          .build();
    }

    @Override
    public Response cancel(String email) {
        ServiceResponse response = userInvitationService.cancel(email);
        return Response
          .status(response.status().toHttpStatus())
          .entity(response.entity())
          .build();
    }
}

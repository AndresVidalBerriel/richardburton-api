package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.EntityServiceImpl;
import br.edu.ifrs.canoas.richardburton.RichardBurton;
import br.edu.ifrs.canoas.richardburton.util.ServiceResponse;
import br.edu.ifrs.canoas.richardburton.util.ServiceStatus;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;

@Stateless
public class UserInvitationServiceImpl extends EntityServiceImpl<UserInvitation, String> implements UserInvitationService {

    static long invitationTTLMinutes = (long) Math.ceil(RichardBurton.getUserInvitationTTLHours()*60);

    @Inject
    private UserInvitationDAO userInvitationDAO;

    @Inject
    private UserService userService;

    @Override
    protected UserInvitationDAO getDAO() {
        return userInvitationDAO;
    }

    @Override
    public ServiceResponse create(UserInvitation invitation) {
        invitation.setExpiresAt(invitation.getCreatedAt().plusMinutes(invitationTTLMinutes));
        return super.create(invitation);
    }

    @Override
    public ServiceResponse refresh(String email) {
        ServiceResponse response = retrieve(email);
        if(!response.ok()) return response;

        UserInvitation invitation = (UserInvitation) response;
        invitation.setExpiresAt(LocalDateTime.now().plusMinutes(invitationTTLMinutes));
        invitation.setStatus(UserInvitation.Status.ISSUED);
        return userInvitationDAO.update(invitation);
    }

    @Override
    public ServiceResponse cancel(String email) {
        ServiceResponse response = retrieve(email);
        if(!response.ok()) return response;

        UserInvitation invitation = (UserInvitation) response;
        invitation.setStatus(UserInvitation.Status.CANCELLED);
        return userInvitationDAO.update(invitation);
    }

    @Override
    public ServiceResponse confirm(User user, String password) {
        ServiceResponse response = retrieve(user.getEmail());
        if(!response.ok()) return response;
        UserInvitation invitation = (UserInvitation) response;
        if(invitation.expired()) return ServiceStatus.EXPIRED_ENTITY;

        response  = userService.create(user, password);
        if(!response.ok()) return response;
        User createdUser = (User) response;

        invitation.setStatus(UserInvitation.Status.CONFIRMED);
        response = userInvitationDAO.update(invitation);

        return response.ok() ? createdUser : response;
    }
}

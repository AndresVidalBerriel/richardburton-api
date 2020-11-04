package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.EntityService;
import br.edu.ifrs.canoas.richardburton.util.ServiceResponse;

import javax.ejb.Local;

@Local
public interface UserInvitationService extends EntityService<UserInvitation, String> {

    ServiceResponse refresh(String email);

    ServiceResponse cancel(String email);

    ServiceResponse confirm(User user, String password);
}

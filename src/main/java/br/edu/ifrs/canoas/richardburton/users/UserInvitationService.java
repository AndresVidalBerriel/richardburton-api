package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.EntityService;

import javax.ejb.Local;

@Local
public interface UserInvitationService extends EntityService<UserInvitation, String> {
}

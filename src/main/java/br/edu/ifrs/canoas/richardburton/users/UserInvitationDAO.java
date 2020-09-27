package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.DAO;

import javax.ejb.Local;


@Local
public interface UserInvitationDAO extends DAO<UserInvitation, String> {
}

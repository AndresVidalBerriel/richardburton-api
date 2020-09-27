package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.DAOImpl;

import javax.ejb.Stateless;

@Stateless
public class UserInvitationDAOImpl extends DAOImpl<UserInvitation, String> implements  UserInvitationDAO{
}

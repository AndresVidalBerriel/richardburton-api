package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.DAO;
import br.edu.ifrs.canoas.richardburton.EntityServiceImpl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import java.util.Set;

@Stateless
public class UserInvitationServiceImpl extends EntityServiceImpl<UserInvitation, String> implements UserInvitationService {

    @Inject
    private UserInvitationDAO userInvitationDAO;

    @Override
    protected UserInvitationDAO getDAO() {
        return userInvitationDAO;
    }

}

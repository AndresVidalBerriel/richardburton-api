package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.DAO;
import br.edu.ifrs.canoas.richardburton.EntityServiceImpl;
import br.edu.ifrs.canoas.richardburton.util.ServiceResponse;
import br.edu.ifrs.canoas.richardburton.util.ServiceStatus;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.regex.Pattern;

@Stateless
public class UserServiceImpl extends EntityServiceImpl<User, Long> implements UserService {

    @Inject
    private UserDAO userDAO;

    @Override
    protected DAO<User, Long> getDAO() {
        return userDAO;
    }

    @Override
    public ServiceResponse retrieve(String email) {

        if (email == null || !Pattern.matches(User.EMAIL_FORMAT, email))
            return ServiceStatus.INVALID_ENTITY;

        User user = userDAO.retrieve(email);
        return user == null ? ServiceStatus.NOT_FOUND : user;
    }

}
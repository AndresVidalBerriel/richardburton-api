package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.DAO;
import br.edu.ifrs.canoas.richardburton.EntityServiceImpl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import java.util.Set;
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
    protected void throwValidationException(Set<ConstraintViolation<User>> violations) throws UserValidationException {
        throw new UserValidationException(violations);
    }

    @Override
    public User retrieve(String email) throws UserValidationException, UserNotFoundException {

        if (email == null) {

            throw new UserValidationException("No email was provided.");

        } if (!Pattern.matches(User.EMAIL_FORMAT, email)) {

            throw new UserValidationException("The format of the provided email " + email + " is not in correct.");
        }

        User user = userDAO.retrieve(email);
        if(user != null) return user;
        else throw new UserNotFoundException("No user found with the email " + email);
    }
}
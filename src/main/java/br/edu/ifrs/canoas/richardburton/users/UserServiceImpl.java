package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.DAO;
import br.edu.ifrs.canoas.richardburton.DuplicateEntityException;
import br.edu.ifrs.canoas.richardburton.EntityServiceImpl;
import br.edu.ifrs.canoas.richardburton.EntityValidationException;
import br.edu.ifrs.canoas.richardburton.session.Session;

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

    public User create(User user) throws DuplicateEntityException, EntityValidationException {

        if(user == null) {

            throw new UserValidationException("An user must be provided");
        }

        try {

            retrieve(user.getEmail());
            throw new DuplicateUserException("An user with the provided email address " + user.getEmail() + " has already been registered");

        } catch (UserNotFoundException e) {

            /*
            if(user.getAuthenticationString() == null || user.getAuthenticationString().trim().isEmpty()) {

                throw new UserValidationException("The authentication string must not be null, empty or blank");
            }
            */

            user = super.create(user);
            user.setAuthenticationString(Session.digest(user.getAuthenticationString()));
            return user;
        }
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
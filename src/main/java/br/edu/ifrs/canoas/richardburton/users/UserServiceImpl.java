package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.DAO;
import br.edu.ifrs.canoas.richardburton.DuplicateEntityException;
import br.edu.ifrs.canoas.richardburton.EntityServiceImpl;
import br.edu.ifrs.canoas.richardburton.EntityValidationException;
import br.edu.ifrs.canoas.richardburton.util.Strings;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.regex.Pattern;

@Stateless
public class UserServiceImpl extends EntityServiceImpl<User, Long> implements UserService {

    @Inject
    private UserDAO userDAO;

    @Override
    protected DAO<User, Long> getDAO() {
        return null;
    }

    @Override
    protected void throwValidationException(Set<ConstraintViolation<User>> violations) throws UserValidationException {
        throw new UserValidationException(violations);
    }

    @Override
    public User authenticate(String email, String authenticationString) throws UserValidationException {

        User user = retrieve(email);
        boolean authentic = user != null && user.getAuthenticationString().equals(Strings.digest(authenticationString));
        return authentic ? user : null;
    }

    @Override
    public User create(User user) throws DuplicateEntityException, EntityValidationException {

        if (retrieve(user.getEmail()) != null) {

            throw new DuplicateUserException("An user with the provided email address has already been registered");
        }

        user.setAuthenticationString(Strings.digest(user.getAuthenticationString()));
        return super.create(user);
    }

    @Override
    public User retrieve(String email) throws UserValidationException {

        if (!Pattern.matches(User.EMAIL_FORMAT, email)) {

            throw new UserValidationException("The provided email's format is not correct.");
        }

        return userDAO.retrieve(email);
    }
}
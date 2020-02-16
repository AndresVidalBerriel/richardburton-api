package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.util.Strings;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.regex.Pattern;

@Stateless
public class UserServiceImpl implements UserService {

    @Inject
    private UserDAO userDAO;

    @Override
    public User authenticate(String email, String authenticationString) throws EmailFormatException {

        User user = retrieve(email);
        boolean authentic = user != null && user.getAuthenticationString().equals(Strings.digest(authenticationString));
        return authentic ? user : null;
    }

    @Override
    public User create(User user) throws ConstraintViolationException, EmailNotUniqueException {

        if (userDAO.retrieve(user.getEmail()) != null) {

            throw new EmailNotUniqueException("An user with the provided email address has already been registered");
        }

        user.setAuthenticationString(Strings.digest(user.getAuthenticationString()));
        return userDAO.create(user);
    }

    @Override
    public List<User> retrieve() {

        return userDAO.retrieve();
    }

    @Override
    public User retrieve(Long id) {

        return userDAO.retrieve(id);
    }

    @Override
    public User retrieve(String email) throws EmailFormatException, NoResultException {

        if (!Pattern.matches(User.EMAIL_FORMAT, email)) {

            throw new EmailFormatException("The provided email's format is not correct.");
        }

        return userDAO.retrieve(email);
    }
}
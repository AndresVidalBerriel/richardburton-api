package br.edu.ifrs.canoas.transnacionalidades.richardburton.services;

import java.util.List;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolationException;

import br.edu.ifrs.canoas.transnacionalidades.richardburton.dao.UserDAO;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.entities.User;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.exceptions.InvalidEmailFormatException;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.exceptions.ResourceAlreadyExistsException;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.util.Strings;

@Stateless
public class UserService {

    @Inject
    private UserDAO userDAO;

    public User authenticate(String email, String authenticationString) throws InvalidEmailFormatException {

        if (!Pattern.matches(User.getEmailformat(), email))
            throw new InvalidEmailFormatException("The provided email's format is not correct.");

        User user = userDAO.retrieve(email);
        boolean authentic = user != null && user.getAuthenticationString().equals(Strings.digest(authenticationString));
        return authentic ? user : null;
    }

    public User create(User user) throws ConstraintViolationException, ResourceAlreadyExistsException {

        if (userDAO.retrieve(user.getEmail()) != null) {
            String errorMessage = "An user has already been registered with that email address.";
            throw new ResourceAlreadyExistsException(errorMessage);
        }

        user.setAuthenticationString(Strings.digest(user.getAuthenticationString()));
        return userDAO.create(user);

    }

    public List<User> retrieveAll() {

        return userDAO.retrieveAll();
    }

    public User retrieve(Long id) {

        return userDAO.retrieve(id);
    }

    public User retrieve(String email) throws InvalidEmailFormatException, NoResultException {

        if (!Pattern.matches(User.EMAIL_FORMAT, email))
            throw new InvalidEmailFormatException("The provided email's format is not correct.");

        return userDAO.retrieve(email);
    }
}
package br.edu.ifrs.canoas.transnacionalidades.richardburton.controllers;

import java.util.List;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

import br.edu.ifrs.canoas.transnacionalidades.richardburton.dao.UserDAO;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.entities.User;

@Stateless
public class UserController {

    @Inject
    private UserDAO userDAO;

    public User authenticate(String email, String authenticationString) {

        User user = userDAO.retrieve(email);
        boolean authentic = user != null && user.getAuthenticationString().equals(authenticationString);
        return authentic ? user : null;
    }

    public User create(User user) throws ConstraintViolationException {

        return userDAO.create(user);
    }

    public List<User> retrieveAll() {

        return userDAO.retrieveAll();
    }

    public User retrieve(String email) throws IllegalArgumentException {

        if (Pattern.matches(User.getEmailformat(), email))
            throw new IllegalArgumentException("The provided email's format is not correct.");

        return userDAO.retrieve(email);
    }
}
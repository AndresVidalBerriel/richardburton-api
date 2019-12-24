package br.edu.ifrs.canoas.transnacionalidades.richardburton.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

import br.edu.ifrs.canoas.transnacionalidades.richardburton.dao.UserDAO;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.entities.User;

@Stateless
public class UserService {

    @Inject
    private UserDAO userDAO;

    public User authenticate(String email, String authenticationString) {

        User user = userDAO.retrieve(email);
        boolean authentic = user != null && user.getAuthenticationString().equals(authenticationString);
        return authentic ? user : null;
    }

    public User create(User user) throws ConstraintViolationException {

        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] currentAuthenticationString = user.getAuthenticationString().getBytes();
            byte[] hashedAuthenticationString = md.digest(currentAuthenticationString);
            user.setAuthenticationString(new String(hashedAuthenticationString));
            return userDAO.create(user);

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
            return null;
        }

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
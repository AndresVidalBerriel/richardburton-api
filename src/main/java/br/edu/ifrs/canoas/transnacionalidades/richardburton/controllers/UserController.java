package br.edu.ifrs.canoas.transnacionalidades.richardburton.controllers;

import java.util.List;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;

import br.edu.ifrs.canoas.transnacionalidades.richardburton.dao.UserDAO;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.entities.User;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.util.http.RBHttpStatus;

@Stateless
public class UserController {

    @Inject
    private UserDAO userDAO;

    public Response create(User user) {

        Response response;
        try {
            user = userDAO.create(user);
            response = Response.status(Response.Status.CREATED).entity(user).build();
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            response = Response.status(RBHttpStatus.UNPROCESSABLE_ENTITY).entity(e).build();
        }
        return response;
    }

    public Response retrieveAll() {

        Response response;
        List<User> users = userDAO.retrieveAll();
        response = Response.status(Response.Status.OK).entity(users).build();

        return response;
    }

    public Response retrieve(String email) {

        Response response;

        if (Pattern.matches(User.getEmailformat(), email)) {

            User user = userDAO.retrieve(email);
            response = Response.status(Response.Status.OK).entity(user).build();

        } else {

            response = Response.status(Response.Status.BAD_REQUEST).build();
        }

        return response;
    }
}
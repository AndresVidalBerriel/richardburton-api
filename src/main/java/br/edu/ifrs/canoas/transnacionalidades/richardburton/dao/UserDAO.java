package br.edu.ifrs.canoas.transnacionalidades.richardburton.dao;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.edu.ifrs.canoas.transnacionalidades.richardburton.entities.User;

@Stateless
public class UserDAO extends BaseDAO<User, Long> {

    private static final long serialVersionUID = 1L;

    public User retrieve(String email) {

        try {

            String queryString = "SELECT user FROM User user WHERE user.email = :email";
            TypedQuery<User> query = em.createQuery(queryString, User.class);
            query.setParameter("email", email);
            return query.getSingleResult();

        } catch (NoResultException e) {

            return null;
        }
    }

}
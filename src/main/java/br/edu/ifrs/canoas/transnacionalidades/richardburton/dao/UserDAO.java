package br.edu.ifrs.canoas.transnacionalidades.richardburton.dao;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.edu.ifrs.canoas.transnacionalidades.richardburton.entities.User;

@Stateless
public class UserDAO extends BaseDAO<User, Long> {

    private static final long serialVersionUID = 1L;

    public User retrieve(String email) {

        try {

            Query query = em.createQuery("SELECT user FROM User user WHERE user.email = :email");
            query.setParameter("email", email);
            return (User) query.getSingleResult();

        } catch (NoResultException e) {

            return null;
        }
    }

}
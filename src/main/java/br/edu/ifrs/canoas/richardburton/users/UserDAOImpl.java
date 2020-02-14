package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.DAOImpl;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Stateless
public class UserDAOImpl extends DAOImpl<User, Long> implements UserDAO {

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
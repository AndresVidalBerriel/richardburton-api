package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.DAOImpl;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
public class UserDAOImpl extends DAOImpl<User, Long> implements UserDAO {

    private static final long serialVersionUID = 1L;

    public User retrieve(String email) {

        try {

            TypedQuery<User> query = em.createNamedQuery("User.retrieveByEmail", User.class);
            query.setParameter("email", email);
            return query.getSingleResult();

        } catch (NoResultException e) {

            return null;
        }
    }

    @Override
    public String getEmail(Long id) {
        try {

            Query query = em.createNamedQuery("User.getEmailById");
            query.setParameter("id", id);
            return (String) query.getSingleResult();

        } catch (NoResultException e) {

            return null;
        }
    }

}
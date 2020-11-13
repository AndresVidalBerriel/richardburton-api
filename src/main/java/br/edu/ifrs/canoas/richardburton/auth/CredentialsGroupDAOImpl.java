package br.edu.ifrs.canoas.richardburton.auth;

import br.edu.ifrs.canoas.richardburton.DAOImpl;

import javax.persistence.TypedQuery;
import java.util.List;

public class CredentialsGroupDAOImpl extends DAOImpl<CredentialsGroup, String> implements CredentialsGroupDAO {

    @Override
    public List<String> getNames() {

        TypedQuery<String> query = em.createNamedQuery("CredentialsGroup.getNames", String.class);
        return query.getResultList();
    }
}

package br.edu.ifrs.canoas.richardburton.auth;

import br.edu.ifrs.canoas.richardburton.DAOImpl;

import javax.persistence.TypedQuery;
import java.util.EnumSet;
import java.util.List;

public class CredentialsDAOImpl extends DAOImpl<Credentials, String> implements CredentialsDAO {

    @Override
    public EnumSet<Permissions> getPermissions(Credentials credentials) {

        EnumSet<Permissions> permissions = EnumSet.noneOf(Permissions.class);

        List<String> queryNames = List.of(
          "Credentials.getDirectPermissions",
          "Credentials.getGroupPermissions"
        );

        for (String queryName: queryNames) {

            TypedQuery<Permissions> query = em.createNamedQuery(queryName, Permissions.class);
            query.setParameter("identifier", credentials.getIdentifier());
            permissions.addAll(query.getResultList());
        }

        return permissions;
    }
}

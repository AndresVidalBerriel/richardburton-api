package br.edu.ifrs.canoas.richardburton.auth;

import br.edu.ifrs.canoas.richardburton.DAO;

import java.util.EnumSet;

public interface CredentialsDAO extends DAO<Credentials, String> {

    EnumSet<Permissions> getPermissions(Credentials credentials);
}

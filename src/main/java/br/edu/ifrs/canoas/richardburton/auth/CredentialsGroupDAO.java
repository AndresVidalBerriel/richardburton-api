package br.edu.ifrs.canoas.richardburton.auth;

import br.edu.ifrs.canoas.richardburton.DAO;

import java.util.List;

public interface CredentialsGroupDAO extends DAO<CredentialsGroup, String> {

    List<String> getNames();
}

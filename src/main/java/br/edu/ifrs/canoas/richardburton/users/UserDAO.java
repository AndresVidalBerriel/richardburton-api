package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.DAO;

import javax.ejb.Local;

@Local
public interface UserDAO extends DAO<User, Long> {

    User retrieve(String email);

    String getEmail(Long id);
}
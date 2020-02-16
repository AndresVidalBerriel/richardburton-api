package br.edu.ifrs.canoas.richardburton.users;

import javax.ejb.Local;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import java.util.List;

@Local
public interface UserService {

    User authenticate(String email, String authenticationString) throws EmailFormatException;

    User create(@NotNull User user) throws ConstraintViolationException, EmailNotUniqueException;

    List<User> retrieve();

    User retrieve(Long id);

    User retrieve(String email) throws EmailFormatException, NoResultException;
}
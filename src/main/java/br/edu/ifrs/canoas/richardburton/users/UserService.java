package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.DuplicateEntityException;
import br.edu.ifrs.canoas.richardburton.EntityService;
import br.edu.ifrs.canoas.richardburton.EntityValidationException;

import javax.ejb.Local;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import java.util.List;

@Local
public interface UserService extends EntityService<User, Long> {

    User authenticate(String email, String authenticationString) throws UserValidationException;

    User retrieve(String email) throws UserValidationException;
}
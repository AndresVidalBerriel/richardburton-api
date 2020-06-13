package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.EntityService;
import javax.ejb.Local;

@Local
public interface UserService extends EntityService<User, Long> {

    User retrieve(String email) throws UserValidationException, UserNotFoundException;
}
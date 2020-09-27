package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.EntityService;
import br.edu.ifrs.canoas.richardburton.util.ServiceResponse;

import javax.ejb.Local;

@Local
public interface UserService extends EntityService<User, Long> {

    ServiceResponse retrieve(String email);
}
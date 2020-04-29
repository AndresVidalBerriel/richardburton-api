package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String message) {
        super(message);
    }
}

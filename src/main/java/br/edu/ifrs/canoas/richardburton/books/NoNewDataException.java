package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.DuplicateEntityException;

public class NoNewDataException extends DuplicateEntityException {

    private static final long serialVersionUID = 1L;

    public NoNewDataException(String message) {
        super(message);
    }

}
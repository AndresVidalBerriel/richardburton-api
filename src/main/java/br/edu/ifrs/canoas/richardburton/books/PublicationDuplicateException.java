package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.DuplicateEntityException;

public class PublicationDuplicateException extends DuplicateEntityException {

    public PublicationDuplicateException(String message) {
        super(message);
    }
}

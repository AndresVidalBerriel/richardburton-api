package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.DuplicateEntityException;

public class TranslatedBookDuplicateException extends DuplicateEntityException {

    public TranslatedBookDuplicateException(String message) {
        super(message);
    }
}

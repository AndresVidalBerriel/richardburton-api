package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.EntityValidationException;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class TranslatedBookValidationException extends EntityValidationException {

    private final Set<ConstraintViolation<TranslatedBook>> violations;

    public TranslatedBookValidationException(Set<ConstraintViolation<TranslatedBook>> violations) {
        super("Translated book validation failed" + violations.toString());
        this.violations = violations;
    }

    public Set<ConstraintViolation<TranslatedBook>> getViolations() {
        return violations;
    }
}

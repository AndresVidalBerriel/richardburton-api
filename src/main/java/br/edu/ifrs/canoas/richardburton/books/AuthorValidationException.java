package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.EntityValidationException;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class AuthorValidationException extends EntityValidationException {

    private final Set<ConstraintViolation<Author>> violations;

    public AuthorValidationException(Set<ConstraintViolation<Author>> violations) {
        super("Author validation failed" + violations.toString());
        this.violations = violations;
    }

    public Set<ConstraintViolation<Author>> getViolations() {
        return violations;
    }
}

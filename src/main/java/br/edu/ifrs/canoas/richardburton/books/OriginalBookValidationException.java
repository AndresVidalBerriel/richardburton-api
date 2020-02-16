package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.EntityValidationException;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class OriginalBookValidationException extends EntityValidationException {

    private final Set<ConstraintViolation<OriginalBook>> violations;

    public OriginalBookValidationException(Set<ConstraintViolation<OriginalBook>> violations) {
        super("Original book validation failed. Violations: " + violations.toString());
        this.violations = violations;
    }

    public Set<ConstraintViolation<OriginalBook>> getViolations() {
        return violations;
    }
}

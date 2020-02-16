package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.EntityValidationException;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class PublicationValidationException extends EntityValidationException {

    private final Set<ConstraintViolation<Publication>> violations;

    public PublicationValidationException(Set<ConstraintViolation<Publication>> violations) {
        super("Publication validation failed. Violations: " + violations.toString());
        this.violations = violations;
    }

    public Set<ConstraintViolation<Publication>> getViolations() {
        return violations;
    }
}

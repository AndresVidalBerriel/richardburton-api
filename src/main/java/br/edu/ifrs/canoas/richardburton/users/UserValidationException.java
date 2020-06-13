package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.EntityValidationException;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class UserValidationException extends EntityValidationException {

    private static final long serialVersionUID = 1L;

    private final Set<ConstraintViolation<User>> violations;

    public UserValidationException(String message) {
        super(message);
        this.violations = null;
    }

    public UserValidationException(Set<ConstraintViolation<User>> violations) {
        super("Author validation failed" + violations.toString());
        this.violations = violations;
    }

    public Set<ConstraintViolation<User>> getViolations() {
        return violations;
    }
}

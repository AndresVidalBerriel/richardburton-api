package br.edu.ifrs.canoas.richardburton.tests.users;

import br.edu.ifrs.canoas.richardburton.users.User;
import br.edu.ifrs.canoas.richardburton.util.Strings;
import org.jboss.resteasy.util.Hex;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.*;

import java.util.Set;

import static org.junit.Assert.*;

public class UserValidationTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    private User user;

    @BeforeClass
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterClass
    public static void close() {
        validatorFactory.close();
    }

    @Before
    public void init() {

        String authenticationString = Strings.digest(Hex.encodeHex("password".getBytes()));

        user = new User();
        user.setEmail("user@example.com");
        user.setAuthenticationString(authenticationString);
        user.setFirstName("First");
        user.setLastName("Last");
    }

    @Test
    public void validJustRequiredFields() {

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void validWithOptionalFields() {

        user.setOccupation("occupation");
        user.setAffiliation("affiliation");
        user.setNationality("nationality");
        user.setAdmin(true);
        user.setToken("token");
        user.setId(1L);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void invalidBadEmailFormat() {

        user.setEmail("invalid email");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    public void invalidMissingEmail() {

        user.setEmail(null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    public void invalidBlankEmail() {

        user.setEmail("  ");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    public void invalidEmptyEmail() {

        user.setEmail("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    public void invalidMissingAuthenticationString() {

        user.setAuthenticationString(null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    public void invalidBlankAuthenticationString() {

        user.setAuthenticationString("  ");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    public void invalidEmptyAuthenticationString() {

        user.setAuthenticationString("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    public void invalidMissingFirstName() {

        user.setFirstName(null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    public void invalidBlankFirstName() {

        user.setFirstName("  ");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    public void invalidEmptyFirstName() {

        user.setFirstName("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    public void invalidMissingLastName() {

        user.setLastName(null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    public void invalidBlankLastName() {

        user.setLastName("  ");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    public void invalidEmptyLastName() {

        user.setLastName("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }
    
    @Test
    public void invalidBlankNationality() {

        user.setNationality("  ");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    public void invalidEmptyNationality() {

        user.setNationality("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    public void invalidBlankOccupation() {

        user.setOccupation("  ");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    public void invalidEmptyOccupation() {

        user.setOccupation("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    public void invalidBlankAffiliation() {

        user.setAffiliation("  ");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    public void invalidEmptyAffiliation() {

        user.setAffiliation("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

}

package br.edu.ifrs.canoas.richardburton.books;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ISBNConstraintValidator.class)
@Documented
public @interface ISBN {

    String message() default "{br.edu.ifrs.canoas.transnacionalidades.richardburton.annotations.ISBN.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        ISBN[] value();
    }

}
package br.edu.ifrs.canoas.transnacionalidades.richardburton.constraints.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.edu.ifrs.canoas.transnacionalidades.richardburton.constraints.validators.NullOrNotBlankValidator;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NullOrNotBlankValidator.class)
@Documented
public @interface NullOrNotBlank {

    String message() default "{br.edu.ifrs.canoas.transnacionalidades.richardburton.annotations.NullOrNotBLank.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        NullOrNotBlank[] value();
    }

}
package br.edu.ifrs.canoas.transnacionalidades.richardburton.constraints.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.validator.routines.ISBNValidator;

import br.edu.ifrs.canoas.transnacionalidades.richardburton.constraints.annotations.ISBN;

public class ISBNConstraintValidator implements ConstraintValidator<ISBN, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return value == null || ISBNValidator.getInstance().isValid(value);
    }

}
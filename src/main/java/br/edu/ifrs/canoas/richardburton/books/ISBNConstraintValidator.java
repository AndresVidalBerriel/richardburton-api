package br.edu.ifrs.canoas.richardburton.books;

import org.apache.commons.validator.routines.ISBNValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ISBNConstraintValidator implements ConstraintValidator<ISBN, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return value == null || ISBNValidator.getInstance().isValid(value);
    }

}
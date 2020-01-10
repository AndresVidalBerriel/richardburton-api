package br.edu.ifrs.canoas.transnacionalidades.richardburton.constraints.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.edu.ifrs.canoas.transnacionalidades.richardburton.constraints.annotations.NullOrNotBlank;

public class NullOrNotBlankValidator implements ConstraintValidator<NullOrNotBlank, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return value == null || !value.trim().isEmpty();
    }
}
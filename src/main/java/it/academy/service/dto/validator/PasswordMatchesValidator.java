package it.academy.service.dto.validator;

import it.academy.service.dto.AccountDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        AccountDTO accountDTO = (AccountDTO) obj;
        return accountDTO.getPassword().equals(accountDTO.getPasswordConfirm());
    }
}
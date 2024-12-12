package com.example.demo.services.validation;

import com.example.demo.controller.data.FieldMessage;
import com.example.demo.controller.data.requests.UserRequestDTO;
import com.example.demo.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public record InsertUserValidator(UserRepository repository) implements ConstraintValidator<InsertUserValidation, UserRequestDTO> {

    @Override
    public void initialize(InsertUserValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserRequestDTO request, ConstraintValidatorContext constraintValidatorContext) {
        List<FieldMessage> fieldMessages = new ArrayList<>();

        if(accountNumberAlreadyExist(request)) {
            fieldMessages.add(new FieldMessage("accountNumber", "accountNumer " + request.accountNumber() + " já existe"));
        }

        for (FieldMessage e : fieldMessages) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(e.message()).addPropertyNode(e.fieldName())
                    .addConstraintViolation();
        }
        return fieldMessages.isEmpty();
    }

    private boolean accountNumberAlreadyExist(UserRequestDTO request) {
        var accountNumber = request.accountNumber();
        var userOptional = repository.findByAccountNumber(accountNumber);
        return userOptional.isPresent();
    }
}
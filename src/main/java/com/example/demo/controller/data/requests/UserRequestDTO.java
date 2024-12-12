package com.example.demo.controller.data.requests;

import com.example.demo.services.validation.InsertUserValidation;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@InsertUserValidation
public record UserRequestDTO(@NotBlank String name, @NotBlank String accountNumber, @NotNull BigDecimal balance) {
}
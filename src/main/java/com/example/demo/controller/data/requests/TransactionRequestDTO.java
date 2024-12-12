package com.example.demo.controller.data.requests;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransactionRequestDTO(@NotNull BigDecimal amount, @NotNull Long senderId, @NotNull Long receiverId) {
}
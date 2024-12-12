package com.example.demo.controller.data.responses;

import com.example.demo.enums.TransactionStatus;

import java.math.BigDecimal;

public record TransactionResponseDTO(Long id, TransactionStatus status, BigDecimal amount,
                                     UserResponseForTransactionDTO sender, UserResponseForTransactionDTO receiver) {
}
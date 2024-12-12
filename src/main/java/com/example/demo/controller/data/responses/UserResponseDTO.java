package com.example.demo.controller.data.responses;

import java.math.BigDecimal;

public record UserResponseDTO(Long id, String name, String accountNumber, BigDecimal balance) {
}
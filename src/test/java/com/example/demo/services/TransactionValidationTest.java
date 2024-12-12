package com.example.demo.services;

import com.example.demo.entities.Transaction;
import com.example.demo.entities.User;
import com.example.demo.exceptions.InsufficientBalanceException;
import com.example.demo.exceptions.LimitExceededException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionValidationTest {

    @InjectMocks
    private TransactionValidation transactionValidation;

    private User sender;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        sender = new User();
        transaction = new Transaction();
    }

    @Test
    void testExecuteWithSufficientBalance() {
        // Arrange
        sender.setBalance(BigDecimal.valueOf(200.0));
        transaction.setAmount(BigDecimal.valueOf(50.0));

        // Act & Assert
        assertDoesNotThrow(() -> transactionValidation.execute(sender, transaction));
    }

    @Test
    void testExecuteWithInsufficientBalance() {
        // Arrange
        sender.setBalance(BigDecimal.valueOf(50.0));
        transaction.setAmount(BigDecimal.valueOf(100.0));

        // Act & Assert
        assertThrows(InsufficientBalanceException.class,
                () -> transactionValidation.execute(sender, transaction));
    }

    @Test
    void testExecuteWithExceededLimit() {
        // Arrange
        sender.setBalance(BigDecimal.valueOf(200.0));
        transaction.setAmount(BigDecimal.valueOf(150.0));

        // Act & Assert
        assertThrows(LimitExceededException.class,
                () -> transactionValidation.execute(sender, transaction));
    }

    @Test
    void testExecuteWithExactLimit() {
        // Arrange
        sender.setBalance(BigDecimal.valueOf(200.0));
        transaction.setAmount(BigDecimal.valueOf(100.0));

        // Act & Assert
        assertDoesNotThrow(() -> transactionValidation.execute(sender, transaction));
    }
}
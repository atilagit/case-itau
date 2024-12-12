package com.example.demo.services;

import com.example.demo.entities.Transaction;
import com.example.demo.entities.User;
import com.example.demo.enums.TransactionStatus;
import com.example.demo.exceptions.TransactionNotFoundException;
import com.example.demo.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository repository;

    @Mock
    private UserService userService;

    @Mock
    private TransactionValidation transactionValidation;

    @Mock
    private TransactionHelperService transactionHelperService;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() throws Exception {
        try (AutoCloseable ignored = MockitoAnnotations.openMocks(this)) {
            /* O AutoCloseable será fechado automaticamente ao final deste bloco */
        }
    }

    @Test
    void testSaveSuccessful() {
        // Arrange
        User sender = new User(1L, "Sender", "12345", new BigDecimal("1000"));
        User receiver = new User(2L, "Receiver", "67890", new BigDecimal("500"));
        Transaction transaction = new Transaction(null, new BigDecimal("100"), sender, receiver, null, null);

        when(userService.findById(1L)).thenReturn(sender);
        when(userService.findById(2L)).thenReturn(receiver);
        doNothing().when(transactionValidation).execute(sender, transaction);

        // Act
        Transaction result = transactionService.save(transaction);

        // Assert
        assertEquals(TransactionStatus.COMPLETED, result.getStatus());
        assertEquals(new BigDecimal("900"), sender.getBalance());
        assertEquals(new BigDecimal("600"), receiver.getBalance());
        assertNotNull(result.getTimestamp());
        verify(userService, times(2)).save(any(User.class));
        verify(repository).save(transaction);
    }

    @Test
    void testSaveFailed() {
        // Arrange
        User sender = new User(1L, "Sender", "12345", new BigDecimal("1000"));
        User receiver = new User(2L, "Receiver", "67890", new BigDecimal("500"));
        Transaction transaction = new Transaction(null, new BigDecimal("100"), sender, receiver, null, null);

        when(userService.findById(1L)).thenReturn(sender);
        when(userService.findById(2L)).thenReturn(receiver);
        doThrow(new RuntimeException("Test exception")).when(transactionValidation).execute(sender, transaction);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> transactionService.save(transaction));
        assertEquals(TransactionStatus.FAILED, transaction.getStatus());
        verify(transactionHelperService).saveFailedTransaction(transaction);
    }

    @Test
    void testFindTransactionsByUserWithStatus() {
        // Arrange
        Long userId = 1L;
        TransactionStatus status = TransactionStatus.COMPLETED;
        Pageable pageable = Pageable.unpaged();
        Page<Transaction> expectedPage = new PageImpl<>(Arrays.asList(new Transaction(), new Transaction()));

        when(repository.findByUserParticipationAndStatus(userId, status, pageable)).thenReturn(expectedPage);

        // Act
        Page<Transaction> result = transactionService.findTransactionsByUser(userId, status, pageable);

        // Assert
        assertEquals(expectedPage, result);
        verify(repository).findByUserParticipationAndStatus(userId, status, pageable);
    }

    @Test
    void testFindTransactionsByUserWithoutStatus() {
        // Arrange
        Long userId = 1L;
        Pageable pageable = Pageable.unpaged();
        Page<Transaction> expectedPage = new PageImpl<>(Arrays.asList(new Transaction(), new Transaction()));

        when(repository.findByUserParticipation(userId, pageable)).thenReturn(expectedPage);

        // Act
        Page<Transaction> result = transactionService.findTransactionsByUser(userId, null, pageable);

        // Assert
        assertEquals(expectedPage, result);
        verify(repository).findByUserParticipation(userId, pageable);
    }

    @Test
    void testFindById() {
        // Arrange
        Long id = 1L;
        Transaction expectedTransaction = new Transaction();
        when(repository.findById(id)).thenReturn(Optional.of(expectedTransaction));

        // Act
        Transaction result = transactionService.findById(id);

        // Assert
        assertEquals(expectedTransaction, result);
    }

    @Test
    void testFindByIdNotFound() {
        // Arrange
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TransactionNotFoundException.class, () -> transactionService.findById(id));
    }
}
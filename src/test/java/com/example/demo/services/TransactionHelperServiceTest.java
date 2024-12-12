package com.example.demo.services;

import com.example.demo.entities.Transaction;
import com.example.demo.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class TransactionHelperServiceTest {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private TransactionHelperService transactionHelperService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void testSaveFailedTransaction() {
        // Arrange
        Transaction transaction = new Transaction();
        LocalDateTime beforeSave = LocalDateTime.now(ZoneId.of("UTC"));

        // Act
        transactionHelperService.saveFailedTransaction(transaction);

        // Assert
        verify(repository).save(transaction);
        assert transaction.getTimestamp() != null;
        assert transaction.getTimestamp().isAfter(beforeSave) || transaction.getTimestamp().isEqual(beforeSave);
        assert transaction.getTimestamp().isBefore(LocalDateTime.now(ZoneId.of("UTC"))) || transaction.getTimestamp().isEqual(LocalDateTime.now(ZoneId.of("UTC")));
    }

    @Test
    void testTransactionAnnotation() {
        // Arrange
        Class<?> clazz = TransactionHelperService.class;
        Method method = null;
        try {
            method = clazz.getMethod("saveFailedTransaction", Transaction.class);
        } catch (NoSuchMethodException e) {
            fail("Method saveFailedTransaction not found");
        }

        // Act
        Transactional transactionalAnnotation = method.getAnnotation(Transactional.class);

        // Assert
        assertNotNull(transactionalAnnotation);
        assertEquals(Propagation.REQUIRES_NEW, transactionalAnnotation.propagation());
    }
}
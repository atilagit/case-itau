package com.example.demo.controller;

import com.example.demo.controller.data.requests.TransactionRequestDTO;
import com.example.demo.controller.data.responses.TransactionResponseDTO;
import com.example.demo.controller.data.responses.UserResponseForTransactionDTO;
import com.example.demo.controller.mappers.TransactionMapper;
import com.example.demo.entities.Transaction;
import com.example.demo.enums.TransactionStatus;
import com.example.demo.services.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @Mock
    private TransactionMapper mapper;

    @InjectMocks
    private TransactionController transactionController;

    @Test
    void mustSaveWithoutException() {
        // Arrange
        TransactionRequestDTO requestDTO = new TransactionRequestDTO(BigDecimal.TEN, 1L, 2L);
        Transaction transaction = new Transaction();
        Transaction savedTransaction = new Transaction();
        savedTransaction.setId(1L);
        UserResponseForTransactionDTO sender = new UserResponseForTransactionDTO(1L, "Sender Name");
        UserResponseForTransactionDTO receiver = new UserResponseForTransactionDTO(2L, "Receiver Name");
        TransactionResponseDTO responseDTO = new TransactionResponseDTO(1L, TransactionStatus.COMPLETED, BigDecimal.TEN, sender, receiver);

        when(mapper.map(requestDTO)).thenReturn(transaction);
        when(transactionService.save(transaction)).thenReturn(savedTransaction);
        when(mapper.map(savedTransaction)).thenReturn(responseDTO);

        // Act
        ResponseEntity<TransactionResponseDTO> response = transactionController.save(requestDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getHeaders().getLocation().toString().endsWith("/1"));
        verify(transactionService).save(transaction);
        verify(mapper).map(savedTransaction);
    }

    @Test
    void mustFindTransactionsByUserWithoutException() {
        // Arrange
        String userId = "1";
        Pageable pageable = Pageable.unpaged();
        Page<Transaction> transactionPage = new PageImpl<>(Arrays.asList(new Transaction(), new Transaction()));
        UserResponseForTransactionDTO sender = new UserResponseForTransactionDTO(1L, "Sender Name");
        UserResponseForTransactionDTO receiver = new UserResponseForTransactionDTO(2L, "Receiver Name");
        TransactionResponseDTO responseDTO = new TransactionResponseDTO(1L, TransactionStatus.COMPLETED, BigDecimal.TEN, sender, receiver);

        when(transactionService.findTransactionsByUser(1L, pageable)).thenReturn(transactionPage);
        when(mapper.map(any(Transaction.class))).thenReturn(responseDTO);

        // Act
        ResponseEntity<Page<TransactionResponseDTO>> response = transactionController.findTransactionsByUser(userId, pageable);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getContent().size());
        verify(transactionService).findTransactionsByUser(1L, pageable);
        verify(mapper, times(2)).map(any(Transaction.class));
    }

    @Test
    void mustFindByAccountNumberWithoutException() {
        // Arrange
        Long id = 1L;
        Transaction transaction = new Transaction();
        UserResponseForTransactionDTO sender = new UserResponseForTransactionDTO(1L, "Sender Name");
        UserResponseForTransactionDTO receiver = new UserResponseForTransactionDTO(2L, "Receiver Name");
        TransactionResponseDTO responseDTO = new TransactionResponseDTO(1L, TransactionStatus.COMPLETED, BigDecimal.TEN, sender, receiver);

        when(transactionService.findById(id)).thenReturn(transaction);
        when(mapper.map(transaction)).thenReturn(responseDTO);

        // Act
        ResponseEntity<TransactionResponseDTO> response = transactionController.findByAccountNumber(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(transactionService).findById(id);
        verify(mapper).map(transaction);
    }
}
package com.example.demo.services;

import com.example.demo.entities.User;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() throws Exception {
        try (AutoCloseable ignored = MockitoAnnotations.openMocks(this)) {
            /* AutoCloseable will be closed automatically at the end of this block */
        }
    }

    @Test
    void testSave() {
        // Arrange
        User user = new User();
        user.setName("John Doe");
        when(repository.save(user)).thenReturn(user);

        // Act
        User savedUser = userService.save(user);

        // Assert
        assertNotNull(savedUser);
        assertEquals("John Doe", savedUser.getName());
        verify(repository).save(user);
    }

    @Test
    void testFindByIdSuccess() {
        // Arrange
        Long id = 1L;
        User user = new User();
        user.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(user));

        // Act
        User foundUser = userService.findById(id);

        // Assert
        assertNotNull(foundUser);
        assertEquals(id, foundUser.getId());
        verify(repository).findById(id);
    }

    @Test
    void testFindByIdNotFound() {
        // Arrange
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.findById(id));
        verify(repository).findById(id);
    }

    @Test
    void testFindByAccountNumberSuccess() {
        // Arrange
        String accountNumber = "123456";
        User user = new User();
        user.setAccountNumber(accountNumber);
        when(repository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(user));

        // Act
        User foundUser = userService.findByAccountNumber(accountNumber);

        // Assert
        assertNotNull(foundUser);
        assertEquals(accountNumber, foundUser.getAccountNumber());
        verify(repository).findByAccountNumber(accountNumber);
    }

    @Test
    void testFindByAccountNumberNotFound() {
        // Arrange
        String accountNumber = "123456";
        when(repository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.findByAccountNumber(accountNumber));
        verify(repository).findByAccountNumber(accountNumber);
    }

    @Test
    void testFindAll() {
        // Arrange
        Pageable pageable = Pageable.unpaged();
        Page<User> expectedPage = new PageImpl<>(Arrays.asList(new User(), new User()));
        when(repository.findAll(pageable)).thenReturn(expectedPage);

        // Act
        Page<User> resultPage = userService.findAll(pageable);

        // Assert
        assertNotNull(resultPage);
        assertEquals(2, resultPage.getContent().size());
        verify(repository).findAll(pageable);
    }
}
package com.example.demo.services.validation;

import com.example.demo.controller.data.requests.UserRequestDTO;
import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InsertUserValidatorTest {

    @Mock
    private UserRepository repository;

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder builder;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilderCustomizableContext;

    private InsertUserValidator validator;

    @BeforeEach
    void setUp() throws Exception {
        try (AutoCloseable ignored = MockitoAnnotations.openMocks(this)) {
            validator = new InsertUserValidator(repository);
        }
    }

    @Test
    void testIsValidWhenAccountNumberDoesNotExist() {
        // Arrange
        UserRequestDTO request = new UserRequestDTO("John Doe", "123456", BigDecimal.valueOf(1000));
        when(repository.findByAccountNumber("123456")).thenReturn(Optional.empty());

        // Act
        boolean result = validator.isValid(request, context);

        // Assert
        assertTrue(result);
        verify(repository).findByAccountNumber("123456");
        verifyNoInteractions(context);
    }

    @Test
    void testIsValidWhenAccountNumberAlreadyExists() {
        // Arrange
        UserRequestDTO request = new UserRequestDTO("John Doe", "123456", BigDecimal.valueOf(1000));
        when(repository.findByAccountNumber("123456")).thenReturn(Optional.of(new User()));
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
        when(builder.addPropertyNode(anyString())).thenReturn(nodeBuilderCustomizableContext);

        // Act
        boolean result = validator.isValid(request, context);

        // Assert
        assertFalse(result);

        verify(repository).findByAccountNumber("123456");
        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate("accountNumer 123456 já existe");
        verify(builder).addPropertyNode("accountNumber");
        verify(nodeBuilderCustomizableContext).addConstraintViolation();
    }


    @Test
    void testInitialize() {
        // Arrange
        InsertUserValidation annotation = mock(InsertUserValidation.class);

        // Act & Assert
        assertDoesNotThrow(() -> validator.initialize(annotation));
    }
}
package com.example.demo.controller;

import com.example.demo.controller.data.requests.UserRequestDTO;
import com.example.demo.controller.data.responses.UserResponseDTO;
import com.example.demo.controller.mappers.UserMapper;
import com.example.demo.entities.User;
import com.example.demo.services.UserService;
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
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserController userController;

    @Test
    void testSave() {
        // Arrange
        UserRequestDTO requestDTO = new UserRequestDTO("John Doe", "123456", BigDecimal.valueOf(1000));
        User user = new User();
        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setAccountNumber("123456");
        UserResponseDTO responseDTO = new UserResponseDTO(1L, "John Doe", "123456", BigDecimal.valueOf(1000));

        when(mapper.map(requestDTO)).thenReturn(user);
        when(userService.save(user)).thenReturn(savedUser);
        when(mapper.map(savedUser)).thenReturn(responseDTO);

        // Act
        ResponseEntity<UserResponseDTO> response = userController.save(requestDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getHeaders().getLocation().toString().endsWith("/123456"));
        verify(userService).save(user);
        verify(mapper).map(savedUser);
    }

    @Test
    void testFindByAccountNumber() {
        // Arrange
        String accountNumber = "123456";
        User user = new User();
        UserResponseDTO responseDTO = new UserResponseDTO(1L, "John Doe", "123456", BigDecimal.valueOf(1000));

        when(userService.findByAccountNumber(accountNumber)).thenReturn(user);
        when(mapper.map(user)).thenReturn(responseDTO);

        // Act
        ResponseEntity<UserResponseDTO> response = userController.findByAccountNumber(accountNumber);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(userService).findByAccountNumber(accountNumber);
        verify(mapper).map(user);
    }

    @Test
    void testFindAllPageable() {
        // Arrange
        Pageable pageable = Pageable.unpaged();
        Page<User> userPage = new PageImpl<>(Arrays.asList(new User(), new User()));
        UserResponseDTO responseDTO = new UserResponseDTO(1L, "John Doe", "123456", BigDecimal.valueOf(1000));

        when(userService.findAll(pageable)).thenReturn(userPage);
        when(mapper.map(any(User.class))).thenReturn(responseDTO);

        // Act
        ResponseEntity<Page<UserResponseDTO>> response = userController.findAllPageable(pageable);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getContent().size());
        verify(userService).findAll(pageable);
        verify(mapper, times(2)).map(any(User.class));
    }
}
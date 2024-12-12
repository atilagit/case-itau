package com.example.demo.controller;

import com.example.demo.controller.data.requests.UserRequestDTO;
import com.example.demo.entities.User;
import com.example.demo.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Test
    void testCreateUser() throws Exception {
        // Arrange
        UserRequestDTO userRequest = new UserRequestDTO("John Doe", "123456", new BigDecimal("1000.00"));
        String userRequestJson = objectMapper.writeValueAsString(userRequest);

        // Act & Assert
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.accountNumber").value("123456"))
                .andExpect(jsonPath("$.balance").value("1000.0"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", org.hamcrest.Matchers.containsString("/api/v1/users/123456")));
    }

    @Test
    void testCreateUserWithInvalidData() throws Exception {
        // Arrange
        UserRequestDTO userRequest = new UserRequestDTO("", "", null);
        String userRequestJson = objectMapper.writeValueAsString(userRequest);

        // Act & Assert
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequestJson))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", org.hamcrest.Matchers.hasSize(3)));
    }

    @Test
    void testCreateUserWithDuplicateAccountNumber() throws Exception {
        // Arrange
        String accountNumber = "56789";

        // Create a user with the account number
        User existingUser = new User(null, "Existing User", accountNumber, new BigDecimal("1000.00"));
        userService.save(existingUser);

        // Try to create another user with the same account number
        UserRequestDTO newUser = new UserRequestDTO("New User", accountNumber, new BigDecimal("2000.00"));
        String newUserJson = objectMapper.writeValueAsString(newUser);

        // Act & Assert
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].message").value("accountNumer 56789 já existe"));
    }
}
package com.example.demo.controller;

import com.example.demo.entities.User;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        User saved = userService.save(user);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<User> findByAccountNumber(@PathVariable String accountNumber) {
        User user = userService.findByAccountNumber(accountNumber);
        return ResponseEntity.ok(user);
    }
}

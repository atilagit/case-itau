package com.example.demo.controller;

import com.example.demo.entities.User;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        User saved = userService.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{accountNumber}").buildAndExpand(saved.getAccountNumber()).toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<User> findByAccountNumber(@PathVariable String accountNumber) {
        User user = userService.findByAccountNumber(accountNumber);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<Page<User>> findAllPageable(@PageableDefault Pageable pageable) {
        Page<User> userPage = userService.findAll(pageable);
        return ResponseEntity.ok(userPage);
    }
}

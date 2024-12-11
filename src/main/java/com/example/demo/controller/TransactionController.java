package com.example.demo.controller;

import com.example.demo.entities.Transaction;
import com.example.demo.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> save(@RequestBody Transaction transaction) {
        Transaction saved = transactionService.save(transaction);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping
    public ResponseEntity<Page<Transaction>> findTransactionsByUser(@RequestParam(name = "userId") String userId, @PageableDefault Pageable pageable) {
        Page<Transaction> transactionPage = transactionService.findTransactionsByUser(Long.valueOf(userId), pageable);
        return ResponseEntity.ok(transactionPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> findByAccountNumber(@PathVariable Long id) {
        Transaction transaction = transactionService.findById(id);
        return ResponseEntity.ok(transaction);
    }
}

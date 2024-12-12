package com.example.demo.controller;

import com.example.demo.controller.data.requests.TransactionRequestDTO;
import com.example.demo.controller.data.responses.TransactionResponseDTO;
import com.example.demo.controller.mappers.TransactionMapper;
import com.example.demo.entities.Transaction;
import com.example.demo.services.TransactionService;
import jakarta.validation.Valid;
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

    @Autowired
    TransactionMapper mapper;

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> save(@RequestBody @Valid TransactionRequestDTO transactionDto) {
        Transaction transaction = mapper.map(transactionDto);
        Transaction saved = transactionService.save(transaction);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(mapper.map(saved));
    }

    @GetMapping
    public ResponseEntity<Page<TransactionResponseDTO>> findTransactionsByUser(@RequestParam(name = "userId") String userId, @PageableDefault Pageable pageable) {
        Page<Transaction> transactionPage = transactionService.findTransactionsByUser(Long.valueOf(userId), pageable);
        Page<TransactionResponseDTO> dtoResponse = transactionPage.map(entity -> mapper.map(entity));
        return ResponseEntity.ok(dtoResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> findByAccountNumber(@PathVariable Long id) {
        Transaction transaction = transactionService.findById(id);
        return ResponseEntity.ok(mapper.map(transaction));
    }
}

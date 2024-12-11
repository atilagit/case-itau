package com.example.demo.services;

import com.example.demo.entities.Transaction;
import com.example.demo.entities.User;
import com.example.demo.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository repository;

    @Autowired
    UserService userService;

    @Autowired
    TransactionValidation transactionValidation;

    public Transaction save(Transaction transaction) {
        User sender = userService.findById(transaction.getSender().getId());
        User receiver = userService.findById(transaction.getReceiver().getId());
        transactionValidation.execute(sender, transaction);
        updateData(sender, receiver, transaction);
        doTransaction(sender, receiver, transaction);
        return transaction;
    }

    public Page<Transaction> findTransactionsByUser(Long userId, Pageable pageable) {
        return repository.findByUserParticipation(userId, pageable);
    }

    private void updateData(User sender, User receiver, Transaction transaction) {
        sender.setBalance(sender.getBalance().subtract(transaction.getAmount()));
        receiver.setBalance(receiver.getBalance().add(transaction.getAmount()));
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setTimestamp(LocalDateTime.now(ZoneId.of("UTC")));
    }

    private void doTransaction(User sender, User receiver, Transaction transaction) {
        userService.save(sender);
        userService.save(receiver);
        repository.save(transaction);
    }
}

package com.example.demo.services;

import com.example.demo.entities.Transaction;
import com.example.demo.entities.User;
import com.example.demo.enums.TransactionStatus;
import com.example.demo.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    TransactionHelperService transactionHelperService;

    @Transactional
    public Transaction save(Transaction transaction) {
        try {
            User sender = userService.findById(transaction.getSender().getId());
            User receiver = userService.findById(transaction.getReceiver().getId());
            transactionValidation.execute(sender, transaction);
            updateData(sender, receiver, transaction);
            transaction.setStatus(TransactionStatus.COMPLETED);
            doTransaction(sender, receiver, transaction);
        } catch (Exception e) {
            transaction.setStatus(TransactionStatus.FAILED);
            transactionHelperService.saveFailedTransaction(transaction);
            throw e;
        }
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

    public Transaction findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Transação não encontrada."));
    }
}

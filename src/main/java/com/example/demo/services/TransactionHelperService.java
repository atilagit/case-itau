package com.example.demo.services;

import com.example.demo.entities.Transaction;
import com.example.demo.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class TransactionHelperService {

    @Autowired
    private TransactionRepository repository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveFailedTransaction(Transaction transaction) {
        transaction.setTimestamp(LocalDateTime.now(ZoneId.of("UTC")));
        repository.save(transaction);
    }
}
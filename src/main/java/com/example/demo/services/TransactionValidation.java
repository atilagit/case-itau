package com.example.demo.services;

import com.example.demo.entities.Transaction;
import com.example.demo.entities.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionValidation {
    public void execute(User sender, Transaction transaction) {
        if (transaction.getAmount().compareTo(sender.getBalance()) > 0) {
            throw new RuntimeException("Saldo insuficiente");
        }
        if (transaction.getAmount().compareTo(BigDecimal.valueOf(100.0)) > 0) {
            throw new RuntimeException("O valor da transação excede o limite autorizado");
        }
    }
}
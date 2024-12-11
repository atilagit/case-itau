package com.example.demo.exceptions;

import java.io.Serial;

public class TransactionNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public TransactionNotFoundException(String msg) {
        super(msg);
    }
}
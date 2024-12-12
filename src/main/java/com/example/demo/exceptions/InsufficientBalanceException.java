package com.example.demo.exceptions;

import java.io.Serial;

public class InsufficientBalanceException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public InsufficientBalanceException(String msg) {
        super(msg);
    }
}
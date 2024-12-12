package com.example.demo.exceptions;

import java.io.Serial;

public class LimitExceededException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public LimitExceededException(String msg) {
        super(msg);
    }
}
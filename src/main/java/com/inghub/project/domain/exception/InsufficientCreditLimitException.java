package com.inghub.project.domain.exception;

public class InsufficientCreditLimitException extends RuntimeException {

    public InsufficientCreditLimitException() {
        super("Insufficient credit limit.");
    }

    public InsufficientCreditLimitException(String message) {
        super(message);
    }
}


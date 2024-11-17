package com.inghub.project.domain.exception;

public class InvalidLoanParametersException extends RuntimeException {

    public InvalidLoanParametersException() {
        super("Invalid loan parameters.");
    }

    public InvalidLoanParametersException(String message) {
        super(message);
    }
}

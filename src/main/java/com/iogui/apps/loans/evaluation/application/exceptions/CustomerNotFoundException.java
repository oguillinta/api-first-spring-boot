package com.iogui.apps.loans.evaluation.application.exceptions;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException() {}

    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerNotFoundException(Throwable cause) {
        super(cause);
    }

}
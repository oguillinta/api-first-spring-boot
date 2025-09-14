package com.iogui.apps.loans.evaluation.application.exceptions;

public class DuplicateCustomerException extends RuntimeException{
    public DuplicateCustomerException() {}

    public DuplicateCustomerException(String message) {
        super(message);
    }

    public DuplicateCustomerException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateCustomerException(Throwable cause) {
        super(cause);
    }
}

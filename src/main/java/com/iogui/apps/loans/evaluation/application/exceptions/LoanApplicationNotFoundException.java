package com.iogui.apps.loans.evaluation.application.exceptions;

public class LoanApplicationNotFoundException extends RuntimeException{
    public LoanApplicationNotFoundException() {}

    public LoanApplicationNotFoundException(String message) {
        super(message);
    }

    public LoanApplicationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoanApplicationNotFoundException(Throwable cause) {
        super(cause);
    }
}

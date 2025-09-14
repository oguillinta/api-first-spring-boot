package com.iogui.apps.loans.evaluation.domain.exceptions;

public class InvalidLoanApplicationStateException extends RuntimeException{
    public InvalidLoanApplicationStateException(String message) {
        super(message);
    }
}

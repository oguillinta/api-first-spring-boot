package com.iogui.apps.loans.evaluation.infrastructure.input.api.rest.exceptions;

import com.iogui.apps.loans.evaluation.application.exceptions.CustomerNotFoundException;
import com.iogui.apps.loans.evaluation.application.exceptions.DuplicateCustomerException;
import com.iogui.apps.loans.evaluation.application.exceptions.LoanApplicationNotFoundException;
import com.iogui.apps.loans.evaluation.domain.exceptions.InvalidDecisionException;
import com.iogui.apps.loans.evaluation.domain.exceptions.InvalidLoanApplicationStateException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseBody
    public ErrorMessage handleCustomerNotFound(CustomerNotFoundException exception) {
        return new ErrorMessage(exception.toString(), exception.getMessage(), NOT_FOUND.value());
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(LoanApplicationNotFoundException.class)
    @ResponseBody
    public ErrorMessage handleLoanApplicationNotFound(LoanApplicationNotFoundException exception) {
        return new ErrorMessage(exception.toString(), exception.getMessage(), NOT_FOUND.value());
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(DuplicateCustomerException.class)
    @ResponseBody
    public ErrorMessage handleDuplicateCustomer(DuplicateCustomerException exception) {
        return new ErrorMessage(exception.toString(), exception.getMessage(), CONFLICT.value());
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(InvalidLoanApplicationStateException.class)
    @ResponseBody
    public ErrorMessage handleInvalidLoanApplicationState(InvalidLoanApplicationStateException exception) {
        return new ErrorMessage(exception.toString(), exception.getMessage(), CONFLICT.value());
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(InvalidDecisionException.class)
    @ResponseBody
    public ErrorMessage handleInvalidDecisionException(InvalidDecisionException exception) {
        return new ErrorMessage(exception.toString(), exception.getMessage(), CONFLICT.value());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ErrorMessage handleBadRequest(Exception exception) {
        return new ErrorMessage(exception.toString(), exception.getMessage(), BAD_REQUEST.value());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ValidationErrorMessage handleValidationExceptions(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ValidationError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ValidationErrorMessage("Validation failed", errors);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleEnumError(MethodArgumentTypeMismatchException ex) {
        var body = new HashMap<String, Object>();
        body.put("error", "Bad Request");
        body.put("message", "Invalid value for '" + ex.getName());
        return ResponseEntity.badRequest().body(body);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorMessage handleGenericError(Exception exception) {
        return new ErrorMessage(
                exception.toString(),
                "Unexpected error occurred",
                INTERNAL_SERVER_ERROR.value());
    }
}

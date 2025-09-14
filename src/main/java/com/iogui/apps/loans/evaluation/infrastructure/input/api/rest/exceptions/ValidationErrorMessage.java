package com.iogui.apps.loans.evaluation.infrastructure.input.api.rest.exceptions;

import java.util.List;

public record ValidationErrorMessage (
        String message,
        List<ValidationError> errors
) {}

package com.iogui.apps.loans.evaluation.infrastructure.input.api.rest.exceptions;

public record ValidationError (String field, String message) {}
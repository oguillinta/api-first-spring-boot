package com.iogui.apps.loans.evaluation.application.ports.input.loan;

import java.util.UUID;

@FunctionalInterface
public interface PerformRiskAssessmentUseCase {
    void performRiskAssessment(UUID loanApplicationId);
}

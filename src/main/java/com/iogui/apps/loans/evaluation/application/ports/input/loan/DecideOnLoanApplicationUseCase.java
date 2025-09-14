package com.iogui.apps.loans.evaluation.application.ports.input.loan;

import com.iogui.apps.loans.evaluation.model.DecideOnLoanApplicationRequest;

import java.util.UUID;

@FunctionalInterface
public interface DecideOnLoanApplicationUseCase {
    void decideOnLoanApplication(UUID loanApplicationId, DecideOnLoanApplicationRequest request);

}

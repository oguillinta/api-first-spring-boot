package com.iogui.apps.loans.evaluation.application.ports.input.loan;

import com.iogui.apps.loans.evaluation.model.LoanApplicationResponse;

import java.util.List;

@FunctionalInterface
public interface GetLoanApplicationsByStatusUseCase {
    List<LoanApplicationResponse> getLoanApplicationsByStatus(String status);
}

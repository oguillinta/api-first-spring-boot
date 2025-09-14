package com.iogui.apps.loans.evaluation.application.ports.input.loan;

import com.iogui.apps.loans.evaluation.model.LoanApplicationResponse;

import java.util.List;
import java.util.UUID;

@FunctionalInterface
public interface GetLoanApplicationsByCustomerUseCase {
    List<LoanApplicationResponse> getLoanApplicationsByCustomer(UUID customerId);
}

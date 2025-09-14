package com.iogui.apps.loans.evaluation.application.ports.input.customer;

import com.iogui.apps.loans.evaluation.model.LoanApplicationResponse;

import java.util.List;
import java.util.UUID;

@FunctionalInterface
public interface GetCustomerLoanApplicationsUseCase {
    List<LoanApplicationResponse> getCustomerLoanApplications(UUID customerId);
}

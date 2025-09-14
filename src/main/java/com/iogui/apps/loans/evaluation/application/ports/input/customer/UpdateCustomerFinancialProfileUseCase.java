package com.iogui.apps.loans.evaluation.application.ports.input.customer;

import com.iogui.apps.loans.evaluation.model.FinancialProfileRequest;

import java.util.UUID;

@FunctionalInterface
public interface UpdateCustomerFinancialProfileUseCase {
    void updateCustomerFinancialProfile(UUID customerId, FinancialProfileRequest request);
}

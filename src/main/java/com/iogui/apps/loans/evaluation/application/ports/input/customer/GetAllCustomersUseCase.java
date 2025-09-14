package com.iogui.apps.loans.evaluation.application.ports.input.customer;

import com.iogui.apps.loans.evaluation.model.CustomerResponse;

import java.util.List;

@FunctionalInterface
public interface GetAllCustomersUseCase {
    List<CustomerResponse> getAllCustomers();
}

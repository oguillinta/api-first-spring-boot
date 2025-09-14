package com.iogui.apps.loans.evaluation.application.ports.input.customer;

import com.iogui.apps.loans.evaluation.model.CustomerRequest;
import com.iogui.apps.loans.evaluation.model.CustomerResponse;

@FunctionalInterface
public interface CreateCustomerUseCase {
    CustomerResponse createCustomer(CustomerRequest customer);
}

package com.iogui.apps.loans.evaluation.application.ports.input.customer;

import com.iogui.apps.loans.evaluation.model.ContactInformationRequest;
import com.iogui.apps.loans.evaluation.model.CustomerResponse;

import java.util.UUID;

@FunctionalInterface
public interface UpdateCustomerContactUseCase {
    CustomerResponse updateCustomerContact(UUID customerId, ContactInformationRequest customerContactRequest);
}

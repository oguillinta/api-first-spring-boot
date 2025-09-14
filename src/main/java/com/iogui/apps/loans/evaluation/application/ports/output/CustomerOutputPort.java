package com.iogui.apps.loans.evaluation.application.ports.output;

import com.iogui.apps.loans.evaluation.domain.ContactInformation;
import com.iogui.apps.loans.evaluation.domain.Customer;
import com.iogui.apps.loans.evaluation.domain.FinancialProfile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerOutputPort {

    List<Customer> getAll();

    Optional<Customer> getCustomerById(UUID id);

    Customer createCustomer(Customer customer);

    Optional<Customer> updateCustomerContact(UUID customerId, ContactInformation customerContact);

    Optional<Customer> updateCustomerFinancialProfile(UUID customerId, FinancialProfile financialProfile);
}

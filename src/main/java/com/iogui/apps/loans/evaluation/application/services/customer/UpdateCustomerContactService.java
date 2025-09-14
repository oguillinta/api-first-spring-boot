package com.iogui.apps.loans.evaluation.application.services.customer;

import com.iogui.apps.loans.evaluation.application.exceptions.CustomerNotFoundException;
import com.iogui.apps.loans.evaluation.application.mappers.customer.CustomerAppMapper;
import com.iogui.apps.loans.evaluation.application.ports.input.customer.UpdateCustomerContactUseCase;
import com.iogui.apps.loans.evaluation.application.ports.output.CustomerOutputPort;
import com.iogui.apps.loans.evaluation.domain.ContactInformation;
import com.iogui.apps.loans.evaluation.domain.Customer;
import com.iogui.apps.loans.evaluation.model.ContactInformationRequest;
import com.iogui.apps.loans.evaluation.model.CustomerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class UpdateCustomerContactService implements UpdateCustomerContactUseCase {
    private static final Logger LOG = LoggerFactory.getLogger(UpdateCustomerContactService.class);
    private final CustomerOutputPort customerOutputPort;
    private final CustomerAppMapper customerAppMapper;

    public UpdateCustomerContactService(CustomerOutputPort customerOutputPort, CustomerAppMapper customerAppMapper) {
        this.customerOutputPort = customerOutputPort;
        this.customerAppMapper = customerAppMapper;
    }

    @Override
    public CustomerResponse updateCustomerContact(UUID customerId, ContactInformationRequest customerContactRequest) {
        LOG.info("Updating contact information for customer: {}", customerId);

        ContactInformation customerContact = customerAppMapper.mapToContactInformationDomain(customerContactRequest);

        Customer customerDomain = customerOutputPort.updateCustomerContact(customerId, customerContact)
                .orElseThrow(() -> {
                    LOG.warn("Customer not found for contact update: {}", customerId);
                    return new CustomerNotFoundException("Customer not found with id:" + customerId);
                });

        LOG.info("Successfully updated contact information for customer: {}", customerId);

        return customerAppMapper.mapToCustomerResponse(customerDomain);
    }
}

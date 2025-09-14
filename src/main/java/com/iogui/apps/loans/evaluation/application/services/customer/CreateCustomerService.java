package com.iogui.apps.loans.evaluation.application.services.customer;

import com.iogui.apps.loans.evaluation.application.mappers.customer.CustomerAppMapper;
import com.iogui.apps.loans.evaluation.application.ports.input.customer.CreateCustomerUseCase;
import com.iogui.apps.loans.evaluation.application.ports.output.CustomerOutputPort;
import com.iogui.apps.loans.evaluation.domain.Customer;
import com.iogui.apps.loans.evaluation.model.CustomerRequest;
import com.iogui.apps.loans.evaluation.model.CustomerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateCustomerService implements CreateCustomerUseCase {
    private static final Logger LOG = LoggerFactory.getLogger(CreateCustomerService.class);
    private final CustomerOutputPort customerOutputPort;
    private final CustomerAppMapper customerAppMapper;

    public CreateCustomerService(
            CustomerOutputPort customerOutputPort,
            CustomerAppMapper customerAppMapper) {
        this.customerOutputPort = customerOutputPort;
        this.customerAppMapper = customerAppMapper;
    }

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        LOG.info("Creating new customer with request: {}", request.getEmail());

        Customer customer = customerAppMapper.mapToCustomerDomain(request);
        Customer savedCustomer = customerOutputPort.createCustomer(customer);

        LOG.info("Successfully created customer with id: {}", savedCustomer.getId());

        return customerAppMapper.mapToCustomerResponse(savedCustomer);
    }
}

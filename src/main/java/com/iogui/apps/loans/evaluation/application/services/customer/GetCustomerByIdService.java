package com.iogui.apps.loans.evaluation.application.services.customer;

import com.iogui.apps.loans.evaluation.application.exceptions.CustomerNotFoundException;
import com.iogui.apps.loans.evaluation.application.mappers.customer.CustomerAppMapper;
import com.iogui.apps.loans.evaluation.application.ports.input.customer.GetCustomerByIdUseCase;
import com.iogui.apps.loans.evaluation.application.ports.output.CustomerOutputPort;
import com.iogui.apps.loans.evaluation.model.CustomerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class GetCustomerByIdService implements GetCustomerByIdUseCase {
    private static final Logger LOG = LoggerFactory.getLogger(GetCustomerByIdService.class);
    CustomerOutputPort customerOutputPort;
    CustomerAppMapper customerAppMapper;

    public GetCustomerByIdService(CustomerOutputPort customerOutputPort, CustomerAppMapper customerAppMapper) {
        this.customerOutputPort = customerOutputPort;
        this.customerAppMapper = customerAppMapper;
    }

    @Override
    public CustomerResponse getCustomerById(UUID customerId) {
        LOG.debug("Retrieving customer by id: {}", customerId );

        return customerOutputPort.getCustomerById(customerId)
                .map(customer ->
                {   LOG.debug("Customer found with id: {}", customerId);
                    return customerAppMapper.mapToCustomerResponse(customer);
                })
                .orElseThrow(() -> {
                    LOG.warn("Customer not found with id: {}", customerId);
                    return new CustomerNotFoundException("Customer not found with id:" + customerId);
                });
    }
}

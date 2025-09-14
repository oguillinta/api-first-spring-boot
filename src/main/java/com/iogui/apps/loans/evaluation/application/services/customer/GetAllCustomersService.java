package com.iogui.apps.loans.evaluation.application.services.customer;

import com.iogui.apps.loans.evaluation.application.mappers.customer.CustomerAppMapper;
import com.iogui.apps.loans.evaluation.application.ports.input.customer.GetAllCustomersUseCase;
import com.iogui.apps.loans.evaluation.application.ports.output.CustomerOutputPort;
import com.iogui.apps.loans.evaluation.model.CustomerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GetAllCustomersService implements GetAllCustomersUseCase {
    private static final Logger LOG = LoggerFactory.getLogger(GetAllCustomersService.class);
    private final CustomerOutputPort customerOutputPort;
    private final CustomerAppMapper customerMapper;

    public GetAllCustomersService(CustomerOutputPort customerOutputPort, CustomerAppMapper customerMapper) {
        this.customerOutputPort = customerOutputPort;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        LOG.debug("Retrieving all customers");
        List<CustomerResponse> response = customerMapper.mapToCustomerResponseList(customerOutputPort.getAll());
        LOG.info("Retrieved {} customers", response.size());
        return response;
    }
}

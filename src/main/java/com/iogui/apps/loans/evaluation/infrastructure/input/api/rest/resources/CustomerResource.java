package com.iogui.apps.loans.evaluation.infrastructure.input.api.rest.resources;


import com.iogui.apps.loans.evaluation.api.CustomersApi;
import com.iogui.apps.loans.evaluation.application.ports.input.customer.*;
import com.iogui.apps.loans.evaluation.application.ports.input.loan.GetLoanApplicationsByCustomerUseCase;
import com.iogui.apps.loans.evaluation.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class CustomerResource implements CustomersApi {
    private final GetAllCustomersUseCase getAllCustomersUseCase;
    private final GetLoanApplicationsByCustomerUseCase getLoanApplicationsByCustomerUseCase;
    private final GetCustomerByIdUseCase getCustomerByIdUseCase;
    private final CreateCustomerUseCase createCustomerUseCase;
    private final UpdateCustomerContactUseCase updateCustomerContactUseCase;
    private final UpdateCustomerFinancialProfileUseCase updateCustomerFinancialProfileUseCase;

    public CustomerResource(
            GetAllCustomersUseCase getAllCustomersUseCase,
            GetLoanApplicationsByCustomerUseCase getLoanApplicationsByCustomerUseCase,
            GetCustomerByIdUseCase getCustomerByIdUseCase,
            CreateCustomerUseCase createCustomerUseCase,
            UpdateCustomerContactUseCase updateCustomerContactUseCase, UpdateCustomerFinancialProfileUseCase updateCustomerFinancialProfileUseCase) {
        this.getAllCustomersUseCase = getAllCustomersUseCase;
        this.getLoanApplicationsByCustomerUseCase = getLoanApplicationsByCustomerUseCase;
        this.getCustomerByIdUseCase = getCustomerByIdUseCase;
        this.createCustomerUseCase = createCustomerUseCase;
        this.updateCustomerContactUseCase = updateCustomerContactUseCase;
        this.updateCustomerFinancialProfileUseCase = updateCustomerFinancialProfileUseCase;
    }

    @PreAuthorize("hasAuthority('CUSTOMER_VIEWER') or hasAuthority('LOAN_OFFICER') or hasAuthority('LOAN_MANAGER') or hasAuthority('CUSTOMER_SERVICE')")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> response = getAllCustomersUseCase.getAllCustomers();

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CUSTOMER_VIEWER') or hasAuthority('LOAN_OFFICER') or hasAuthority('CUSTOMER_SERVICE')")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable UUID customerId) {
        CustomerResponse response = getCustomerByIdUseCase.getCustomerById(customerId);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('LOAN_OFFICER') or hasAuthority('CUSTOMER_SERVICE')")
    public ResponseEntity<List<LoanApplicationResponse>> getLoanApplicationsByCustomer(@PathVariable UUID customerId) {
        List<LoanApplicationResponse> response = getLoanApplicationsByCustomerUseCase.getLoanApplicationsByCustomer(customerId);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('LOAN_OFFICER')")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest customerRequest) {
        CustomerResponse customerResponse = createCustomerUseCase.createCustomer(customerRequest);
        return ResponseEntity.ok(customerResponse);
    }

    @PreAuthorize("hasAuthority('CUSTOMER_SERVICE') or hasAuthority('LOAN_OFFICER')")
    public ResponseEntity<CustomerResponse> updateCustomerContact(
            @PathVariable UUID customerId,
            @RequestBody ContactInformationRequest contactInformationRequest) {
        CustomerResponse response = updateCustomerContactUseCase.updateCustomerContact(customerId, contactInformationRequest);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('LOAN_OFFICER')")
    public ResponseEntity<Void> updateCustomerFinancialProfile(
            @PathVariable UUID customerId,
            @RequestBody FinancialProfileRequest financialProfileRequest) {
        updateCustomerFinancialProfileUseCase.updateCustomerFinancialProfile(customerId, financialProfileRequest);
        return ResponseEntity.ok().build();
    }
}

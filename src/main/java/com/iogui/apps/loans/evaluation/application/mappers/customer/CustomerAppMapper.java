package com.iogui.apps.loans.evaluation.application.mappers.customer;

import com.iogui.apps.loans.evaluation.domain.ContactInformation;
import com.iogui.apps.loans.evaluation.domain.Customer;
import com.iogui.apps.loans.evaluation.domain.FinancialProfile;
import com.iogui.apps.loans.evaluation.model.ContactInformationRequest;
import com.iogui.apps.loans.evaluation.model.CustomerRequest;
import com.iogui.apps.loans.evaluation.model.CustomerResponse;
import com.iogui.apps.loans.evaluation.model.FinancialProfileRequest;

import java.util.List;

public interface CustomerAppMapper {

    List<CustomerResponse> mapToCustomerResponseList(List<Customer> customer);
    CustomerResponse mapToCustomerResponse(Customer customer);
    Customer mapToCustomerDomain(CustomerRequest request);
    ContactInformation mapToContactInformationDomain(ContactInformationRequest request);
    FinancialProfile mapToFinancialProfileDomain(FinancialProfileRequest request);
}

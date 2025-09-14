package com.iogui.apps.loans.evaluation.application.mappers.customer;

import com.iogui.apps.loans.evaluation.domain.*;
import com.iogui.apps.loans.evaluation.model.ContactInformationRequest;
import com.iogui.apps.loans.evaluation.model.CustomerRequest;
import com.iogui.apps.loans.evaluation.model.CustomerResponse;
import com.iogui.apps.loans.evaluation.model.FinancialProfileRequest;

import java.util.*;
import java.util.stream.Collectors;

public class CustomerAppMapperImpl implements CustomerAppMapper {
    @Override
    public List<CustomerResponse> mapToCustomerResponseList(List<Customer> customers) {
        return customers.stream()
                .map(this::mapToCustomerResponse)
                .collect(Collectors.toList());

    }

    @Override
    public CustomerResponse mapToCustomerResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());
        response.setEmail(customer.getContactInformation().getEmail());
        response.setPhoneNumber(customer.getContactInformation().getPhoneNumber());
        response.setStreet(customer.getContactInformation().getAddress().getStreet());
        response.setCity(customer.getContactInformation().getAddress().getCity());
        response.setState(customer.getContactInformation().getAddress().getState());
        response.setZipCode(customer.getContactInformation().getAddress().getZipCode());
        response.setCountry(customer.getContactInformation().getAddress().getCountry());
        response.setMonthlyIncome(customer.getFinancialProfile().getMonthlyIncome().getAmount());
        response.setMonthlyExpenses(customer.getFinancialProfile().getMonthlyExpenses().getAmount());
        response.setExistingDebt(customer.getFinancialProfile().getExistingDebt().getAmount());
        response.setYearsOfEmployment(customer.getFinancialProfile().getYearsOfEmployment());
        response.setDebtToIncomeRatio(customer.getFinancialProfile().getDebtToIncomeRatio());
        return response;
    }

    @Override
    public Customer mapToCustomerDomain(CustomerRequest request) {
        return new Customer(
                request.getId(),
                request.getFirstName(),
                request.getLastName(),
                new ContactInformation(
                        request.getEmail(),
                        request.getPhoneNumber(),
                        new Address(
                                request.getStreet(),
                                request.getCity(),
                                request.getState(),
                                request.getZipCode(),
                                request.getCountry()
                        )),
                new FinancialProfile(
                        Money.of(request.getMonthlyIncome(), "USD"),
                        Money.of(request.getMonthlyExpenses(), "USD"),
                        Money.of(request.getExistingDebt(), "USD"),
                        request.getYearsOfEmployment(),
                        request.getDebtToIncomeRatio()
                )
        );
    }

    @Override
    public ContactInformation mapToContactInformationDomain(ContactInformationRequest request) {
        return new ContactInformation(
                request.getEmail(),
                request.getPhoneNumber(),
                new Address(
                        request.getStreet(),
                        request.getCity(),
                        request.getState(),
                        request.getZipCode(),
                        request.getCountry()
                ));
    }

    @Override
    public FinancialProfile mapToFinancialProfileDomain(FinancialProfileRequest request) {
        return new FinancialProfile(
                Money.of(request.getMonthlyIncome(), "USD"),
                Money.of(request.getMonthlyExpenses(), "USD"),
                Money.of(request.getExistingDebt(), "USD"),
                request.getYearsOfEmployment(),
                request.getDebtToIncomeRatio()
        );
    }
}

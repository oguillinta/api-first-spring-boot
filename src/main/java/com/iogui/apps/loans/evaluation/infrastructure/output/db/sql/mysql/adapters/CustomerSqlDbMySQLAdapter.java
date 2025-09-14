package com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.adapters;

import com.iogui.apps.loans.evaluation.application.ports.output.CustomerOutputPort;
import com.iogui.apps.loans.evaluation.domain.*;
import com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.entities.CustomerEntity;
import com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.mappers.customer.CustomerMySQLMapper;
import com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.repositories.CustomerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


public class CustomerSqlDbMySQLAdapter implements CustomerOutputPort {
    private final CustomerRepository customerRepository;
    private final CustomerMySQLMapper customerMySQLMapper;

    public CustomerSqlDbMySQLAdapter(
            CustomerRepository customerRepository,
            CustomerMySQLMapper customerMySQLMapper) {
        this.customerRepository = customerRepository;
        this.customerMySQLMapper = customerMySQLMapper;
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll()
                .stream()
                .map(customerMySQLMapper::mapToCustomerDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Customer> getCustomerById(UUID id) {

        Optional<CustomerEntity> customerDb = customerRepository.findById(id);

        if (customerDb.isPresent()) {
            Customer customer = customerMySQLMapper.mapToCustomerDomain(customerDb.get());
            return Optional.of(customer);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Customer createCustomer(Customer customer) {
        CustomerEntity customerEntity = customerMySQLMapper.mapToCustomerEntity(customer);
        return customerMySQLMapper.mapToCustomerDomain(customerRepository.save(customerEntity));
    }

    @Override
    public Optional<Customer> updateCustomerContact(UUID customerId, ContactInformation customerContact) {
        Optional<CustomerEntity> customerDb = customerRepository.findById(customerId);
        if (customerDb.isEmpty()) {
            return Optional.empty();
        }
        CustomerEntity customerDbToUpdate = customerDb.get();

        customerDbToUpdate.setEmail(customerContact.getEmail());
        customerDbToUpdate.setPhoneNumber(customerContact.getPhoneNumber());

        Address address = customerContact.getAddress();
        customerDbToUpdate.setStreet(address.getStreet());
        customerDbToUpdate.setCity(address.getCity());
        customerDbToUpdate.setState(address.getState());
        customerDbToUpdate.setZipCode(address.getZipCode());
        customerDbToUpdate.setCountry(address.getCountry());

        CustomerEntity updatedCustomerDb = customerRepository.save(customerDbToUpdate);

        return Optional.of(customerMySQLMapper.mapToCustomerDomain(updatedCustomerDb));

    }

    @Override
    public Optional<Customer> updateCustomerFinancialProfile(UUID customerId, FinancialProfile financialProfile) {
        Optional<CustomerEntity> customerDb = customerRepository.findById(customerId);
        if (customerDb.isEmpty()) {
            return Optional.empty();
        }
        CustomerEntity customerDbToUpdate = customerDb.get();
        customerDbToUpdate.setMonthlyIncome(financialProfile.getMonthlyIncome().getAmount());
        customerDbToUpdate.setMonthlyExpenses(financialProfile.getMonthlyExpenses().getAmount());
        customerDbToUpdate.setExistingDebt(financialProfile.getExistingDebt().getAmount());
        customerDbToUpdate.setYearsOfEmployment(financialProfile.getYearsOfEmployment());
        customerDbToUpdate.setDebtToIncomeRatio(financialProfile.getDebtToIncomeRatio());

        CustomerEntity updatedCustomerDb = customerRepository.save(customerDbToUpdate);

        return Optional.of(customerMySQLMapper.mapToCustomerDomain(updatedCustomerDb));

    }
}

package com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.mappers.customer;

import com.iogui.apps.loans.evaluation.domain.*;
import com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.entities.CustomerEntity;

import java.math.BigDecimal;

public class CustomerMySQLMapperImpl implements CustomerMySQLMapper {
    public Customer mapToCustomerDomain(CustomerEntity entity) {

        return new Customer(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                new ContactInformation(
                        entity.getEmail(),
                        entity.getPhoneNumber(),
                        new Address(
                                entity.getStreet(),
                                entity.getCity(),
                                entity.getState(),
                                entity.getZipCode(),
                                entity.getCountry()
                        )),
                new FinancialProfile(
                        Money.of(entity.getMonthlyIncome(), "USD") ,
                        Money.of(entity.getMonthlyExpenses(), "USD"),
                        Money.of(entity.getExistingDebt(), "USD"),
                        entity.getYearsOfEmployment(),
                        entity.getDebtToIncomeRatio())
                );

    }

    @Override
    public CustomerEntity mapToCustomerEntity(Customer domain) {
        return new CustomerEntity(
                domain.getId(),
                domain.getFirstName(),
                domain.getLastName(),
                domain.getContactInformation().getEmail(),
                domain.getContactInformation().getPhoneNumber(),
                domain.getContactInformation().getAddress().getStreet(),
                domain.getContactInformation().getAddress().getCity(),
                domain.getContactInformation().getAddress().getState(),
                domain.getContactInformation().getAddress().getZipCode(),
                domain.getContactInformation().getAddress().getCountry(),
                domain.getFinancialProfile().getMonthlyIncome().getAmount(),
                domain.getFinancialProfile().getMonthlyExpenses().getAmount(),
                domain.getFinancialProfile().getExistingDebt().getAmount(),
                "USD",
                domain.getFinancialProfile().getYearsOfEmployment(),
                domain.getFinancialProfile().getDebtToIncomeRatio(),
                domain.getCreatedAt(),
                domain.getUpdateAt()
        );
    }

    ;
}

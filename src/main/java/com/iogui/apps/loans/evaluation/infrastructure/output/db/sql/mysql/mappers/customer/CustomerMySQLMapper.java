package com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.mappers.customer;

import com.iogui.apps.loans.evaluation.domain.Customer;
import com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.entities.CustomerEntity;

public interface CustomerMySQLMapper {
    Customer mapToCustomerDomain(CustomerEntity entity);
    CustomerEntity mapToCustomerEntity(Customer domainCustomer);
}

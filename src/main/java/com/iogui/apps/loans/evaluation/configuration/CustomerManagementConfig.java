package com.iogui.apps.loans.evaluation.configuration;

import com.iogui.apps.loans.evaluation.application.mappers.customer.CustomerAppMapper;
import com.iogui.apps.loans.evaluation.application.mappers.customer.CustomerAppMapperImpl;
import com.iogui.apps.loans.evaluation.application.ports.input.customer.*;
import com.iogui.apps.loans.evaluation.application.ports.output.CustomerOutputPort;
import com.iogui.apps.loans.evaluation.application.ports.output.LoanApplicationOutputPort;
import com.iogui.apps.loans.evaluation.application.ports.output.RiskAssessmentOutputPort;
import com.iogui.apps.loans.evaluation.application.services.customer.*;
import com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.adapters.CustomerSqlDbMySQLAdapter;
import com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.mappers.customer.CustomerMySQLMapper;
import com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.mappers.customer.CustomerMySQLMapperImpl;
import com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.repositories.CustomerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerManagementConfig {

    @Bean
    CustomerSqlDbMySQLAdapter customerSqlDbMySQLAdapter(
            CustomerRepository customerRepository,
            CustomerMySQLMapper customerMySQLMapper
    ) {
        return new CustomerSqlDbMySQLAdapter(customerRepository, customerMySQLMapper);
    }

    @Bean
    CustomerAppMapper customerAppMapper() {
        return new CustomerAppMapperImpl();
    }

    @Bean
    CustomerMySQLMapper customerMySQLMapper() {
        return new CustomerMySQLMapperImpl();
    }

    @Bean
    GetAllCustomersUseCase getAllCustomersUseCase(
            CustomerOutputPort customerOutputPort,
            CustomerAppMapper customerAppMapper
    ) {
        return new GetAllCustomersService(customerOutputPort, customerAppMapper);
    }

    @Bean
    GetAllCustomersService getAllCustomersService(
            CustomerOutputPort customerOutputPort,
            CustomerAppMapper customerAppMapper
    ) {
        return new GetAllCustomersService(customerOutputPort, customerAppMapper);
    }

    @Bean
    GetCustomerByIdUseCase getCustomerByIdUseCase(
            CustomerOutputPort customerOutputPort,
            CustomerAppMapper customerAppMapper
    ) {
        return new GetCustomerByIdService(customerOutputPort, customerAppMapper);
    }

    @Bean
    GetCustomerByIdService getCustomerByIdService(
            CustomerOutputPort customerOutputPort,
            CustomerAppMapper customerAppMapper
    ) {
        return new GetCustomerByIdService(customerOutputPort, customerAppMapper);
    }

    @Bean
    CreateCustomerUseCase createCustomerUseCase(
            CustomerOutputPort customerOutputPort,
            CustomerAppMapper customerAppMapper
    ) {
        return new CreateCustomerService(customerOutputPort, customerAppMapper);
    }

    @Bean
    CreateCustomerService createCustomerService(
            CustomerOutputPort customerOutputPort,
            CustomerAppMapper customerAppMapper
    ) {
        return new CreateCustomerService(customerOutputPort, customerAppMapper);
    }

    @Bean
    UpdateCustomerContactUseCase updateCustomerContactUseCase(
            CustomerOutputPort customerOutputPort,
            CustomerAppMapper customerAppMapper
    ) {
        return new UpdateCustomerContactService(customerOutputPort, customerAppMapper);
    }

    @Bean
    UpdateCustomerContactService updateCustomerContactService(
            CustomerOutputPort customerOutPort,
            CustomerAppMapper customerAppMapper
    ) {
        return new UpdateCustomerContactService(customerOutPort, customerAppMapper);
    }

    @Bean
    UpdateCustomerFinancialProfileUseCase updateCustomerFinancialProfileUseCase(
            CustomerOutputPort customerOutputPort,
            LoanApplicationOutputPort loanApplicationOutputPort,
            RiskAssessmentOutputPort riskAssessmentOutputPort,
            CustomerAppMapper customerAppMapper
    ) {
        return new UpdateCustomerFinancialProfileService(
                customerOutputPort,
                loanApplicationOutputPort,
                riskAssessmentOutputPort,
                customerAppMapper);
    }

    @Bean
    UpdateCustomerFinancialProfileService UpdateCustomerFinancialProfileService(
            CustomerOutputPort customerOutPort,
            LoanApplicationOutputPort loanApplicationOutputPort,
            RiskAssessmentOutputPort riskAssessmentOutputPort,
            CustomerAppMapper customerAppMapper
    ) {
        return new UpdateCustomerFinancialProfileService(
                customerOutPort,
                loanApplicationOutputPort,
                riskAssessmentOutputPort,
                customerAppMapper);
    }
}

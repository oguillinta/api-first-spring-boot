package com.iogui.apps.loans.evaluation.configuration;

import com.iogui.apps.loans.evaluation.application.mappers.loan.LoanAppMapper;
import com.iogui.apps.loans.evaluation.application.mappers.loan.LoanAppMapperImpl;
import com.iogui.apps.loans.evaluation.application.ports.input.loan.*;
import com.iogui.apps.loans.evaluation.application.ports.output.CustomerOutputPort;
import com.iogui.apps.loans.evaluation.application.ports.output.LoanApplicationOutputPort;
import com.iogui.apps.loans.evaluation.application.ports.output.RiskAssessmentOutputPort;
import com.iogui.apps.loans.evaluation.application.services.loan.*;
import com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.adapters.LoanApplicationSqlDbMySQLAdapter;
import com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.mappers.loan.LoanMySQLMapper;
import com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.mappers.loan.LoanMySQLMapperImpl;
import com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.repositories.LoanApplicationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoanAppManagementConfig {

    @Bean
    LoanApplicationSqlDbMySQLAdapter loanApplicationSqlDbMySQLAdapter(
            LoanApplicationRepository loanApplicationRepository,
            LoanMySQLMapper loanMySQLMapper
    ) {
        return new LoanApplicationSqlDbMySQLAdapter(loanApplicationRepository, loanMySQLMapper);
    }

    @Bean
    LoanAppMapper loanAppMapper() {
        return new LoanAppMapperImpl();
    }

    @Bean
    LoanMySQLMapper loanMySQLMapper() {
        return new LoanMySQLMapperImpl();
    }

    @Bean
    GetLoanApplicationsByCustomerUseCase getLoanApplicationsByCustomerUseCase(
            LoanApplicationOutputPort loanApplicationOutputPort,
            LoanAppMapper loanAppMapper
    ) {
        return new GetLoanApplicationsByCustomerService(loanApplicationOutputPort, loanAppMapper);
    }

    @Bean
    GetLoanApplicationsByStatusUseCase getLoanApplicationsByStatusUseCase(
            LoanApplicationOutputPort loanApplicationOutputPort,
            LoanAppMapper loanAppMapper
    ) {
        return new GetLoanApplicationsByStatusService(loanApplicationOutputPort, loanAppMapper);
    }

    @Bean
    DecideOnLoanApplicationUseCase decideOnLoanApplicationUseCase(
            LoanApplicationOutputPort loanApplicationOutputPort,
            LoanAppMapper loanAppMapper
    ) {
        return new DecideOnLoanApplicationService(loanApplicationOutputPort, loanAppMapper);
    }

    @Bean
    DecideOnLoanApplicationService decideOnLoanApplicationService(
            LoanApplicationOutputPort loanApplicationOutputPort,
            LoanAppMapper loanAppMapper
    ) {
        return new DecideOnLoanApplicationService(loanApplicationOutputPort, loanAppMapper);
    }

    @Bean
    PerformRiskAssessmentUseCase performRiskAssessmentUseCase(
            LoanApplicationOutputPort loanApplicationOutputPort,
            CustomerOutputPort customerOutputPort,
            RiskAssessmentOutputPort riskAssessmentOutputPort
    ) {
        return new PerformRiskAssessmentService(
                loanApplicationOutputPort,
                customerOutputPort,
                riskAssessmentOutputPort);
    }

    @Bean
    PerformRiskAssessmentService performRiskAssessmentService(
            LoanApplicationOutputPort loanApplicationOutputPort,
            CustomerOutputPort customerOutputPort,
            RiskAssessmentOutputPort riskAssessmentOutputPort
    ) {
        return new PerformRiskAssessmentService(
                loanApplicationOutputPort,
                customerOutputPort,
                riskAssessmentOutputPort);
    }

    @Bean
    SubmitLoanApplicationUseCase submitLoanApplicationUseCase(
            LoanApplicationOutputPort loanApplicationOutputPort,
            CustomerOutputPort customerOutputPort,
            LoanAppMapper loanAppMapper
    ) {
        return new SubmitLoanApplicationService(loanApplicationOutputPort, customerOutputPort, loanAppMapper);
    }

    @Bean
    SubmitLoanApplicationService submitLoanApplicationService(
            LoanApplicationOutputPort loanApplicationOutputPort,
            CustomerOutputPort customerOutputPort,
            LoanAppMapper loanAppMapper
    ) {
        return new SubmitLoanApplicationService(loanApplicationOutputPort, customerOutputPort, loanAppMapper);
    }
}

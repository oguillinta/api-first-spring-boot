package com.iogui.apps.loans.evaluation.application.services.customer;

import com.iogui.apps.loans.evaluation.application.exceptions.CustomerNotFoundException;
import com.iogui.apps.loans.evaluation.application.mappers.customer.CustomerAppMapper;
import com.iogui.apps.loans.evaluation.application.ports.input.customer.UpdateCustomerFinancialProfileUseCase;
import com.iogui.apps.loans.evaluation.application.ports.output.CustomerOutputPort;
import com.iogui.apps.loans.evaluation.application.ports.output.LoanApplicationOutputPort;
import com.iogui.apps.loans.evaluation.application.ports.output.RiskAssessmentOutputPort;
import com.iogui.apps.loans.evaluation.domain.Customer;
import com.iogui.apps.loans.evaluation.domain.FinancialProfile;
import com.iogui.apps.loans.evaluation.domain.LoanApplication;
import com.iogui.apps.loans.evaluation.domain.RiskProfile;
import com.iogui.apps.loans.evaluation.domain.enums.ApplicationStatus;
import com.iogui.apps.loans.evaluation.model.FinancialProfileRequest;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

@Transactional
public class UpdateCustomerFinancialProfileService implements UpdateCustomerFinancialProfileUseCase {
    private static final Logger LOG = LoggerFactory.getLogger(UpdateCustomerFinancialProfileService.class);
    private final CustomerOutputPort customerOutputPort;
    private final LoanApplicationOutputPort loanApplicationOutputPort;
    private final RiskAssessmentOutputPort riskAssessmentOutputPort;
    private final CustomerAppMapper customerAppMapper;

    public UpdateCustomerFinancialProfileService(
            CustomerOutputPort customerOutputPort,
            LoanApplicationOutputPort loanApplicationOutputPort,
            RiskAssessmentOutputPort riskAssessmentOutputPort,
            CustomerAppMapper customerAppMapper
    ) {
        this.customerOutputPort = customerOutputPort;
        this.loanApplicationOutputPort = loanApplicationOutputPort;
        this.riskAssessmentOutputPort = riskAssessmentOutputPort;

        this.customerAppMapper = customerAppMapper;
    }

    @Override
    @Transactional
    public void updateCustomerFinancialProfile(UUID customerId, FinancialProfileRequest request) {
        LOG.info("Updating financial profile information for customer: {}", customerId);

        FinancialProfile financialProfile = customerAppMapper.mapToFinancialProfileDomain(request);

        Customer customer = customerOutputPort.updateCustomerFinancialProfile(customerId, financialProfile)
                .orElseThrow(() -> {
                    LOG.warn("Customer not found for financial update: {}", customerId);
                    return new CustomerNotFoundException("Customer not found with id:" + customerId);
                });

        LOG.info("Successfully updated risk profile information for customer: {}", customerId);

        List<LoanApplication> activeLoans = loanApplicationOutputPort
                .getLoanApplicationsByCustomer(customerId)
                .stream()
                .filter(loan -> loan.getStatus() == ApplicationStatus.PENDING_REVIEW
                    && loan.getCustomerId() == customerId)
                .toList();

        for (LoanApplication loan : activeLoans) {
            RiskProfile newRiskProfile = riskAssessmentOutputPort.assessRisk(customer, loan);
            loan.applyRiskAssessment(newRiskProfile);
            loanApplicationOutputPort.updateLoanApplication(loan);
        }

        LOG.info("Successfully updated loan applications for customer: {}", customerId);

    }
}

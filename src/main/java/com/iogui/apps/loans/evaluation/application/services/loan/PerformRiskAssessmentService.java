package com.iogui.apps.loans.evaluation.application.services.loan;

import com.iogui.apps.loans.evaluation.application.exceptions.CustomerNotFoundException;
import com.iogui.apps.loans.evaluation.application.exceptions.LoanApplicationNotFoundException;
import com.iogui.apps.loans.evaluation.application.ports.input.loan.PerformRiskAssessmentUseCase;
import com.iogui.apps.loans.evaluation.application.ports.output.CustomerOutputPort;
import com.iogui.apps.loans.evaluation.application.ports.output.LoanApplicationOutputPort;
import com.iogui.apps.loans.evaluation.application.ports.output.RiskAssessmentOutputPort;
import com.iogui.apps.loans.evaluation.domain.Customer;
import com.iogui.apps.loans.evaluation.domain.LoanApplication;
import com.iogui.apps.loans.evaluation.domain.RiskProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class PerformRiskAssessmentService implements PerformRiskAssessmentUseCase {
    private static final Logger LOG = LoggerFactory.getLogger(PerformRiskAssessmentService.class);
    private final LoanApplicationOutputPort loanApplicationOutputPort;
    private final CustomerOutputPort customerOutputPort;
    private final RiskAssessmentOutputPort riskAssessmentOutputPort;

    public PerformRiskAssessmentService(
            LoanApplicationOutputPort loanApplicationOutputPort,
            CustomerOutputPort customerOutputPort,
            RiskAssessmentOutputPort riskAssessmentOutputPort) {
        this.loanApplicationOutputPort = loanApplicationOutputPort;
        this.customerOutputPort = customerOutputPort;
        this.riskAssessmentOutputPort = riskAssessmentOutputPort;
    }

    @Override
    public void performRiskAssessment(UUID loanApplicationId) {
        LOG.info("Starting risk assessment for loan application: {}", loanApplicationId);

        LoanApplication loanApplication = loanApplicationOutputPort.getLoanApplicationById(loanApplicationId)
                .orElseThrow(() ->{
                    LOG.warn("Loan application not found with risk assessment: {}", loanApplicationId);
                    return new LoanApplicationNotFoundException("Loan Application not found with id: " + loanApplicationId);
                });

        Customer customer = customerOutputPort.getCustomerById(loanApplication.getCustomerId())
                .orElseThrow(() -> {
                    LOG.warn("Customer not found for loan application {}: customer ID{}", loanApplicationId, loanApplication.getCustomerId());
                    return new CustomerNotFoundException("Customer not found with id: " + loanApplication.getCustomerId());
                });

        LOG.info("Performing risk assessment for customer: {} with loan amount: {}",
                customer.getId(), loanApplication.getRequestedAmount().getAmount());

        RiskProfile riskProfile = riskAssessmentOutputPort.assessRisk(customer, loanApplication);

        LOG.info("Risk assessment completed for loan application: {} - Risk Level: {}. Eligible: {}",
                    loanApplicationId, riskProfile.getRiskLevel(), riskProfile.getIsEligible());

        loanApplication.applyRiskAssessment(riskProfile);

        loanApplicationOutputPort.updateLoanApplication(loanApplication);

        LOG.info("Successfully applied risk assessment to loan application: {} - New Status: {}",
                loanApplicationId, loanApplication.getStatus());
    }
}

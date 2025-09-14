package com.iogui.apps.loans.evaluation.application.services.loan;

import com.iogui.apps.loans.evaluation.application.exceptions.LoanApplicationNotFoundException;
import com.iogui.apps.loans.evaluation.application.mappers.loan.LoanAppMapper;
import com.iogui.apps.loans.evaluation.application.ports.input.loan.DecideOnLoanApplicationUseCase;
import com.iogui.apps.loans.evaluation.application.ports.output.LoanApplicationOutputPort;
import com.iogui.apps.loans.evaluation.domain.Decision;
import com.iogui.apps.loans.evaluation.domain.LoanApplication;
import com.iogui.apps.loans.evaluation.domain.enums.DecisionType;
import com.iogui.apps.loans.evaluation.model.DecideOnLoanApplicationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;


public class DecideOnLoanApplicationService implements DecideOnLoanApplicationUseCase {
    private static final Logger LOG = LoggerFactory.getLogger(DecideOnLoanApplicationService.class);
    private final LoanApplicationOutputPort loanApplicationOutputPort;
    private final LoanAppMapper loanAppMapper;

    public DecideOnLoanApplicationService(LoanApplicationOutputPort loanApplicationOutputPort, LoanAppMapper loanAppMapper) {
        this.loanApplicationOutputPort = loanApplicationOutputPort;
        this.loanAppMapper = loanAppMapper;
    }


    @Override
    public void decideOnLoanApplication(UUID loanApplicationId, DecideOnLoanApplicationRequest request) {
        LOG.info("Processing loan decision for application: {} with decision type: {}",
                loanApplicationId, request.getDecisionType());

        LoanApplication loanApplication = loanApplicationOutputPort.getLoanApplicationById(loanApplicationId)
                .orElseThrow(() -> {
                            LOG.warn("Loan application not found for decision: {}", loanApplicationId);
                            return new LoanApplicationNotFoundException("Loan application not found with id:" + loanApplicationId);
                        }
                );

        Decision decision = loanAppMapper.mapToDecisionDomain(request);

        if (decision.getType() == DecisionType.APPROVED) {
            loanApplication.approved(decision);
            LOG.info("Loan application {} approved with amount: {}", loanApplicationId, decision.getApprovedAmount());
        } else {
            loanApplication.reject(decision);
            LOG.info("Loan application {} rejected. Reason: {}", loanApplicationId, decision.getNotes());
        }

        loanApplicationOutputPort.updateLoanApplication(loanApplication);
        LOG.info("Successfully processed decision for loan application: {}", loanApplicationId);

    }
}

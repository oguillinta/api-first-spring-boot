package com.iogui.apps.loans.evaluation.application.services.loan;

import com.iogui.apps.loans.evaluation.application.exceptions.CustomerNotFoundException;
import com.iogui.apps.loans.evaluation.application.mappers.loan.LoanAppMapper;
import com.iogui.apps.loans.evaluation.application.ports.input.loan.SubmitLoanApplicationUseCase;
import com.iogui.apps.loans.evaluation.application.ports.output.CustomerOutputPort;
import com.iogui.apps.loans.evaluation.application.ports.output.LoanApplicationOutputPort;
import com.iogui.apps.loans.evaluation.domain.LoanApplication;
import com.iogui.apps.loans.evaluation.domain.Money;
import com.iogui.apps.loans.evaluation.domain.enums.LoanType;
import com.iogui.apps.loans.evaluation.model.LoanApplicationRequest;
import com.iogui.apps.loans.evaluation.model.LoanApplicationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubmitLoanApplicationService implements SubmitLoanApplicationUseCase {
    private static final Logger LOG = LoggerFactory.getLogger(SubmitLoanApplicationService.class);
    private final LoanApplicationOutputPort loanApplicationOutputPort;
    private final CustomerOutputPort customerOutputPort;
    private final LoanAppMapper loanAppMapper;

    public SubmitLoanApplicationService(
            LoanApplicationOutputPort loanApplicationOutputPort,
            CustomerOutputPort customerOutputPort,
            LoanAppMapper loanAppMapper) {
        this.loanApplicationOutputPort = loanApplicationOutputPort;
        this.customerOutputPort = customerOutputPort;
        this.loanAppMapper = loanAppMapper;
    }

    @Override
    public LoanApplicationResponse submitLoanApplication(LoanApplicationRequest request) {
        LOG.info("Submitting loan application for customer: {} with amount: {}",
                request.getCustomerId(), request.getRequestedAmount());

        customerOutputPort.getCustomerById(request.getCustomerId())
                .orElseThrow(() -> {
                    LOG.warn("Customer not found during loan submission: {}", request.getCustomerId());
                    return new CustomerNotFoundException("Customer not found with id:" + request.getCustomerId());
                });

        LoanApplication loanApplication = LoanApplication.submit(
                request.getCustomerId(),
                Money.of(request.getRequestedAmount(), "USD"),
                request.getPurpose(),
                LoanType.valueOf(request.getLoanType())
        );

        LoanApplication savedApplication = loanApplicationOutputPort.submitLoanApplication(loanApplication);

        LOG.info("Successfully submitted loan application: {} for customer: {} with amount: {}",
                savedApplication.getLoanApplicationId(),
                request.getCustomerId(),
                request.getRequestedAmount());

        return loanAppMapper.mapToLoanApplicationResponse(savedApplication);
    }
}

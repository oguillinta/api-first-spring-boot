package com.iogui.apps.loans.evaluation.application.services.loan;

import com.iogui.apps.loans.evaluation.application.mappers.loan.LoanAppMapper;
import com.iogui.apps.loans.evaluation.application.ports.input.loan.GetLoanApplicationsByCustomerUseCase;
import com.iogui.apps.loans.evaluation.application.ports.output.LoanApplicationOutputPort;
import com.iogui.apps.loans.evaluation.domain.LoanApplication;
import com.iogui.apps.loans.evaluation.model.LoanApplicationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class GetLoanApplicationsByCustomerService implements GetLoanApplicationsByCustomerUseCase {
    private static final Logger LOG = LoggerFactory.getLogger(GetLoanApplicationsByCustomerService.class);
    private final LoanApplicationOutputPort loanApplicationOutputPort;
    private final LoanAppMapper loanAppMapper;

    public GetLoanApplicationsByCustomerService(
            LoanApplicationOutputPort loanApplicationOutputPort,
            LoanAppMapper loanAppMapper) {
        this.loanApplicationOutputPort = loanApplicationOutputPort;
        this.loanAppMapper = loanAppMapper;
    }

    @Override
    public List<LoanApplicationResponse> getLoanApplicationsByCustomer(UUID customerId) {
        LOG.info("Retrieving loan application for customer: {}", customerId);

        List<LoanApplication> loanApplicationList = loanApplicationOutputPort.getLoanApplicationsByCustomer(customerId);

        LOG.info("Found {} loan application for customer: {}", loanApplicationList.size(), customerId);

        return loanApplicationList
                .stream()
                .map(loanAppMapper::mapToLoanApplicationResponse)
                .collect(Collectors.toList());
    }
}

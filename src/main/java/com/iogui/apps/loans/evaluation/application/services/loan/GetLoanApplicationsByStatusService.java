package com.iogui.apps.loans.evaluation.application.services.loan;

import com.iogui.apps.loans.evaluation.application.mappers.loan.LoanAppMapper;
import com.iogui.apps.loans.evaluation.application.ports.input.loan.GetLoanApplicationsByStatusUseCase;
import com.iogui.apps.loans.evaluation.application.ports.output.LoanApplicationOutputPort;
import com.iogui.apps.loans.evaluation.domain.LoanApplication;
import com.iogui.apps.loans.evaluation.model.LoanApplicationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class GetLoanApplicationsByStatusService implements GetLoanApplicationsByStatusUseCase {
    private static final Logger LOG = LoggerFactory.getLogger(GetLoanApplicationsByStatusService.class);
    private final LoanApplicationOutputPort loanApplicationOutputPort;
    private final LoanAppMapper loanAppMapper;

    public GetLoanApplicationsByStatusService(
            LoanApplicationOutputPort loanApplicationOutputPort,
            LoanAppMapper loanAppMapper) {
        this.loanApplicationOutputPort = loanApplicationOutputPort;
        this.loanAppMapper = loanAppMapper;
    }


    @Override
    public List<LoanApplicationResponse> getLoanApplicationsByStatus(String status) {
        LOG.info("Retrieving loan applications with status: {}", status);

        List<LoanApplication> loanApplicationsList = loanApplicationOutputPort.getLoanApplicationsByStatus(status);

        LOG.info("Found {} loan applications with status: {}", loanApplicationsList.size(), status);

        return loanApplicationsList
                .stream()
                .map(loanAppMapper::mapToLoanApplicationResponse)
                .collect(Collectors.toList());
    }
}

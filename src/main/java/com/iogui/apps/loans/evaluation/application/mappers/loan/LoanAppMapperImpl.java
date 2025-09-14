package com.iogui.apps.loans.evaluation.application.mappers.loan;

import com.iogui.apps.loans.evaluation.domain.Decision;
import com.iogui.apps.loans.evaluation.domain.LoanApplication;
import com.iogui.apps.loans.evaluation.domain.Money;
import com.iogui.apps.loans.evaluation.domain.enums.DecisionType;
import com.iogui.apps.loans.evaluation.model.DecideOnLoanApplicationRequest;
import com.iogui.apps.loans.evaluation.model.LoanApplicationResponse;

import java.time.LocalDateTime;

public class LoanAppMapperImpl implements LoanAppMapper {
    @Override
    public LoanApplicationResponse mapToLoanApplicationResponse(LoanApplication loanApplication) {
       LoanApplicationResponse loanApplicationResponse = new LoanApplicationResponse();
       loanApplicationResponse.setId(loanApplication.getLoanApplicationId());
       loanApplicationResponse.setCustomerId(loanApplication.getCustomerId());
       loanApplicationResponse.setRequestedAmount(loanApplication.getRequestedAmount().getAmount());
       loanApplicationResponse.setLoanType(loanApplication.getType().toString());
       loanApplicationResponse.setPurpose(loanApplication.getPurpose());
       loanApplicationResponse.setStatus(loanApplication.getStatus().toString());
       loanApplicationResponse.setSubmittedAt(loanApplication.getSubmittedAt().toString());
       return loanApplicationResponse;
    }

    @Override
    public Decision mapToDecisionDomain(DecideOnLoanApplicationRequest request) {
        //REVIEW
        return new Decision(
                DecisionType.valueOf(request.getDecisionType().name()),
                request.getNotes(),
                request.getDecidedBy(),
                LocalDateTime.now(),
                Money.usd(request.getApprovedAmount().getAmount())
        );
    }
}


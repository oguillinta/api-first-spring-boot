package com.iogui.apps.loans.evaluation.application.mappers.loan;

import com.iogui.apps.loans.evaluation.domain.Decision;
import com.iogui.apps.loans.evaluation.domain.LoanApplication;
import com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.entities.LoanApplicationEntity;
import com.iogui.apps.loans.evaluation.model.DecideOnLoanApplicationRequest;
import com.iogui.apps.loans.evaluation.model.LoanApplicationResponse;

public interface LoanAppMapper {
    LoanApplicationResponse mapToLoanApplicationResponse(LoanApplication loanApplication);
    Decision mapToDecisionDomain(DecideOnLoanApplicationRequest request);
}

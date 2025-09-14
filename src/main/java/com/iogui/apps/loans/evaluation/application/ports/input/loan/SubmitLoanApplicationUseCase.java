package com.iogui.apps.loans.evaluation.application.ports.input.loan;

import com.iogui.apps.loans.evaluation.model.LoanApplicationRequest;
import com.iogui.apps.loans.evaluation.model.LoanApplicationResponse;

@FunctionalInterface
public interface SubmitLoanApplicationUseCase {
    LoanApplicationResponse submitLoanApplication(LoanApplicationRequest request);
}

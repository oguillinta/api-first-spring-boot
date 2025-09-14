package com.iogui.apps.loans.evaluation.application.ports.output;

import com.iogui.apps.loans.evaluation.domain.ContactInformation;

import com.iogui.apps.loans.evaluation.domain.LoanApplication;
import com.iogui.apps.loans.evaluation.domain.RiskProfile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoanApplicationOutputPort {

    Optional<LoanApplication> getLoanApplicationById(UUID id);

    List<LoanApplication> getLoanApplicationsByCustomer(UUID customerId);

    List<LoanApplication> getLoanApplicationsByStatus(String status);

    Optional<LoanApplication> updateLoanApplication(LoanApplication loanApplication);

    LoanApplication submitLoanApplication(LoanApplication loanApplication);

}

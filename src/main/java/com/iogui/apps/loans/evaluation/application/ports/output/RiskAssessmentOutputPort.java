package com.iogui.apps.loans.evaluation.application.ports.output;

import com.iogui.apps.loans.evaluation.domain.Customer;
import com.iogui.apps.loans.evaluation.domain.LoanApplication;
import com.iogui.apps.loans.evaluation.domain.RiskProfile;

public interface RiskAssessmentOutputPort {
    RiskProfile assessRisk(Customer customer, LoanApplication loanApplication);
}

package com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.mappers.loan;

import com.iogui.apps.loans.evaluation.domain.LoanApplication;
import com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.entities.LoanApplicationEntity;

public interface LoanMySQLMapper {
    LoanApplication mapToLoanApplicationDomain(LoanApplicationEntity entity);
    LoanApplicationEntity mapToLoanApplicationEntity(LoanApplication domain);
}

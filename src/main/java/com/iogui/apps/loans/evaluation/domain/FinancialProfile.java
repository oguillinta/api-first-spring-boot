package com.iogui.apps.loans.evaluation.domain;

import java.math.BigDecimal;

public class FinancialProfile {
    private final Money monthlyIncome;
    private final Money monthlyExpenses;
    private final Money existingDebt;
    private final int yearsOfEmployment;
    private final BigDecimal debtToIncomeRatio;

    public FinancialProfile(
            Money monthlyIncome,
            Money monthlyExpenses,
            Money existingDebt,
            int yearsOfEmployment,
            BigDecimal debtToIncomeRatio) {
        this.monthlyIncome = monthlyIncome;
        this.monthlyExpenses = monthlyExpenses;
        this.existingDebt = existingDebt;
        this.yearsOfEmployment = yearsOfEmployment;
        this.debtToIncomeRatio = debtToIncomeRatio;
    }

    public Money getMonthlyIncome() {
        return monthlyIncome;
    }

    public Money getMonthlyExpenses() {
        return monthlyExpenses;
    }

    public Money getExistingDebt() {
        return existingDebt;
    }

    public int getYearsOfEmployment() {
        return yearsOfEmployment;
    }

    public BigDecimal getDebtToIncomeRatio() {
        return debtToIncomeRatio;
    }
}

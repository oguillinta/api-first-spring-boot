package com.iogui.apps.loans.evaluation.infrastructure.output.api.rest;

import com.iogui.apps.loans.evaluation.application.ports.output.RiskAssessmentOutputPort;
import com.iogui.apps.loans.evaluation.domain.Customer;
import com.iogui.apps.loans.evaluation.domain.FinancialProfile;
import com.iogui.apps.loans.evaluation.domain.LoanApplication;
import com.iogui.apps.loans.evaluation.domain.RiskProfile;
import com.iogui.apps.loans.evaluation.domain.enums.RiskLevel;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Component
public class MoodysRiskAssessmentAdapter implements RiskAssessmentOutputPort {
    private static final BigDecimal MAX_DEBT_TO_INCOME_RATIO = new BigDecimal("0.40");
    private static final BigDecimal MIN_MONTHLY_INCOME = new BigDecimal("2000");
    private static final int MIN_EMPLOYMENT_YEARS = 2;

    @Override
    public RiskProfile assessRisk(Customer customer, LoanApplication loanApplication) {
        List<String> flags = new ArrayList<>();
        boolean isEligible = true;

        int creditScore = calculateCreditScore(customer);
        BigDecimal debtToIncomeRatio = customer.getFinancialProfile().getDebtToIncomeRatio();
        if (debtToIncomeRatio.compareTo(MAX_DEBT_TO_INCOME_RATIO) > 0) {
            isEligible = false;
            flags.add("High debt-to-income ratio: " + debtToIncomeRatio.multiply(new BigDecimal("100"))
                    .setScale(1, RoundingMode.HALF_UP) + "%");
        }

        if (customer.getFinancialProfile().getMonthlyIncome().getAmount().compareTo(MIN_MONTHLY_INCOME) < 0) {
            isEligible = false;
            flags.add("Monthly income below minimun requirement");
        }

        if (customer.getFinancialProfile().getYearsOfEmployment() < MIN_EMPLOYMENT_YEARS) {
            isEligible = false;
            flags.add("Insufficient employment history");
        }

        if (creditScore < 600) {
            isEligible = false;
            flags.add("Low credit score");
        } else if (creditScore < 650) {
            flags.add("Below average credit score");
        }

        BigDecimal loanToIncomeRatio = loanApplication.getRequestedAmount().getAmount()
                .divide(customer.getFinancialProfile().getMonthlyIncome().getAmount().multiply(new BigDecimal("12")),
                        4, RoundingMode.HALF_UP);

        if (loanToIncomeRatio.compareTo(new BigDecimal("5")) > 0) {
            isEligible = false;
            flags.add("Requested loan amount too high relative to annual income");
        }

        RiskLevel riskLevel = determineRiskLevel(creditScore, debtToIncomeRatio, flags.size());

        if (isEligible && flags.isEmpty()) {
            flags.add("All risk criteria met");
        }

        return new RiskProfile(creditScore, isEligible, riskLevel, flags);
    }


    private int calculateCreditScore(Customer customer) {
        FinancialProfile profile = customer.getFinancialProfile();

        int baseScore = 650;

        // Adjust based on debt-to-income ratio
        BigDecimal debtToIncome = profile.getDebtToIncomeRatio();

        if (debtToIncome.compareTo(new BigDecimal("0.10")) <= 0) {
            baseScore += 100; // Very low debt
        } else if (debtToIncome.compareTo(new BigDecimal("0.20")) <= 0) {
            baseScore += 50; // Low debt
        } else if (debtToIncome.compareTo(new BigDecimal("0.30")) <= 0) {
            baseScore += 20; // Moderate debt
        } else if (debtToIncome.compareTo(new BigDecimal("0.40")) <= 0) {
            baseScore -= 20; // High debt
        } else {
            baseScore -= 100; // Very high debt
        }

        // Adjust based on income level
        BigDecimal monthlyIncome = profile.getMonthlyIncome().getAmount();
        if (monthlyIncome.compareTo(new BigDecimal("8000")) >= 0) {
            baseScore += 50; // High income
        } else if (monthlyIncome.compareTo(new BigDecimal("5000")) >= 0) {
            baseScore += 25; // Good income
        } else if (monthlyIncome.compareTo(new BigDecimal("3000")) < 0) {
            baseScore -= 25; // Low income
        }

        // Adjust based on employment stability
        int employmentYears = profile.getYearsOfEmployment();
        if (employmentYears >= 5) {
            baseScore += 30;
        } else if (employmentYears >= 2) {
            baseScore += 10;
        } else if (employmentYears < 1) {
            baseScore -= 50;
        }

        // Add some randomness but keep it consistent per customer
        int customerVariation = Math.abs(customer.getId().hashCode() % 50) - 25;
        baseScore += customerVariation;

        // Ensure score is within valid range
        return Math.max(300, Math.min(850, baseScore));

    }

    private RiskLevel determineRiskLevel(int creditScore, BigDecimal debtToIncomeRatio, int flagCount) {
        if (creditScore < 600 || debtToIncomeRatio.compareTo(new BigDecimal("0.35")) > 0 || flagCount >= 3) {
            return RiskLevel.HIGH;
        }
        if (creditScore < 700 || debtToIncomeRatio.compareTo(new BigDecimal("0.25")) > 0 || flagCount >= 1) {
            return RiskLevel.MEDIUM;
        }
        return RiskLevel.LOW;
    }
}

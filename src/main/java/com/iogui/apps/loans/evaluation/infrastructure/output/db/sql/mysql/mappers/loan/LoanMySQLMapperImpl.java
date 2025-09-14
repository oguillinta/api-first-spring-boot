package com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.mappers.loan;

import com.iogui.apps.loans.evaluation.domain.Decision;
import com.iogui.apps.loans.evaluation.domain.LoanApplication;
import com.iogui.apps.loans.evaluation.domain.Money;
import com.iogui.apps.loans.evaluation.domain.RiskProfile;
import com.iogui.apps.loans.evaluation.domain.enums.ApplicationStatus;
import com.iogui.apps.loans.evaluation.domain.enums.DecisionType;
import com.iogui.apps.loans.evaluation.domain.enums.LoanType;
import com.iogui.apps.loans.evaluation.domain.enums.RiskLevel;
import com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.entities.LoanApplicationEntity;

import java.util.Arrays;
import java.util.Collections;


public class LoanMySQLMapperImpl implements LoanMySQLMapper {
    @Override
    public LoanApplication mapToLoanApplicationDomain(LoanApplicationEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("LoanApplicationEntity cannot be null");
        }

        // Only create RiskProfile if assessment data exists
        RiskProfile riskProfile = null;
        if (entity.getCreditScore() != null || entity.getIsEligible() != null || entity.getRiskLevel() != null) {
            riskProfile = new RiskProfile(
                    entity.getCreditScore() != null ? entity.getCreditScore() : 0,
                    entity.getIsEligible() != null ? entity.getIsEligible() : false,
                    entity.getRiskLevel() != null ? RiskLevel.valueOf(entity.getRiskLevel()) : RiskLevel.MEDIUM,
                    entity.getRiskFlags() != null && !entity.getRiskFlags().trim().isEmpty() ?
                            Arrays.stream(entity.getRiskFlags().split(","))
                                    .map(String::trim)
                                    .filter(flag -> !flag.isEmpty())
                                    .toList() :
                            Collections.emptyList()
            );
        }

        // Only create Decision if decision data exists
        Decision decision = null;
        if (entity.getDecisionType() != null && !entity.getDecisionType().trim().isEmpty()) {
            try {
                Money approvedAmount = null;
                if (entity.getApprovedAmount() != null) {
                    approvedAmount = Money.of(entity.getApprovedAmount(), "USD");
                }

                decision = new Decision(
                        DecisionType.valueOf(entity.getDecisionType()),
                        entity.getDecisionNotes(),
                        entity.getDecidedBy(),
                        entity.getDecidedAt(),
                        approvedAmount
                );
            } catch (IllegalArgumentException e) {
                // Invalid decision type in database, leave decision as null
                decision = null;
            }
        }

        return new LoanApplication(
                entity.getId(),
                entity.getCustomerId(),
                Money.of(entity.getRequestedAmount(), "USD"),
                entity.getPurpose(),
                entity.getLoanType() != null ? LoanType.valueOf(entity.getLoanType()) : LoanType.PERSONAL,
                entity.getStatus() != null ? ApplicationStatus.valueOf(entity.getStatus()) : ApplicationStatus.SUBMITTED,
                entity.getSubmittedAt(),
                riskProfile,  // null if no assessment performed
                decision,     // null if no decision made
                entity.getDecidedAt()
        );
    }

    @Override
    public LoanApplicationEntity mapToLoanApplicationEntity(LoanApplication domain) {
        if (domain == null) {
            throw new IllegalArgumentException("LoanApplication domain cannot be null");
        }

        LoanApplicationEntity entity = new LoanApplicationEntity();

        // Map basic fields - always present
        entity.setCustomerId(domain.getCustomerId());
        entity.setRequestedAmount(domain.getRequestedAmount().getAmount());
        entity.setCurrency(domain.getRequestedAmount().getCurrency().getCurrencyCode());
        entity.setPurpose(domain.getPurpose());
        entity.setLoanType(domain.getType().name());
        entity.setStatus(domain.getStatus().name());
        entity.setSubmittedAt(domain.getSubmittedAt());
        entity.setDecidedAt(domain.getDecidedAt());

        // Map RiskProfile - only if it exists
        RiskProfile riskProfile = domain.getRiskProfile();
        if (riskProfile != null) {
            entity.setCreditScore(riskProfile.getCreditScore());
            entity.setIsEligible(riskProfile.getIsEligible());
            entity.setRiskLevel(riskProfile.getRiskLevel().name());
            entity.setRiskFlags(riskProfile.getFlags() != null && !riskProfile.getFlags().isEmpty() ?
                    String.join(",", riskProfile.getFlags()) : null);
        }
        // If riskProfile is null, leave all risk fields as null in database

        // Map Decision - only if it exists
        Decision decision = domain.getDecision();
        if (decision != null) {
            entity.setDecisionType(decision.getType().name());
            entity.setDecisionNotes(decision.getNotes());
            entity.setDecidedBy(decision.getDecidedBy());
            if (decision.getApprovedAmount() != null) {
                entity.setApprovedAmount(decision.getApprovedAmount().getAmount());
            }
        }
        // If decision is null, leave all decision fields as null in database

        return entity;
    }
}
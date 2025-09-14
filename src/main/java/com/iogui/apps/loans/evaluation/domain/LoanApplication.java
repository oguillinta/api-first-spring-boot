package com.iogui.apps.loans.evaluation.domain;

import com.iogui.apps.loans.evaluation.domain.enums.ApplicationStatus;
import com.iogui.apps.loans.evaluation.domain.enums.LoanType;
import com.iogui.apps.loans.evaluation.domain.enums.DecisionType;
import com.iogui.apps.loans.evaluation.domain.exceptions.InvalidDecisionException;
import com.iogui.apps.loans.evaluation.domain.exceptions.InvalidLoanApplicationStateException;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class LoanApplication {
    private  UUID loanApplicationId;
    private final UUID customerId;
    private final Money requestedAmount;
    private final String purpose;
    private final LoanType type;
    private ApplicationStatus status;
    private final LocalDateTime submittedAt;
    private RiskProfile riskProfile;
    private Decision decision;
    private LocalDateTime decidedAt;

    public LoanApplication(
            UUID customerId,
            Money requestedAmount,
            String purpose,
            LoanType type
    ) {
        //this.loanApplicationId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        this.customerId = customerId;
        this.requestedAmount = requestedAmount;
        this.purpose = purpose;
        this.type = type;
        this.status = ApplicationStatus.SUBMITTED;
        this.submittedAt = LocalDateTime.now();
    }

    public LoanApplication(UUID loanApplicationId,
                           UUID customerId,
                           Money requestedAmount,
                           String purpose,
                           LoanType type,
                           ApplicationStatus status,
                           LocalDateTime submittedAt,
                           RiskProfile riskProfile,
                           Decision decision,
                           LocalDateTime decidedAt) {
        this.loanApplicationId = loanApplicationId;
        this.customerId = customerId;
        this.requestedAmount = requestedAmount;
        this.purpose = purpose;
        this.type = type;
        this.status = status;
        this.submittedAt = submittedAt;
        this.riskProfile = riskProfile;
        this.decision = decision;
        this.decidedAt = decidedAt;
    }

    public static LoanApplication submit(UUID customerId, Money requestedAmount, String purpose, LoanType type) {
        return new LoanApplication(
                customerId,
                requestedAmount,
                purpose,
                type
        );
    }

    public void applyRiskAssessment(RiskProfile riskProfile) {
        if (this.status != ApplicationStatus.SUBMITTED && this.status != ApplicationStatus.PENDING_REVIEW) {
            throw new InvalidLoanApplicationStateException(
                    "Cannot perform risk analysis on application with status: " + this.status);
        }

        this.riskProfile = Objects.requireNonNull(riskProfile, "Risk profile cannot be null");
        this.status = riskProfile.getIsEligible() ?
                ApplicationStatus.PENDING_REVIEW :
                ApplicationStatus.AUTO_REJECTED;
    }

    public void approved(Decision approvalDecision) {
        if (this.status != ApplicationStatus.PENDING_REVIEW) {
            throw new InvalidLoanApplicationStateException(
                    "Cannot approve application with status: " + this.status);
        }

        if (approvalDecision.getType() != DecisionType.APPROVED) {
            throw new InvalidDecisionException("Decision must be of type APPROVED");
        }

        this.decision = approvalDecision;
        this.status = ApplicationStatus.APPROVED;
        this.decidedAt = LocalDateTime.now();
    }

    public void reject(Decision rejectionDecision) {
        if (this.status != ApplicationStatus.PENDING_REVIEW) {
            throw new InvalidLoanApplicationStateException(
                    "Cannot reject application with status: " + this.status);
        }

        if (rejectionDecision.getType() != DecisionType.REJECTED) {
            throw new InvalidDecisionException("Decision must be of type REJECTED");
        }

        this.decision = rejectionDecision;
        this.status = ApplicationStatus.REJECTED;
        this.decidedAt = LocalDateTime.now();
    }

    public boolean isPendingReview() {
        return this.status == ApplicationStatus.PENDING_REVIEW;
    }

    public boolean isDecided() {
        return this.status == ApplicationStatus.APPROVED || this.status == ApplicationStatus.REJECTED;
    }

    public boolean requiresManualReview() {
        return this.status == ApplicationStatus.PENDING_REVIEW;
    }

    public UUID getLoanApplicationId() {
        return loanApplicationId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public Money getRequestedAmount() {
        return requestedAmount;
    }

    public String getPurpose() {
        return purpose;
    }

    public LoanType getType() {
        return type;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public RiskProfile getRiskProfile() {
        return riskProfile;
    }

    public LocalDateTime getDecidedAt() {
        return decidedAt;
    }

    public Decision getDecision() {
        return decision;
    }
}

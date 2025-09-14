package com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.entities;

import com.iogui.apps.loans.evaluation.domain.enums.ApplicationStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="loan_applications")
public class LoanApplicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private CustomerEntity customer;

    @Column(name = "requested_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal requestedAmount;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Column(name = "purpose", nullable = false, length = 500)
    private String purpose;

    @Column(name = "loan_type", nullable = false)
    private String loanType;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt;

    @Column(name = "decided_at")
    private LocalDateTime decidedAt;

    @Column(name = "decision_type")
    private String decisionType;

    @Column(name = "decision_notes", length = 1000)
    private String decisionNotes;

    @Column(name = "decided_by", length = 100)
    private String decidedBy;

    @Column(name = "approved_amount", precision = 19, scale = 2)
    private BigDecimal approvedAmount;

    @Column(name = "credit_score")
    private Integer creditScore;

    @Column(name = "is_eligible")
    private Boolean isEligible;

    @Column(name = "risk_level")
    private String riskLevel;

    @Column(name = "risk_flags", length = 2000)
    private String riskFlags;


    public LoanApplicationEntity(UUID id, UUID customerId, BigDecimal requestedAmount,
                                 String currency, String purpose, String loanType) {
        this.id = id;
        this.customerId = customerId;
        this.requestedAmount = requestedAmount;
        this.currency = currency;
        this.purpose = purpose;
        this.loanType = loanType;
        this.status = ApplicationStatus.SUBMITTED.toString();
        this.submittedAt = LocalDateTime.now();
    }

    public LoanApplicationEntity() {};

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRiskFlags() {
        return riskFlags;
    }

    public void setRiskFlags(String riskFlags) {
        this.riskFlags = riskFlags;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public Boolean getIsEligible() {
        return isEligible;
    }

    public void setIsEligible(Boolean isEligible) {
        this.isEligible = isEligible;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public BigDecimal getApprovedAmount() {
        return approvedAmount;
    }

    public void setApprovedAmount(BigDecimal approvedAmount) {
        this.approvedAmount = approvedAmount;
    }

    public String getDecidedBy() {
        return decidedBy;
    }

    public void setDecidedBy(String decidedBy) {
        this.decidedBy = decidedBy;
    }

    public String getDecisionNotes() {
        return decisionNotes;
    }

    public void setDecisionNotes(String decisionNotes) {
        this.decisionNotes = decisionNotes;
    }

    public String getDecisionType() {
        return decisionType;
    }

    public void setDecisionType(String decisionType) {
        this.decisionType = decisionType;
    }

    public LocalDateTime getDecidedAt() {
        return decidedAt;
    }

    public void setDecidedAt(LocalDateTime decidedAt) {
        this.decidedAt = decidedAt;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(BigDecimal requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }
}

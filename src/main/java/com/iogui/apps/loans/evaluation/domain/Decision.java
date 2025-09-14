package com.iogui.apps.loans.evaluation.domain;

import com.iogui.apps.loans.evaluation.domain.enums.DecisionType;

import java.time.LocalDateTime;

public class Decision {
    private final DecisionType type;
    private final String notes;
    private final String decidedBy;
    private final LocalDateTime decidedAt;
    private final Money approvedAmount;

    public Decision(DecisionType type, String notes, String decidedBy, LocalDateTime decidedAt, Money approvedAmount) {
        this.type = type;
        this.notes = notes;
        this.decidedBy = decidedBy;
        this.decidedAt = decidedAt;
        this.approvedAmount = approvedAmount;
    }

    public static Decision approved(String notes, String decidedBy, Money approvedAmount) {
        return new Decision(DecisionType.APPROVED, notes, decidedBy,
                LocalDateTime.now(), approvedAmount);
    }

    public static Decision rejected(String notes, String decidedBy) {
        return new Decision(DecisionType.REJECTED, notes, decidedBy,
                LocalDateTime.now(), null);
    }


    public DecisionType getType() {
        return type;
    }

    public String getNotes() {
        return notes;
    }

    public String getDecidedBy() {
        return decidedBy;
    }

    public LocalDateTime getDecidedAt() {
        return decidedAt;
    }

    public Money getApprovedAmount() {
        return approvedAmount;
    }
}

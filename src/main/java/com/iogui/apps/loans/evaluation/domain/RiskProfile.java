package com.iogui.apps.loans.evaluation.domain;

import com.iogui.apps.loans.evaluation.domain.enums.RiskLevel;

import java.util.List;

public class RiskProfile {
    private final int creditScore;
    private final boolean isEligible;
    private final RiskLevel riskLevel;
    private final List<String> flags;

    public RiskProfile(int creditScore, boolean isEligible, RiskLevel riskLevel, List<String> flags) {
        this.creditScore = creditScore;
        this.isEligible = isEligible;
        this.riskLevel = riskLevel;
        this.flags = flags;
    }

    public boolean hasFlag(String flag) {
        return flags.contains(flag);
    }

    public int getCreditScore() {
        return creditScore;
    }

    public boolean getIsEligible() {
        return isEligible;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public List<String> getFlags() {
        return flags;
    }
}

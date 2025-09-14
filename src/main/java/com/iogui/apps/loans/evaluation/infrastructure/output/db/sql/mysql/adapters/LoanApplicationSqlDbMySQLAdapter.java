package com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.adapters;

import com.iogui.apps.loans.evaluation.application.ports.output.LoanApplicationOutputPort;
import com.iogui.apps.loans.evaluation.domain.Decision;
import com.iogui.apps.loans.evaluation.domain.LoanApplication;
import com.iogui.apps.loans.evaluation.domain.RiskProfile;
import com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.entities.LoanApplicationEntity;
import com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.mappers.loan.LoanMySQLMapper;
import com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.repositories.LoanApplicationRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class LoanApplicationSqlDbMySQLAdapter implements LoanApplicationOutputPort {
    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanMySQLMapper loanMySQLMapper;

    public LoanApplicationSqlDbMySQLAdapter(
            LoanApplicationRepository loanApplicationRepository,
            LoanMySQLMapper loanMySQLMapper) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.loanMySQLMapper = loanMySQLMapper;
    }

    @Override
    public Optional<LoanApplication> getLoanApplicationById(UUID id) {
        Optional<LoanApplicationEntity> loanApplicationDb = loanApplicationRepository.findById(id);

        if (loanApplicationDb.isPresent()) {
            LoanApplication loanApplication = loanMySQLMapper.mapToLoanApplicationDomain(loanApplicationDb.get());
            return Optional.of(loanApplication);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<LoanApplication> updateLoanApplication(LoanApplication loanApplication) {
        Optional<LoanApplicationEntity> loanApplicationDb = loanApplicationRepository.findById(loanApplication.getLoanApplicationId());
        if (loanApplicationDb.isEmpty()) {
            return Optional.empty();
        }
        LoanApplicationEntity loanApplicationDbToUpdate = loanApplicationDb.get();

        // Update basic fields (always safe to update)
        loanApplicationDbToUpdate.setId(loanApplication.getLoanApplicationId());
        loanApplicationDbToUpdate.setCustomerId(loanApplication.getCustomerId());

        if (loanApplication.getRequestedAmount() != null) {
            loanApplicationDbToUpdate.setRequestedAmount(loanApplication.getRequestedAmount().getAmount());
            loanApplicationDbToUpdate.setCurrency(loanApplication.getRequestedAmount().getCurrency().getCurrencyCode());
        }

        if (loanApplication.getPurpose() != null) {
            loanApplicationDbToUpdate.setPurpose(loanApplication.getPurpose());
        }

        if (loanApplication.getType() != null) {
            loanApplicationDbToUpdate.setLoanType(loanApplication.getType().toString());
        }

        if (loanApplication.getStatus() != null) {
            loanApplicationDbToUpdate.setStatus(loanApplication.getStatus().toString());
        }

        if (loanApplication.getSubmittedAt() != null) {
            loanApplicationDbToUpdate.setSubmittedAt(loanApplication.getSubmittedAt());
        }

        loanApplicationDbToUpdate.setDecidedAt(loanApplication.getDecidedAt()); // Can be null

        // Update Decision fields only if Decision exists
        Decision decision = loanApplication.getDecision();
        if (decision != null) {
            if (decision.getType() != null) {
                loanApplicationDbToUpdate.setDecisionType(decision.getType().toString());
            }
            loanApplicationDbToUpdate.setDecisionNotes(decision.getNotes()); // Can be null
            loanApplicationDbToUpdate.setDecidedBy(decision.getDecidedBy()); // Can be null

            if (decision.getApprovedAmount() != null) {
                loanApplicationDbToUpdate.setApprovedAmount(decision.getApprovedAmount().getAmount());
            }
        } else {
            // Clear decision fields if no decision
            loanApplicationDbToUpdate.setDecisionType(null);
            loanApplicationDbToUpdate.setDecisionNotes(null);
            loanApplicationDbToUpdate.setDecidedBy(null);
            loanApplicationDbToUpdate.setApprovedAmount(null);
        }

        // Update RiskProfile fields only if RiskProfile exists
        RiskProfile riskProfile = loanApplication.getRiskProfile();
        if (riskProfile != null) {
            loanApplicationDbToUpdate.setCreditScore(riskProfile.getCreditScore());
            loanApplicationDbToUpdate.setIsEligible(riskProfile.getIsEligible());

            if (riskProfile.getRiskLevel() != null) {
                loanApplicationDbToUpdate.setRiskLevel(riskProfile.getRiskLevel().toString());
            }

            if (riskProfile.getFlags() != null && !riskProfile.getFlags().isEmpty()) {
                loanApplicationDbToUpdate.setRiskFlags(String.join(",", riskProfile.getFlags()));
            } else {
                loanApplicationDbToUpdate.setRiskFlags(null);
            }
        } else {
            // Clear risk profile fields if no risk profile
            loanApplicationDbToUpdate.setCreditScore(null);
            loanApplicationDbToUpdate.setIsEligible(null);
            loanApplicationDbToUpdate.setRiskLevel(null);
            loanApplicationDbToUpdate.setRiskFlags(null);
        }

        LoanApplicationEntity updatedLoanApplicationDb = loanApplicationRepository.save(loanApplicationDbToUpdate);

        return Optional.of(loanMySQLMapper.mapToLoanApplicationDomain(updatedLoanApplicationDb));

    }

    @Override
    public LoanApplication submitLoanApplication(LoanApplication loanApplication) {
        LoanApplicationEntity loanApplicationEntity = loanMySQLMapper.mapToLoanApplicationEntity(loanApplication);
        return loanMySQLMapper.mapToLoanApplicationDomain(loanApplicationRepository.save(loanApplicationEntity));
    }

    @Override
    public List<LoanApplication> getLoanApplicationsByCustomer(UUID customerId) {
        return loanApplicationRepository.findByCustomerId(customerId)
                .stream()
                .map(loanMySQLMapper::mapToLoanApplicationDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<LoanApplication> getLoanApplicationsByStatus(String status) {
        return loanApplicationRepository.findByStatus(status)
                .stream()
                .map(loanMySQLMapper::mapToLoanApplicationDomain)
                .collect(Collectors.toList());
    }
}

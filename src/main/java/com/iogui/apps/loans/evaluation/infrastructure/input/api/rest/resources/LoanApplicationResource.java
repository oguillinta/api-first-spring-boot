package com.iogui.apps.loans.evaluation.infrastructure.input.api.rest.resources;

import com.iogui.apps.loans.evaluation.api.LoanApplicationsApi;
import com.iogui.apps.loans.evaluation.application.ports.input.loan.*;
import com.iogui.apps.loans.evaluation.model.DecideOnLoanApplicationRequest;
import com.iogui.apps.loans.evaluation.model.LoanApplicationRequest;
import com.iogui.apps.loans.evaluation.model.LoanApplicationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class LoanApplicationResource implements LoanApplicationsApi {

    private final GetLoanApplicationsByStatusUseCase getLoanApplicationsByStatusUseCase;
    private final DecideOnLoanApplicationUseCase decideOnLoanApplicationUseCase;
    private final PerformRiskAssessmentUseCase performRiskAssessmentUseCase;
    private final SubmitLoanApplicationUseCase submitLoanApplicationUseCase;

    public LoanApplicationResource(
            GetLoanApplicationsByStatusUseCase getLoanApplicationsByStatusUseCase,
            DecideOnLoanApplicationUseCase decideOnLoanApplicationUseCase,
            PerformRiskAssessmentUseCase performRiskAssessmentUseCase,
            SubmitLoanApplicationUseCase submitLoanApplicationUseCase) {
        this.getLoanApplicationsByStatusUseCase = getLoanApplicationsByStatusUseCase;
        this.decideOnLoanApplicationUseCase = decideOnLoanApplicationUseCase;
        this.performRiskAssessmentUseCase = performRiskAssessmentUseCase;
        this.submitLoanApplicationUseCase = submitLoanApplicationUseCase;
    }

    @PreAuthorize("hasAuthority('LOAN_OFFICER') or hasAuthority('LOAN_MANAGER')")
    public ResponseEntity<List<LoanApplicationResponse>> getLoanApplicationsByStatus(@RequestParam(required = true) String status) {
        List<LoanApplicationResponse> response = getLoanApplicationsByStatusUseCase.getLoanApplicationsByStatus(status);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('LOAN_OFFICER') or hasAuthority('LOAN_MANAGER')")
    public ResponseEntity<LoanApplicationResponse> submitLoanApplication(@RequestBody LoanApplicationRequest request) {
        LoanApplicationResponse response = submitLoanApplicationUseCase.submitLoanApplication(request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('RISK_ANALYST')")
    public ResponseEntity<Void> performRiskAssessment(@PathVariable UUID loanApplicationId) {
        performRiskAssessmentUseCase.performRiskAssessment(loanApplicationId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('LOAN_APPROVER') or hasAuthority('LOAN_MANAGER')")
    public ResponseEntity<Void> decideOnLoanApplication(
            @PathVariable UUID loanApplicationId,
            @RequestBody DecideOnLoanApplicationRequest request) {
        decideOnLoanApplicationUseCase.decideOnLoanApplication(loanApplicationId, request);
        return ResponseEntity.ok().build();
    }
}

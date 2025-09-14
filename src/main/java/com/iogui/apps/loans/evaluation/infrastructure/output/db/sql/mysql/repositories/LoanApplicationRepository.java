package com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.repositories;

import com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.entities.LoanApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LoanApplicationRepository extends JpaRepository<LoanApplicationEntity, UUID> {
    List<LoanApplicationEntity> findByCustomerId(UUID id);
    List<LoanApplicationEntity> findByStatus(String status);
}

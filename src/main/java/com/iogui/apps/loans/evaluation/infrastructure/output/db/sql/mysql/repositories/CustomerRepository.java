package com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.repositories;

import com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {

}

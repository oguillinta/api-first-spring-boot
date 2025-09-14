package com.iogui.apps.loans.evaluation.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID id;
    private final String firstName;
    private final String lastName;
    private final ContactInformation contactInformation;
    private final FinancialProfile financialProfile;
    private final LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public Customer(UUID id, String firstName, String lastName, ContactInformation contactInformation, FinancialProfile financialProfile) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactInformation = contactInformation;
        this.financialProfile = financialProfile;
        this.createdAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public FinancialProfile getFinancialProfile() {
        return financialProfile;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }
}

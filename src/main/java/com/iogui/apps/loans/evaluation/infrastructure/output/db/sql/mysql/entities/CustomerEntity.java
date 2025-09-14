package com.iogui.apps.loans.evaluation.infrastructure.output.db.sql.mysql.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="customers")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name="first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name="last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name ="email", nullable = false, unique = true)
    private String email;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="street", nullable = false)
    private String street;

    @Column(name="city", nullable = false)
    private String city;

    @Column(name="state", nullable = false)
    private String state;

    @Column(name="zip_code", nullable = false)
    private String zipCode;

    @Column(name="country", nullable = false)
    private String country;

    @Column(name="monthly_income", nullable = false, precision = 19, scale = 2)
    private BigDecimal monthlyIncome;

    @Column(name="monthly_expenses", nullable = false, precision = 19, scale = 2)
    private BigDecimal monthlyExpenses;

    @Column(name="existing_debt", nullable = false, precision = 19, scale = 2)
    private BigDecimal existingDebt;

    @Column(name="currency", nullable = false, length = 3)
    private String currency;

    @Column(name="years_of_employment", nullable = false)
    private Integer yearsOfEmployment;

    @Column(name="debt_to_income_ratio", precision = 5, scale = 4)
    private BigDecimal debtToIncomeRatio;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LoanApplicationEntity> loanApplications = new ArrayList<>();

    public CustomerEntity() {}

    public CustomerEntity(
            UUID id,
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            String street,
            String city,
            String state,
            String zipCode,
            String country,
            BigDecimal monthlyIncome,
            BigDecimal monthlyExpenses,
            BigDecimal existingDebt,
            String currency,
            Integer yearsOfEmployment,
            BigDecimal debtToIncomeRatio,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
        this.monthlyIncome = monthlyIncome;
        this.monthlyExpenses = monthlyExpenses;
        this.existingDebt = existingDebt;
        this.currency = currency;
        this.yearsOfEmployment = yearsOfEmployment;
        this.debtToIncomeRatio = debtToIncomeRatio;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public BigDecimal getMonthlyExpenses() {
        return monthlyExpenses;
    }

    public void setMonthlyExpenses(BigDecimal monthlyExpenses) {
        this.monthlyExpenses = monthlyExpenses;
    }

    public BigDecimal getExistingDebt() {
        return existingDebt;
    }

    public void setExistingDebt(BigDecimal existingDebt) {
        this.existingDebt = existingDebt;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getYearsOfEmployment() {
        return yearsOfEmployment;
    }

    public void setYearsOfEmployment(Integer yearsOfEmployment) {
        this.yearsOfEmployment = yearsOfEmployment;
    }

    public BigDecimal getDebtToIncomeRatio() {
        return debtToIncomeRatio;
    }

    public void setDebtToIncomeRatio(BigDecimal debtToIncomeRatio) {
        this.debtToIncomeRatio = debtToIncomeRatio;
    }

    public List<LoanApplicationEntity> getLoanApplications() {
        return loanApplications;
    }

    public void setLoanApplications(List<LoanApplicationEntity> loanApplications) {
        this.loanApplications = loanApplications;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

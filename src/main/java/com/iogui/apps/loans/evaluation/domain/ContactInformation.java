package com.iogui.apps.loans.evaluation.domain;

public class ContactInformation {
    private final String email;
    private final String phoneNumber;
    private final Address address;

    public ContactInformation(String email, String phoneNumber, Address address) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Address getAddress() {
        return address;
    }
}

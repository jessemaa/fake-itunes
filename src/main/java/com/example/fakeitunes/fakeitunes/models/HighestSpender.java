package com.example.fakeitunes.fakeitunes.models;

public class HighestSpender {
    private String firstName;
    private String lastName;
    private String totalSpending;

    public HighestSpender(String firstName, String lastName, String totalSpending) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.totalSpending = totalSpending;
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

    public String getTotalSpending() {
        return totalSpending;
    }

    public void setTotalSpending(String totalSpending) {
        this.totalSpending = totalSpending;
    }
}

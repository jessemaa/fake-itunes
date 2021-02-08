package com.example.fakeitunes.fakeitunes.models;

public class Country {
    private String country;
    private String numberOfCustomers;

    public Country(String country, String numberOfCustomers) {
        this.country = country;
        this.numberOfCustomers = numberOfCustomers;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNumberOfCustomers() {
        return numberOfCustomers;
    }

    public void setNumberOfCustomers(String numberOfCustomers) {
        this.numberOfCustomers = numberOfCustomers;
    }
}
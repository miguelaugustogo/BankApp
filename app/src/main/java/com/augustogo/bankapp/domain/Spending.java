package com.augustogo.bankapp.domain;

public class Spending {

    private String typeTransaction;
    private String description;
    private String date;
    private Double value;

    public Spending(String typeTransaction, String description, String date, Double value) {
        this.typeTransaction = typeTransaction;
        this.description = description;
        this.date = date;
        this.value = value;
    }
    public String getTypeTransaction() {
        return typeTransaction;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public Double getValue() {
        return value;
    }
 }

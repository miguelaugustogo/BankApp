package com.augustogo.bankapp.domain;

import com.augustogo.bankapp.ConstantsApp;
import com.augustogo.bankapp.config.BaseCallback;
import com.augustogo.bankapp.data.repository.DashBoardRepository;

import java.util.List;

public class
Spending {

    private DashBoardRepository repository;
    private String typeTransaction;
    private String description;
    private String date;
    private Double value;

    public Spending() { }

    public Spending(String typeTransaction, String description, String date, Double value) {
        this.typeTransaction = typeTransaction;
        this.description = description;
        this.date = date;
        this.value = value;
    }

    public void setRepository(DashBoardRepository repository) {
        this.repository = repository;
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
    public void listSpending(long idUser, final BaseCallback<List<Spending>> onResult){

        if(repository==null){
            onResult.onUnsuccessful(ConstantsApp.REPOSITORY_NULL);
            return;
        }
        if(idUser<=0){
            onResult.onUnsuccessful(ConstantsApp.ID_INVALID);
            return;
        }
        repository.listStatements(idUser, new BaseCallback<List<Spending>>() {
            @Override
            public void onSuccessful(List<Spending> value) {
                onResult.onSuccessful(value);
            }

            @Override
            public void onUnsuccessful(String error) {
                onResult.onUnsuccessful(error);
            }
        });
    }
}

package com.augustogo.bankapp.domain;

import android.content.Context;

import com.augustogo.bankapp.ConstantsApp;
import com.augustogo.bankapp.config.BaseCallback;

import java.io.Serializable;

public class UserAccount implements Serializable {

    private UserAccountContract.IRepository repository;
    private Long id;
    private String name;
    private String bankAccount;
    private String agency;
    private Double balance;

    private String username;
    private String password;

    public UserAccount() {
    }

    public UserAccount(Long id, String name, String bankAccount, String agency, Double balance) {
        this.id = id;
        this.name = name;
        this.bankAccount = bankAccount;
        this.agency = agency;
        this.balance = balance;
    }

    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setRepository(UserAccountContract.IRepository repository) {
        this.repository = repository;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public String getAgency() {
        return agency;
    }

    public Double getBalance() {
        return balance;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void login(final BaseCallback<UserAccount> onResult){
        if(repository == null){
            onResult.onUnsuccessful(ConstantsApp.REPOSITORY_NULL);
            return;
        }
        if(username.isEmpty()){
            onResult.onUnsuccessful(ConstantsApp.USER_NULL);
            return;
        }

        if(password.isEmpty()){
            onResult.onUnsuccessful(ConstantsApp.PASSWORD_NULL);
            return;
        }
        if(!validUsername()) {
            onResult.onUnsuccessful(ConstantsApp.USER_INVALID);
            return;
        }
        if(!validPassword()){
            onResult.onUnsuccessful(ConstantsApp.PASSWORD_INVALID);
            return;
        }

        repository.login(username, password, new BaseCallback<UserAccount>() {
            @Override
            public void onSuccessful(UserAccount value) {
                onResult.onSuccessful(value);
            }

            @Override
            public void onUnsuccessful(String error) {
                onResult.onUnsuccessful(error);
            }
        });
    }

    private boolean validPassword() {
        return password.matches("((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!&*()_=+{}?;:><,.|']).{4,})");
    }

    private boolean validUsername() {
        if(username.matches(".+@.+\\..+")) return true;
        return username.matches("[0-9]{11}");
    }

    public void loadPreference(Context context,final BaseCallback<UserAccount> onResult) {
        repository.loadPreference(context, new BaseCallback<UserAccount>() {
            @Override
            public void onSuccessful(UserAccount value) {
                onResult.onSuccessful(value);
            }

            @Override
            public void onUnsuccessful(String error) {
                onResult.onUnsuccessful(error);
            }
        });
    }
}

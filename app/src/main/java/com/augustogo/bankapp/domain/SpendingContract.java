package com.augustogo.bankapp.domain;

import com.augustogo.bankapp.config.BaseCallback;

import java.util.List;

public class SpendingContract {
    public interface IRepository{
        void listStatements(long idUser, BaseCallback<List<Spending>> onResult);
    }
}

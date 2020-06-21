package com.augustogo.bankapp.config;

public interface BaseCallback<T> {
    void onSuccessful(T value);
    void onUnsuccessful(String error);
}

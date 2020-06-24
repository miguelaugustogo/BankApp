package com.augustogo.bankapp.data.remote.dto;

import com.augustogo.bankapp.domain.UserAccount;

public class UserAccountDto {
    private UserAccount userAccount;
    private Error error;

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}

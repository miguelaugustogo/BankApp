package com.augustogo.bankapp.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.augustogo.bankapp.ConstantsApp;
import com.augustogo.bankapp.config.BaseCallback;
import com.augustogo.bankapp.config.Repository;
import com.augustogo.bankapp.data.local.LoginSharedPref;
import com.augustogo.bankapp.data.remote.LoginService;
import com.augustogo.bankapp.data.remote.dto.UserAccountDto;
import com.augustogo.bankapp.domain.UserAccount;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository extends Repository {

    private static LoginSharedPref sharedPref;
    UserAccount userAccountMutableLiveData;

    public UserAccount login(final String username, final String password) {
        super.data.restApi(LoginService.class)
                .login(username, password)
                .enqueue(new Callback<UserAccount>() {
                    @Override
                    public void onResponse(@NonNull Call<UserAccount> call, @NonNull  Response<UserAccount> response) {
//                            sharedPref.saveSharedPref(username, password);

                            userAccountMutableLiveData = response.body();
                    }

                    @Override
                    public void onFailure(@NonNull Call<UserAccount> call, @NonNull Throwable t) {
                    }
                });

        return userAccountMutableLiveData;
    }

    public UserAccount loadPreference(Context context) {

        sharedPref = new LoginSharedPref(context);
        UserAccount userAccount= new UserAccount(sharedPref.getUsername(), sharedPref.getPassword());
        userAccountMutableLiveData = userAccount;
        return userAccountMutableLiveData;
    }
}


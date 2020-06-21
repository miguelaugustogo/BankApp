package com.augustogo.bankapp.data.repository;

import android.content.Context;

import com.augustogo.bankapp.ConstantsApp;
import com.augustogo.bankapp.config.BaseCallback;
import com.augustogo.bankapp.config.Repository;
import com.augustogo.bankapp.data.local.LoginSharedPref;
import com.augustogo.bankapp.data.remote.LoginService;
import com.augustogo.bankapp.data.remote.dto.UserAccountDto;
import com.augustogo.bankapp.domain.UserAccount;
import com.augustogo.bankapp.domain.UserAccountContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository extends Repository implements UserAccountContract.IRepository {

    private static LoginSharedPref sharedPref;

    @Override
    public void login(final String username, final String password, final BaseCallback<UserAccount> onResult) {
        super.data.restApi(LoginService.class)
                .login(username, password)
                .enqueue(new Callback<UserAccountDto>() {
                    @Override
                    public void onResponse(Call<UserAccountDto> call, Response<UserAccountDto> response) {
                        if(response.body().getError().getCode()!=0)
                            onResult.onUnsuccessful(response.body().getError().getMessage());
                        else{
                            sharedPref.saveSharedPref(username, password);
                            onResult.onSuccessful(response.body().getUserAccount().toDomain());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserAccountDto> call, Throwable t) {
                        onResult.onUnsuccessful(ConstantsApp.NO_CONNECTION);
                    }
                });
    }

    @Override
    public void loadPreference(Context context, BaseCallback<UserAccount> onResult) {
        sharedPref = new LoginSharedPref(context);
        onResult.onSuccessful(new UserAccount(sharedPref.getUsername(), sharedPref.getPassword()));
    }
}

package com.augustogo.bankapp.data.repository;

import androidx.annotation.NonNull;

import com.augustogo.bankapp.config.App;
import com.augustogo.bankapp.config.BaseCallback;
import com.augustogo.bankapp.config.RetrofitApi;
import com.augustogo.bankapp.data.remote.LoginService;
import com.augustogo.bankapp.data.remote.dto.LoginDto;
import com.augustogo.bankapp.data.remote.dto.UserAccountDto;
import com.augustogo.bankapp.domain.UserAccount;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    private static final LoginRepository ourInstance = new LoginRepository();
    private LoginService service;

    private LoginRepository() {
        service = RetrofitApi.create(LoginService.class);
    }

    public static LoginRepository getInstance() {
        return ourInstance;
    }

    public void login(final String username, final String password, final BaseCallback<UserAccount> baseCallback) {
        service.login(username, password).enqueue(new Callback<UserAccountDto>() {
            @Override
            public void onResponse(Call<UserAccountDto> call, Response<UserAccountDto> response) {
                if (response.body().getError().getCode() != 0) {
                    baseCallback.onUnsuccessful(response.body().getError().getMessage());
                }else {
                    App.getSharedPref().saveSharedPref(username, password);
                    LoginDto accountDto = response.body().getUserAccount();
                    UserAccount userAccount = new UserAccount(accountDto.getUserId(), accountDto.getName(), accountDto.getBankAccount(), accountDto.getAgency(), accountDto.getBalance());
                    baseCallback.onSuccessful(userAccount);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserAccountDto> call, @NonNull Throwable t) {
                baseCallback.onUnsuccessful(t.getMessage());
            }
        });
    }
}


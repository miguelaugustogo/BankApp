package com.augustogo.bankapp.ui.login;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.augustogo.bankapp.ConstantsApp;
import com.augustogo.bankapp.config.BaseCallback;
import com.augustogo.bankapp.domain.UserAccount;
import com.augustogo.bankapp.data.repository.LoginRepository;

public class LoginViewModel extends ViewModel {
    LoginRepository loginRepository;


    public LoginViewModel(){
        loginRepository= new LoginRepository();
    }
    public void getLogin(String username, String password, final BaseCallback<UserAccount> onResult){
        if(username.isEmpty()){
            onResult.onUnsuccessful(ConstantsApp.USER_NULL);
            return;
        }

        if(password.isEmpty()){
            onResult.onUnsuccessful(ConstantsApp.PASSWORD_NULL);
            return;
        }
        if(!validUsername(username)) {
            onResult.onUnsuccessful(ConstantsApp.USER_INVALID);
            return;
        }
        if(!validPassword(password)){
            onResult.onUnsuccessful(ConstantsApp.PASSWORD_INVALID);
            return;
        }

        loginRepository.login(username, password, new BaseCallback<UserAccount>() {
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

    private boolean validPassword(String password) {
        return password.matches("((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!&*()_=+{}?;:><,.|']).{4,})");
    }

    private boolean validUsername(String username) {
        if(username.matches(".+@.+\\..+")) return true;
        return username.matches("[0-9]{11}");
    }

    public void loadPreference(Context context, final BaseCallback<UserAccount> onResult) {
        loginRepository.loadPreference(context, new BaseCallback<UserAccount>() {
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

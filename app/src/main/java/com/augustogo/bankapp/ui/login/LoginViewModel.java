package com.augustogo.bankapp.ui.login;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.augustogo.bankapp.config.BaseCallback;
import com.augustogo.bankapp.data.repository.LoginRepository;
import com.augustogo.bankapp.domain.UserAccount;

public class LoginViewModel extends ViewModel {
    LoginRepository loginRepository;

    private MutableLiveData<UserAccount> userAccount = new MutableLiveData<>();

    public LoginViewModel() {
        super();
        loginRepository = LoginRepository.getInstance();
    }

    public void login(String username, String password) {

        if (!username.isEmpty() && !password.isEmpty()) {
            if (validUsername(username) && validPassword(password)) {

                loginRepository.login(username, password, new BaseCallback<UserAccount>() {
                    @Override
                    public void onSuccessful(UserAccount value) {
                        userAccount.postValue(value);
                        Log.i("LoginViewModel", "USER_ACCOUNT");
                    }

                    @Override
                    public void onUnsuccessful(String error) {
                    }
                });
            }
        }
    }

    public LiveData<UserAccount> userAccountLiveData() {
        return userAccount;
    }

    private boolean validPassword(String password) {
        return password.matches("((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!&*()_=+{}?;:><,.|']).{4,})");
    }

    private boolean validUsername(String username) {
        if (username.matches(".+@.+\\..+")) return true;
        return username.matches("[0-9]{11}");
    }
}

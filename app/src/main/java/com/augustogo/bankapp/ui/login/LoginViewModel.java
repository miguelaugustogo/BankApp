package com.augustogo.bankapp.ui.login;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.augustogo.bankapp.domain.UserAccount;
import com.augustogo.bankapp.data.repository.LoginRepository;


public class LoginViewModel extends ViewModel {
    LoginRepository loginRepository;

    private MutableLiveData<Boolean> isValidParametersLogin = new MutableLiveData<>();
    private MutableLiveData<UserAccount> userAccount = new MutableLiveData<>();

    public LoginViewModel(){
        loginRepository= new LoginRepository();
    }


    public void login(String username, String password){

        if (!username.isEmpty() && !password.isEmpty()){
            if (validUsername(username) && validPassword(password)){
                isValidParametersLogin.setValue(true);
                userAccount.postValue(loginRepository.login(username, password));
            }
        }
//        return userAccountMutableLiveData;
    }

    public LiveData<UserAccount> userAccountLiveData(){
        return userAccount;
    }

    private boolean validPassword(String password) {
        return password.matches("((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!&*()_=+{}?;:><,.|']).{4,})");
    }

    private boolean validUsername(String username) {
        if(username.matches(".+@.+\\..+")) return true;
        return username.matches("[0-9]{11}");
    }

    public void loadPreference(Context context) {
        userAccount.setValue(loginRepository.loadPreference(context));
    }
}

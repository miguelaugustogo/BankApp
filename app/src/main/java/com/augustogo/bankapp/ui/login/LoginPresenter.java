package com.augustogo.bankapp.ui.login;

import android.util.Log;

import com.augustogo.bankapp.config.BaseCallback;
import com.augustogo.bankapp.data.repository.LoginRepository;
import com.augustogo.bankapp.domain.UserAccount;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void login(String username, String password) {
        view.showProgress(true);


        UserAccount userAccount = new UserAccount(username, password);
        userAccount.setRepository(new LoginRepository());
        userAccount.login(new BaseCallback<UserAccount>() {
            @Override
            public void onSuccessful(UserAccount value) {
                view.showProgress(false);
                view.navigationToHome(value);
            }

            @Override
            public void onUnsuccessful(String error) {
                view.showProgress(false);
                view.showError(error);
            }
        });
    }

    @Override
    public void loadPreference() {
        UserAccount userAccount = new UserAccount();
        userAccount.setRepository(new LoginRepository());
        userAccount.loadPreference(view.getContext(), new BaseCallback<UserAccount>() {
            @Override
            public void onSuccessful(UserAccount value) {
                view.setPreferences(value);
            }

            @Override
            public void onUnsuccessful(String error) {
                Log.e("SHARED_PREFERENCES ", error);
            }
        });
    }
}

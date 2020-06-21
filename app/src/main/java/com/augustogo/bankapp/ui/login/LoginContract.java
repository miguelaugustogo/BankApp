package com.augustogo.bankapp.ui.login;

import android.content.Context;
import com.augustogo.bankapp.domain.UserAccount;

public class LoginContract {

    public interface View{

        void navigationToHome(UserAccount userAccount);

        void showError(String error);

        void showProgress(boolean show);

        void setPreferences(UserAccount value);

        Context getContext();
    }
    interface Presenter{

        void login(String username, String password);

        void loadPreference();
    }
}

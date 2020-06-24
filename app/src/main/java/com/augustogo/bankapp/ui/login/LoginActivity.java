package com.augustogo.bankapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.augustogo.bankapp.ConstantsApp;
import com.augustogo.bankapp.R;
import com.augustogo.bankapp.config.BaseCallback;
import com.augustogo.bankapp.data.local.LoginSharedPref;
import com.augustogo.bankapp.domain.UserAccount;
import com.augustogo.bankapp.ui.DialogApp;
import com.augustogo.bankapp.ui.dashboard.DashBoardActivity;

public class LoginActivity extends AppCompatActivity {

    public static final String USER_ACCOUNT = "userAccount";
    private EditText editTextUsername;
    private EditText editTextPassword;
    Button buttonLogin;
    private ProgressBar progressLogin;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.v("ACTIVITY 0","");
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loadUi();
        loadObserve();
        loadActions();
    }

    private void loadObserve() {

//        loginViewModel.loadPreference(this).observe(this, (UserAccount userAccount) -> {
//            if (userAccount != null) setPreferences(userAccount);
//            else showError("NOT SHARED PREFERENCES");
//        });

        loginViewModel.userAccountLiveData().observe(this, (UserAccount userAccount) -> {
            if (userAccount != null) navigationToHome(userAccount);
            else showError("NOT LOGIN");
        });
    }

    private void loadActions() {

        editTextPassword.setOnEditorActionListener((textView, i, keyEvent) -> {

            if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_NULL) {
                loginViewModel.login(editTextUsername.getText().toString().trim(),
                        editTextPassword.getText().toString().trim());
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(editTextPassword.getWindowToken(), 0);
                return true;
            }
            return false;
        });

        buttonLogin.setOnClickListener(view -> {
            loginViewModel.login(editTextUsername.getText().toString().trim(),
                    editTextPassword.getText().toString().trim());
        });
    }

    private void loadUi() {
        editTextUsername = findViewById(R.id.edit_text_username);
        editTextPassword = findViewById(R.id.edit_text_password);
        buttonLogin = findViewById(R.id.button_login);
        progressLogin = findViewById(R.id.progress_login);
    }


    public void navigationToHome(UserAccount userAccount) {
        Log.e("ACTIVITY2","NAME "+ userAccount.getName());
        if (!userAccount.getName().isEmpty()) {

            Log.d("ACTIVITY3","NAME "+ userAccount.getName());
            Intent intent = new Intent(this, DashBoardActivity.class);
            intent.putExtra(USER_ACCOUNT, userAccount);
            startActivity(intent);
            finish();
        }
    }

    public void showError(String error) {
        if (error.equals(ConstantsApp.NO_CONNECTION))
            DialogApp.showDialogConnection(this);
        else
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }


    public void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        buttonLogin.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
        buttonLogin.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                buttonLogin.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
            }
        });

        progressLogin.setVisibility(show ? View.VISIBLE : View.GONE);
        progressLogin.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressLogin.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }


    public void setPreferences(UserAccount value) {
        editTextUsername.setText(value.getUsername());
        editTextPassword.setText(value.getPassword());
    }


    public Context getContext() {
        return this;
    }
}
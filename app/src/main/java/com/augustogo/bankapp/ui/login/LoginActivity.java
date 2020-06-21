package com.augustogo.bankapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.augustogo.bankapp.domain.UserAccount;
import com.augustogo.bankapp.ui.DialogApp;
import com.augustogo.bankapp.ui.dashboard.DashBoardActivity;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    public static final String USER_ACCOUNT = "userAccount";
    private LoginContract.Presenter presenter;
    private EditText editTextUsername;
    private EditText editTextPassword;
    Button buttonLogin;
    private ProgressBar progressLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this);
        loadUi();
        loadActions();
        presenter.loadPreference();
    }

    private void loadActions() {

        editTextPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_NULL) {
                presenter.login(editTextUsername.getText().toString().trim(),
                        editTextPassword.getText().toString().trim());
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(editTextPassword.getWindowToken(), 0);
                return true;
            }
                return false;
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.login(editTextUsername.getText().toString().trim(),
                        editTextPassword.getText().toString().trim());
            }
        });

    }

    private void loadUi() {
        editTextUsername = findViewById(R.id.edit_text_username);
        editTextPassword = findViewById(R.id.edit_text_password);
        buttonLogin = findViewById(R.id.button_login);
        progressLogin = findViewById(R.id.progress_login);

    }

    @Override
    public void navigationToHome(UserAccount userAccount) {
        Intent intent = new Intent(this, DashBoardActivity.class);
        intent.putExtra(USER_ACCOUNT, userAccount);
        startActivity(intent);
        finish();
    }

    @Override
    public void showError(String error) {
        if(error.equals(ConstantsApp.NO_CONNECTION))
            DialogApp.showDialogConnection(this);
        else
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress(final boolean show) {
        int shortAnimTime = getResources ().getInteger (android.R.integer.config_shortAnimTime);

        buttonLogin.setVisibility (show ? View.INVISIBLE : View.VISIBLE);
        buttonLogin.animate ().setDuration (shortAnimTime).alpha (
                show ? 0 : 1).setListener (new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                buttonLogin.setVisibility (show ? View.INVISIBLE : View.VISIBLE);
            }
        });

        progressLogin.setVisibility (show ? View.VISIBLE : View.GONE);
        progressLogin.animate ().setDuration (shortAnimTime).alpha (
                show ? 1 : 0).setListener (new AnimatorListenerAdapter () {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressLogin.setVisibility (show ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void setPreferences(UserAccount value) {
        editTextUsername.setText(value.getUsername());
        editTextPassword.setText(value.getPassword());
    }

    @Override
    public Context getContext() {
        return this;
    }
}
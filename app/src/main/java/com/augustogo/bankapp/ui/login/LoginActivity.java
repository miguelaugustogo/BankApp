package com.augustogo.bankapp.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.augustogo.bankapp.ConstantsApp;
import com.augustogo.bankapp.R;
import com.augustogo.bankapp.config.App;
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

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loadUi();
        loadObserve();
        loadActions();
    }

    private void loadObserve() {

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
            buttonLogin.setEnabled(false);
        });
    }

    private void loadUi() {
        editTextUsername = findViewById(R.id.edit_text_username);
        editTextPassword = findViewById(R.id.edit_text_password);

        if (!App.getSharedPref().getUsername().isEmpty()){
            editTextUsername.setText(App.getSharedPref().getUsername());
        }
        if (!App.getSharedPref().getPassword().isEmpty()){
            editTextPassword.setText(App.getSharedPref().getPassword());
        }

        buttonLogin = findViewById(R.id.button_login);
        progressLogin = findViewById(R.id.progress_login);
    }


    public void navigationToHome(UserAccount userAccount) {

        if (!userAccount.getName().isEmpty()) {
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
}
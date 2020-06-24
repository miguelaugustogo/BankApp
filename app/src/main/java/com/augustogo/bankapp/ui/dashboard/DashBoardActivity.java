package com.augustogo.bankapp.ui.dashboard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.augustogo.bankapp.ConstantsApp;
import com.augustogo.bankapp.R;
import com.augustogo.bankapp.config.BaseCallback;
import com.augustogo.bankapp.domain.Spending;
import com.augustogo.bankapp.domain.UserAccount;
import com.augustogo.bankapp.ui.DialogApp;
import com.augustogo.bankapp.ui.dashboard.adapter.AdapterCardDashBoard;
import com.augustogo.bankapp.ui.login.LoginActivity;
import com.augustogo.bankapp.util.CoinUtil;

import java.util.List;

public class DashBoardActivity extends AppCompatActivity {

    private TextView textViewNameCustomer;
    private TextView textViewAccount;
    private TextView textViewBalance;
    private ImageView imageViewLogout;
    private RecyclerView recyclerViewDashBoard;
    private UserAccount userAccount;
    private ProgressBar progressBarDashBoard;
    private DashBoardViewModel dashBoardViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Log.e("DASHBOARD","");

        dashBoardViewModel = new ViewModelProvider(this).get(DashBoardViewModel.class);
        loadUi();
        loadExtras();
        loadActions();
//        dashBoardViewModel.listSpending(userAccount.getId(), new BaseCallback<List<Spending>>() {
//            @Override
//            public void onSuccessful(List<Spending> value) {
//                showProgress(false);
//                listSpent(value);
//            }
//
//            @Override
//            public void onUnsuccessful(String error) {
//                showProgress(false);
//                showError(error);
//            }
//        });

    }

    private void loadActions() {
        imageViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    private void loadUi() {
        textViewNameCustomer = findViewById(R.id.textview_name_customer);
        textViewAccount = findViewById(R.id.textview_account);
        textViewBalance = findViewById(R.id.textview_balance);
        imageViewLogout = findViewById(R.id.imageview_logout);
        recyclerViewDashBoard = findViewById(R.id.recyclerview_dash_board);
        progressBarDashBoard = findViewById(R.id.progressbar_dash_board);
    }

    private void loadExtras() {
        if (getIntent().hasExtra(LoginActivity.USER_ACCOUNT)) {
            userAccount = (UserAccount) getIntent()
                    .getSerializableExtra(LoginActivity.USER_ACCOUNT);
                textViewNameCustomer.setText(userAccount.getName());
                textViewAccount.setText(String.format("%s / %s",
                        userAccount.getBankAccount(),
                        userAccount.getAgency().replaceAll(
                                "([0-9]{2})([0-9]{6})([0-9])", "$1.$2-$3")));
                textViewBalance.setText(CoinUtil.formatReal(userAccount.getBalance()));
        }
    }


    public void listSpent(List<Spending> value) {
        recyclerViewDashBoard.setAdapter(new AdapterCardDashBoard(this, value));
    }


    public void showError(String error) {
        if (error.equals(ConstantsApp.NO_CONNECTION))
            DialogApp.showDialogConnection(this);
        else
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }


    public void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        recyclerViewDashBoard.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
        recyclerViewDashBoard.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                recyclerViewDashBoard.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
            }
        });

        progressBarDashBoard.setVisibility(show ? View.VISIBLE : View.GONE);
        progressBarDashBoard.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressBarDashBoard.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        logout();
    }

    public void logout(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

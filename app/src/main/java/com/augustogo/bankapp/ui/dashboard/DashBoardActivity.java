package com.augustogo.bankapp.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.augustogo.bankapp.R;
import com.augustogo.bankapp.domain.Spending;
import com.augustogo.bankapp.domain.UserAccount;
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

        dashBoardViewModel = new ViewModelProvider(this).get(DashBoardViewModel.class);
        loadUi();
        loadExtras();
        loadObserve();
        loadActions();
    }

    private void loadObserve() {
        dashBoardViewModel.listSpendingLiveData().observe(this, (List<Spending> listSpending) -> {
            initRecyclerView(listSpending);
        });
    }

    private void loadActions() {
        imageViewLogout.setOnClickListener(view -> logout());
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
                dashBoardViewModel.listSpending(userAccount.getId());
        }
    }

    public void initRecyclerView(List<Spending> value) {
        AdapterCardDashBoard adapter = new AdapterCardDashBoard(this, value);
        recyclerViewDashBoard.setAdapter(adapter);
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

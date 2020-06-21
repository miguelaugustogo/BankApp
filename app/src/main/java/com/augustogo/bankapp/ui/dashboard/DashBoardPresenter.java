package com.augustogo.bankapp.ui.dashboard;

import com.augustogo.bankapp.config.BaseCallback;
import com.augustogo.bankapp.data.repository.StatementsRepository;
import com.augustogo.bankapp.domain.Spending;

import java.util.List;

public class DashBoardPresenter implements DashBoardContract.Presenter {

    private DashBoardContract.View view;

    public DashBoardPresenter(DashBoardContract.View view) {
        this.view = view;
    }

    @Override
    public void loadList(Long idUser) {
        view.showProgress(true);
        Spending spending = new Spending();
        spending.setRepository(new StatementsRepository());
        spending.listSpending(idUser, new BaseCallback<List<Spending>>() {
            @Override
            public void onSuccessful(List<Spending> value) {
                view.showProgress(false);
                view.listSpent(value);
            }

            @Override
            public void onUnsuccessful(String error) {
                view.showProgress(false);
                view.showError(error);
            }
        });
    }
}

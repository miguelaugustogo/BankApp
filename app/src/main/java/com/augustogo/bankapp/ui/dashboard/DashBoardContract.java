package com.augustogo.bankapp.ui.dashboard;

import com.augustogo.bankapp.domain.Spending;

import java.util.List;

public class DashBoardContract {

    interface View{

        void listSpent(List<Spending> value);

        void showError(String error);

        void showProgress(boolean show);
    }
    interface Presenter{

        void loadList(Long idUser);
    }
}

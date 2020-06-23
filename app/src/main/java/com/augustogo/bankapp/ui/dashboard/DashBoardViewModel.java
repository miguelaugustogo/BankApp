package com.augustogo.bankapp.ui.dashboard;

import androidx.lifecycle.ViewModel;

import com.augustogo.bankapp.ConstantsApp;
import com.augustogo.bankapp.config.BaseCallback;
import com.augustogo.bankapp.domain.Spending;
import com.augustogo.bankapp.data.repository.DashBoardRepository;

import java.util.List;

public class DashBoardViewModel extends ViewModel {

    DashBoardRepository dashBoardRepository;

    public DashBoardViewModel(){
        dashBoardRepository= new DashBoardRepository();
    }

    public void listSpending(long idUser, final BaseCallback<List<Spending>> onResult){

        if(dashBoardRepository==null){
            onResult.onUnsuccessful(ConstantsApp.REPOSITORY_NULL);
            return;
        }
        if(idUser<=0){
            onResult.onUnsuccessful(ConstantsApp.ID_INVALID);
            return;
        }

        dashBoardRepository.listStatements(idUser, new BaseCallback<List<Spending>>() {
            @Override
            public void onSuccessful(List<Spending> value) {
                onResult.onSuccessful(value);
            }

            @Override
            public void onUnsuccessful(String error) {
                onResult.onUnsuccessful(error);
            }
        });
    }
}

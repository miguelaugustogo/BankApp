package com.augustogo.bankapp.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.augustogo.bankapp.config.BaseCallback;
import com.augustogo.bankapp.data.repository.DashBoardRepository;
import com.augustogo.bankapp.domain.Spending;

import java.util.List;

public class DashBoardViewModel extends ViewModel {

    DashBoardRepository dashBoardRepository;
    private MutableLiveData<List<Spending>> listSpending = new MutableLiveData<>();

    public DashBoardViewModel(){
        super();
        dashBoardRepository= DashBoardRepository.getInstance();
    }

    public void listSpending(long idUser){

        dashBoardRepository.listStatements(idUser, new BaseCallback<List<Spending>>() {
            @Override
            public void onSuccessful(List<Spending> value) {
                listSpending.postValue(value);
            }

            @Override
            public void onUnsuccessful(String error) {
            }
        });
    }

    public LiveData<List<Spending>> listSpendingLiveData (){
        return listSpending;
    }
}

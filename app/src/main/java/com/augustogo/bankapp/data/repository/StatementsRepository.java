package com.augustogo.bankapp.data.repository;

import com.augustogo.bankapp.ConstantsApp;
import com.augustogo.bankapp.config.BaseCallback;
import com.augustogo.bankapp.config.Repository;
import com.augustogo.bankapp.data.remote.StatementsService;
import com.augustogo.bankapp.data.remote.dto.SpendingDto;
import com.augustogo.bankapp.domain.Spending;
import com.augustogo.bankapp.domain.SpendingContract;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatementsRepository extends Repository implements SpendingContract.IRepository {

    @Override
    public void listStatements(long idUser,final BaseCallback<List<Spending>> onResult) {
        super.data.restApi(StatementsService.class).spendingList(idUser)
                .enqueue(new Callback<SpendingDto>() {
                    @Override
                    public void onResponse(Call<SpendingDto> call, Response<SpendingDto> response) {
                        if(response.isSuccessful() && response.body().getError().getCode()==0) {
                            if (response.body().getStatementList().size() < 1){
                                onResult.onUnsuccessful(ConstantsApp.NO_RECORDS_FOUND);
                                return;
                            }
                            onResult.onSuccessful(response.body().toDomain());
                        } else
                            onResult.onUnsuccessful(response.body().getError().getMessage());

                    }

                    @Override
                    public void onFailure(Call<SpendingDto> call, Throwable t) {
                        onResult.onUnsuccessful(ConstantsApp.NO_CONNECTION);
                    }
                });
    }
}

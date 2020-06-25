package com.augustogo.bankapp.data.repository;

import com.augustogo.bankapp.ConstantsApp;
import com.augustogo.bankapp.config.BaseCallback;
import com.augustogo.bankapp.config.RetrofitApi;
import com.augustogo.bankapp.data.remote.StatementsService;
import com.augustogo.bankapp.data.remote.dto.SpendingDto;
import com.augustogo.bankapp.domain.Spending;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardRepository {

    private static final DashBoardRepository ourInstance = new DashBoardRepository();
    private StatementsService service;

    private DashBoardRepository() {
        service = RetrofitApi.create(StatementsService.class);
    }

    public static DashBoardRepository getInstance() {
        return ourInstance;
    }

    public void listStatements(long idUser, final BaseCallback<List<Spending>> baseCallback) {
        service.spendingList(idUser).enqueue(new Callback<SpendingDto>() {
            @Override
            public void onResponse(Call<SpendingDto> call, Response<SpendingDto> response) {
                if (response.isSuccessful() && response.body().getError().getCode() == 0) {
                    if (response.body().getStatementList().size() < 1) {
                        baseCallback.onUnsuccessful(ConstantsApp.NO_RECORDS_FOUND);
                        return;
                    }
                    baseCallback.onSuccessful(response.body().toDomain());
                } else
                    baseCallback.onUnsuccessful(response.body().getError().getMessage());
            }

            @Override
            public void onFailure(Call<SpendingDto> call, Throwable t) {
                baseCallback.onUnsuccessful(ConstantsApp.NO_CONNECTION);
            }
        });
    }
}

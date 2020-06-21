package com.augustogo.bankapp.data.remote;

import com.augustogo.bankapp.data.remote.dto.SpendingDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StatementsService {

    @GET("statements/{idUser}")
    Call<SpendingDto> spendingList(@Path("idUser") long id);
}

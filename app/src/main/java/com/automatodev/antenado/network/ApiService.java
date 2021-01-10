package com.automatodev.antenado.network;

import com.automatodev.antenado.responses.TvDataSheet;
import com.automatodev.antenado.responses.TvDetailsDataShet;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("most-popular")
    Call<TvDataSheet> getMostPopular(@Query("page") int page);

    @GET("show-details")
    Call<TvDetailsDataShet> getDetailsTvShow(@Query("q") int q);

}

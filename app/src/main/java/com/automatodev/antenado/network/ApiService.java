package com.automatodev.antenado.network;

import com.automatodev.antenado.models.TvDetails;
import com.automatodev.antenado.models.TvMostPopular;
import com.automatodev.antenado.responses.TvDataSheet;
import com.automatodev.antenado.responses.TvDetailsDataSheet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("most-popular")
    Call<TvDataSheet> getMostPopular(@Query("page") int page);

    @GET("show-details")
    Call<TvDetailsDataSheet> getDetailsTvShow(@Query("q") String q);

    @GET("search")
    Call<TvDataSheet> searchTvShow(@Query("q") String q, @Query("page") int page);

}

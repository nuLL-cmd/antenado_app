package com.automatodev.antenado.services;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.automatodev.antenado.models.TvMostPopular;
import com.automatodev.antenado.network.ApiClient;
import com.automatodev.antenado.network.ApiService;
import com.automatodev.antenado.responses.TvDataSheet;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvMostPopularService {

    private ApiService apiService;
   // private View view;
    public TvMostPopularService(){
        //this.view = view;
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TvDataSheet> getAllTvMostPopular(int page){
        MutableLiveData<TvDataSheet> data = new MutableLiveData<>();
        apiService.getMostPopular(page).enqueue(new Callback<TvDataSheet>() {
            @Override
            public void onResponse(Call<TvDataSheet> call, Response<TvDataSheet> response) {
                if(response.isSuccessful())
                    data.setValue(response.body());
                else{
                    try {
                        JSONObject error = new JSONObject(response.errorBody().string());
                       // Snackbar.make(view, "Erro ao recuperar os dados solicitados! \nTente novamente mais tarde", Snackbar.LENGTH_LONG).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                        Log.e("logx","Error getAllTvMostPopular: "+e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<TvDataSheet> call, Throwable t) {
                t.printStackTrace();
                Log.e("logx","Error getAllTvMostPopular: "+t.getMessage());
            }
        });
        return data;
    }
}

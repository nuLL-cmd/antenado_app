package com.automatodev.antenado.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.automatodev.antenado.network.ApiClient;
import com.automatodev.antenado.network.ApiService;
import com.automatodev.antenado.responses.TvDataSheet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvMostService {

    private ApiService apiService;


    public TvMostService(){
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
                    //Toast.makeText(context,"Houve um problema ao trazer os dados /n codigo: "+response.code(), Toast.LENGTH_LONG).show();
                    try {
                        JSONObject error = new JSONObject(response.errorBody().string());
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

package com.automatodev.antenado.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.automatodev.antenado.network.ApiClient;
import com.automatodev.antenado.network.ApiService;
import com.automatodev.antenado.models.TvDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvDetailsService {

    private ApiService apiService;

    public TvDetailsService( ){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TvDetails> getDetailsTvShow(String id){
        MutableLiveData<TvDetails> data = new MutableLiveData<>();
        apiService.getDetailsTvShow(id).enqueue(new Callback<TvDetails>() {
            @Override
            public void onResponse(Call<TvDetails> call, Response<TvDetails> response) {
                if (response.isSuccessful()){
                    data.setValue(response.body());
                }else{
                    try{
                        assert response.errorBody() != null;
                        JSONObject error = new JSONObject(response.errorBody().string());
                    }catch (JSONException | IOException e){
                        Log.e("logx", "ResponseGetDetailsTvShow: "+e.getMessage());

                    }
                }
            }

            @Override
            public void onFailure(Call<TvDetails> call, Throwable t) {
                Log.e("logx", "ResponseGetDetailsTvShow: "+t.getMessage());

            }
        });

        return data;
    }
}

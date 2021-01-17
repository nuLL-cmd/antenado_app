package com.automatodev.antenado.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.automatodev.antenado.network.ApiClient;
import com.automatodev.antenado.network.ApiService;
import com.automatodev.antenado.models.TvDetails;
import com.automatodev.antenado.responses.TvDetailsDataSheet;

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

    public LiveData<TvDetailsDataSheet> getDetailsTvShow(String id){
        MutableLiveData<TvDetailsDataSheet> data = new MutableLiveData<>();
        apiService.getDetailsTvShow(id).enqueue(new Callback<TvDetailsDataSheet>() {
            @Override
            public void onResponse(Call<TvDetailsDataSheet> call, Response<TvDetailsDataSheet> response) {
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
            public void onFailure(Call<TvDetailsDataSheet> call, Throwable t) {
                Log.e("logx", "ResponseGetDetailsTvShow: "+t.getMessage());

            }
        });

        return data;
    }
}

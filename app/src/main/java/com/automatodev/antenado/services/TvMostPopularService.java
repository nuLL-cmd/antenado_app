package com.automatodev.antenado.services;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.automatodev.antenado.models.TvMostPopular;
import com.automatodev.antenado.network.ApiClient;
import com.automatodev.antenado.network.ApiService;
import com.automatodev.antenado.responses.TvDataSheet;
import com.automatodev.antenado.responses.TvDetailsDataShet;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvMostPopularService {

    private ApiService apiService;
    private Context context;
   // private View view;
    public TvMostPopularService(Context context){
        //this.view = view;
        apiService = ApiClient.getRetrofit().create(ApiService.class);
        this.context = context;
    }

    public LiveData<TvDetailsDataShet> getDetailsTvShow(int id){
        MutableLiveData<TvDetailsDataShet> liveData = new MutableLiveData<>();
        apiService.getDetailsTvShow(id).enqueue(new Callback<TvDetailsDataShet>() {
            @Override
            public void onResponse(Call<TvDetailsDataShet> call, Response<TvDetailsDataShet> response) {
                if (response.isSuccessful()){
                    liveData.setValue(response.body());
                }else {
                    try {
                        JSONObject error = new JSONObject(response.errorBody().string());
                    }catch (IOException | JSONException e) {
                        Log.e("logx",  "TryError getDetailsTvData");
                        Toast.makeText(context, "Erro ao trazer os dados: "+response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<TvDetailsDataShet> call, Throwable t) {
                Toast.makeText(context, "Erro ao trazer os dados: "+t.getCause(), Toast.LENGTH_SHORT).show();
                Log.e("logx","ErrorgetDetailsTvData: "+t.getMessage());
            }
        });

        return liveData;
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

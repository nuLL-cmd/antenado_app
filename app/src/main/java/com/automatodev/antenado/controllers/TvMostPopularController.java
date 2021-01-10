package com.automatodev.antenado.controllers;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.automatodev.antenado.responses.TvDataSheet;
import com.automatodev.antenado.responses.TvDetailsDataShet;
import com.automatodev.antenado.services.TvMostPopularService;

public class TvMostPopularController extends ViewModel {

    private TvMostPopularService tmpService;
    private Context context;

    public TvMostPopularController(Context context){
        this.context = context;
        tmpService  = new TvMostPopularService(this.context);
    }

    public LiveData<TvDataSheet> getAllTvMostPopular(int page){
        return tmpService.getAllTvMostPopular(page);
    }

    public LiveData<TvDetailsDataShet> getDetailsTvShow(int id){
        return tmpService.getDetailsTvShow(id);
    }

}

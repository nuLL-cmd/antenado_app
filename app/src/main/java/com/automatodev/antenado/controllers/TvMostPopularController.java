package com.automatodev.antenado.controllers;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.automatodev.antenado.responses.TvDataSheet;
import com.automatodev.antenado.services.TvMostPopularService;

public class TvMostPopularController extends ViewModel {

    private TvMostPopularService tmpService;

    public TvMostPopularController(View view){
        tmpService  = new TvMostPopularService(view);
    }

    public LiveData<TvDataSheet> getAllTvMostPopular(int page){
        return tmpService.getAllTvMostPopular(page);
    }
}

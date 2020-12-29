package com.automatodev.antenado.controllers;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.automatodev.antenado.responses.TvDataSheet;
import com.automatodev.antenado.services.TvMostPopularService;

public class TvMostPopularController extends ViewModel {

    private TvMostPopularService tmpService;


    public TvMostPopularController(){
        tmpService  = new TvMostPopularService();
    }

    public LiveData<TvDataSheet> getAllTvMostPopular(int page){
        return tmpService.getAllTvMostPopular(page);
    }


}

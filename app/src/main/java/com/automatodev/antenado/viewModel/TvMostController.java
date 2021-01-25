package com.automatodev.antenado.viewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.automatodev.antenado.responses.TvDataSheet;
import com.automatodev.antenado.repository.TvMostService;

public class TvMostController extends ViewModel {

    private TvMostService tmpService;

    public TvMostController(){
        tmpService  = new TvMostService();
    }


    public LiveData<TvDataSheet> getAllTvMostPopular(int page){
        return tmpService.getAllTvMostPopular(page);
    }

    public LiveData<TvDataSheet> searchTvShow(String query, int page){
        return tmpService.searchTvShow(query, page);
    }
}

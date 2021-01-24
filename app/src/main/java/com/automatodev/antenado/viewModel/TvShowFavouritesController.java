package com.automatodev.antenado.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.automatodev.antenado.database.TvShowDatabase;
import com.automatodev.antenado.models.TvMostPopular;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class TvShowFavouritesController extends AndroidViewModel {

    private TvShowDatabase tvShowDatabase;
    public TvShowFavouritesController (@NonNull Application application){
        super(application);
        tvShowDatabase = TvShowDatabase.getTvShowDatabase(application);
    }

    public Completable addFavourites(TvMostPopular tvMostPopular){
        return tvShowDatabase.tvShowDao().addFavourites(tvMostPopular);
    }

    public Flowable<List<TvMostPopular>> getFavourites(){
        return tvShowDatabase.tvShowDao().getFavourites();
    }

    public Completable deleteFavourite(TvMostPopular tvMostPopular){
        return tvShowDatabase.tvShowDao().removeFovourite(tvMostPopular);
    }

    public Flowable<TvMostPopular> getFavouriteSingle(int id){
        return tvShowDatabase.tvShowDao().getFavouriteSingle(id);
    }
}

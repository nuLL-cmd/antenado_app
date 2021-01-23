package com.automatodev.antenado.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.automatodev.antenado.adapters.TvMostPopularAdapter;
import com.automatodev.antenado.databinding.ActivityFavouritesBinding;
import com.automatodev.antenado.listener.TvDataListener;
import com.automatodev.antenado.models.TvMostPopular;
import com.automatodev.antenado.viewModel.TvShowFavouritesController;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FavouritesActivity extends AppCompatActivity implements TvDataListener {

    private ActivityFavouritesBinding binding;
    private TvShowFavouritesController tvFavouriteController;
    private TvMostPopularAdapter tvMostPopularAdapter;
    private List<TvMostPopular> tvMostPopularList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavouritesBinding.inflate(getLayoutInflater());
        View viewFavourites = binding.getRoot();
        setContentView(viewFavourites);

        setSupportActionBar(binding.toolbarFavourites);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Favoritos");


        showData();


    }

    public void showData() {
        binding.recyclerFavourites.hasFixedSize();
        tvFavouriteController = new ViewModelProvider(this).get(TvShowFavouritesController.class);
        tvMostPopularAdapter = new TvMostPopularAdapter(tvMostPopularList, this);
        binding.recyclerFavourites.setAdapter(tvMostPopularAdapter);
        fetchFavourites();
    }

    private void fetchFavourites() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(tvFavouriteController.getFavourites()
                .subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(tvShows -> {
                   if(tvShows != null){
                           tvMostPopularList.addAll(tvShows);
                           tvMostPopularAdapter.notifyDataSetChanged();;
                   }
                }));
    }

    @Override
    public void tvShowClicked(TvMostPopular tvMostPopular) {

    }
}
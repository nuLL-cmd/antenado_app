package com.automatodev.antenado.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.automatodev.antenado.adapters.TvMostPopularAdapter;
import com.automatodev.antenado.databinding.ActivityFavouritesBinding;
import com.automatodev.antenado.listener.TvDataListener;
import com.automatodev.antenado.models.TvMostPopular;
import com.automatodev.antenado.utilities.RefreshRules;
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
        tvMostPopularAdapter = new TvMostPopularAdapter("favList", tvMostPopularList, this);
        binding.recyclerFavourites.setAdapter(tvMostPopularAdapter);
        fetchFavourites();
    }

    private void fetchFavourites() {
        tvMostPopularList.clear();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(tvFavouriteController.getFavourites()
                .subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(tvShows -> {
                    if (tvShows != null) {
                        tvMostPopularList.addAll(tvShows);
                        tvMostPopularAdapter.notifyDataSetChanged();
                        compositeDisposable.dispose();
                    }
                }));
    }

    @Override
    public void tvShowClicked(TvMostPopular tvMostPopular) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("tvShow",tvMostPopular);
        startActivity(intent);

    }

    @Override
    public void tvShowDelete(TvMostPopular tvMOstPopular, int position) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(tvFavouriteController.deleteFavourite(tvMOstPopular).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(()->{
            Toast.makeText(this, "Teste position: " + position, Toast.LENGTH_SHORT).show();
            tvMostPopularList.remove(position);
            tvMostPopularAdapter.notifyItemRemoved(position);
            tvMostPopularAdapter.notifyItemRangeChanged(position, tvMostPopularAdapter.getItemCount());

        }));
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (RefreshRules.IS_FAVOURITES_UPDATE_LIST){
            fetchFavourites();
            RefreshRules.IS_FAVOURITES_UPDATE_LIST = false;
        }

    }

}
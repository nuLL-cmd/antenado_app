package com.automatodev.antenado.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.automatodev.antenado.R;
import com.automatodev.antenado.adapters.EpisodesAdapter;
import com.automatodev.antenado.databinding.ActivityDetailsBinding;
import com.automatodev.antenado.databinding.LayoutBottomEpisodesBinding;
import com.automatodev.antenado.models.EpisodesEntity;
import com.automatodev.antenado.models.TvMostPopular;
import com.automatodev.antenado.utilities.RefreshRules;
import com.automatodev.antenado.viewModel.TvDetailsController;
import com.automatodev.antenado.viewModel.TvShowFavouritesController;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DetailsActivity extends AppCompatActivity {

    private TvDetailsController tvDetailsController;
    private TvShowFavouritesController tvFavouriteController;
    private List<EpisodesEntity> episodesEntities = new ArrayList<>();
    private String url;
    private TvMostPopular tvMostPopular = new TvMostPopular();
    private Boolean isFavourite = false;
    View viewDetails;

    private ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        viewDetails = binding.getRoot();
        setContentView(viewDetails);
        tvDetailsController = new ViewModelProvider(this).get(TvDetailsController.class);
        tvFavouriteController = new ViewModelProvider(this).get(TvShowFavouritesController.class);

        getData();

    }

    public void fetchDetails(String id) {
        binding.setIsLoading(true);

        tvDetailsController.getDetailsTvShow(id).observe(this, tvDetailsDataSheet -> {
            if (tvDetailsDataSheet != null) {
                episodesEntities.addAll(tvDetailsDataSheet.getTvShow().getEpisodes());
                url = tvDetailsDataSheet.getTvShow().getUrl();
                binding.setIsLoading(false);
                binding.setTvDetails(tvDetailsDataSheet.getTvShow());

                binding.fabListEpisodesDetails.setOnClickListener(v ->{
                    if (isFavourite){
                            new CompositeDisposable().add(tvFavouriteController.deleteFavourite(tvMostPopular).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(() ->{
                                binding.fabListEpisodesDetails.setImageResource(R.drawable.ic_heart);
                                Snackbar.make(v, "Favorito removido!", Snackbar.LENGTH_LONG).show();
                                RefreshRules.IS_FAVOURITES_UPDATE_LIST = true;
                                isFavourite = false;
                            }));
                    }else{
                        new CompositeDisposable().add(tvFavouriteController.addFavourites(tvMostPopular).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(() ->{
                            binding.fabListEpisodesDetails.setImageResource(R.drawable.ic_check);
                            Snackbar.make(v, "Favorito adicionado com sucesso!", Snackbar.LENGTH_LONG).show();
                            RefreshRules.IS_FAVOURITES_UPDATE_LIST = true;
                            isFavourite = true;
                        }));
                    }
                });
            }
        });
    }

    public void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tvMostPopular = (TvMostPopular) bundle.getSerializable("tvShow");
            binding.setUrl(tvMostPopular.getUrlImage());
            checkFavourite(tvMostPopular.getId());
            fetchDetails(String.valueOf(tvMostPopular.getId()));


        }
    }


    public void actDetailsMain(View view) {
        NavUtils.navigateUpFromSameTask(this);
    }


    public void showListEpisodes(View view) {
        BottomSheetDialog dialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutBottomEpisodesBinding bottomBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_bottom_episodes, binding.relativeDataDetails, false);
        bottomBinding.lblTitleLayoutButtomEpisodes.setText("Episodios | " + tvMostPopular.getName());
        bottomBinding.recyclerItemBottomEpisodes.hasFixedSize();
        bottomBinding.recyclerItemBottomEpisodes.setLayoutManager(new LinearLayoutManager(this));
        EpisodesAdapter episodesAdapter = new EpisodesAdapter(episodesEntities);
        bottomBinding.recyclerItemBottomEpisodes.setAdapter(episodesAdapter);
        dialog.setContentView(bottomBinding.getRoot());
        dialog.show();

    }


    public void goSite(View view) {
        if (url != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);

        }
    }

    public void checkFavourite(int id){
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(tvFavouriteController.getFavouriteSingle(id).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(tvMostPopular1 -> {
                if (tvMostPopular1 != null){
                    isFavourite = true;
                    binding.fabListEpisodesDetails.setImageResource(R.drawable.ic_check);
                    compositeDisposable.dispose();
                }
        }));
    }
}
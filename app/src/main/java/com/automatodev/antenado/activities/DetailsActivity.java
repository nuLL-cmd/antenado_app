package com.automatodev.antenado.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProvider;

import com.automatodev.antenado.databinding.ActivityDetailsBinding;
import com.automatodev.antenado.viewModel.TvDetailsController;
import com.google.android.material.snackbar.Snackbar;

public class DetailsActivity extends AppCompatActivity {

    private TvDetailsController tvDetailsController;

    private ActivityDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivityDetailsBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);
        setSupportActionBar(binding.toolbarDetails);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.toolbarDetails.setNavigationOnClickListener(v1 -> NavUtils.navigateUpFromSameTask(DetailsActivity.this));
        binding.fabListEpisodesDetails.setOnClickListener(view -> Snackbar.make(view, "Id do item: ", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        getData();

    }

    public void fetchDetails(String id){
        binding.setIsLoading(true);
        tvDetailsController = new ViewModelProvider(this).get(TvDetailsController.class);
        tvDetailsController.getDetailsTvShow(id).observe(this, tvDetailsDataSheet -> {
            if (tvDetailsDataSheet != null){
                binding.setIsLoading(false);
                binding.setTvDetails(tvDetailsDataSheet.getTvShow());
            }
        });
    }

    public void getData(){
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            String name = bundle.getString("name");
            String id = String.valueOf(bundle.getInt("id", -1));
            String picuture = bundle.getString("picture");
            binding.setUrl(picuture);
            binding.toolbarDetails.setTitle(name);
            fetchDetails(id);


        }
    }



}
package com.automatodev.antenado.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.automatodev.antenado.R;
import com.automatodev.antenado.adapters.EpisodesAdapter;
import com.automatodev.antenado.databinding.ActivityDetailsBinding;
import com.automatodev.antenado.models.EpisodesEntity;
import com.automatodev.antenado.viewModel.TvDetailsController;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private TvDetailsController tvDetailsController;
    private List<EpisodesEntity> episodesEntities = new ArrayList<>();
    View viewDetails;

    private ActivityDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivityDetailsBinding.inflate(getLayoutInflater());
        viewDetails = binding.getRoot();
        setContentView(viewDetails);

        binding.fabListEpisodesDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Favorito adicionado com sucesso!", Snackbar.LENGTH_LONG).show();
            }
        });

        getData();

    }

    public void fetchDetails(String id){
        binding.setIsLoading(true);
        tvDetailsController = new ViewModelProvider(this).get(TvDetailsController.class);
        tvDetailsController.getDetailsTvShow(id).observe(this, tvDetailsDataSheet -> {
            if (tvDetailsDataSheet != null){
                episodesEntities.addAll(tvDetailsDataSheet.getTvShow().getEpisodes());
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
            fetchDetails(id);


        }
    }


    public void actDetailsMain(View view){
        NavUtils.navigateUpFromSameTask(this);
    }


    public void showListEpisodes(View view){
        BottomSheetDialog dialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        //LayoutInflater inflater = LayoutInflater.from(this);
        //LayoutBottomEpisodesBinding bottomEpisodesBinding = DataBindingUtil.inflate(inflater, R.layout.layout_bottom_episodes, binding.relativeDataDetails,false);
        View view1 = getLayoutInflater().inflate(R.layout.layout_bottom_episodes, null);
        RecyclerView recyclerView = view1.findViewById(R.id.recyclerItem_bottomEpisodes);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        EpisodesAdapter episodesAdapter = new EpisodesAdapter(episodesEntities);
        recyclerView.setAdapter(episodesAdapter);
        dialog.setContentView(view1);
        dialog.show();

    }
}
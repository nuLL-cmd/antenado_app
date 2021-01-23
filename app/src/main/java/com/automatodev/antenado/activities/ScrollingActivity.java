package com.automatodev.antenado.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.automatodev.antenado.R;
import com.automatodev.antenado.adapters.TvMostPopularAdapter;
import com.automatodev.antenado.databinding.ActivityScrollingBinding;
import com.automatodev.antenado.listener.TvDataListener;
import com.automatodev.antenado.models.TvMostPopular;
import com.automatodev.antenado.viewModel.TvMostController;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ScrollingActivity extends AppCompatActivity implements TvDataListener{
    private List<TvMostPopular> tvMostPopulars = new ArrayList<>();
    private TvMostPopularAdapter adapter;
    private TvMostController tvControler;
    private ActivityScrollingBinding binding;
    private int currentPage = 1;
    private int totalAvaliablePages = 1;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScrollingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout toolBarLayout =  findViewById(R.id.toolbar_layout);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        showData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void showData() {
        binding.includeContentScolling.recyclerItemMain.hasFixedSize();
        tvControler = new ViewModelProvider(this).get(TvMostController.class);
        adapter = new TvMostPopularAdapter(tvMostPopulars, this);

        binding.includeContentScolling.recyclerItemMain.setAdapter(adapter);
        binding.includeContentScolling.recyclerItemMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!binding.includeContentScolling.recyclerItemMain.canScrollVertically(1)){
                    if (currentPage <= totalAvaliablePages){
                        currentPage += 1;
                        getAllTvMostPopular();
                    }
                }
            }
        });
        getAllTvMostPopular();
    }

    private void getAllTvMostPopular() {
        toggleLoading();
        tvControler.getAllTvMostPopular(currentPage).observe(this, tvDataSheet -> {
            toggleLoading();
            if (tvDataSheet != null) {
                totalAvaliablePages = tvDataSheet.getTotalPages();
                if (tvDataSheet.getTvMostPopulars() != null) {
                    tvMostPopulars.addAll(tvDataSheet.getTvMostPopulars());
                    int oldCount = tvMostPopulars.size();
                    tvMostPopulars.addAll(tvDataSheet.getTvMostPopulars());
                    adapter.notifyItemRangeChanged(oldCount, tvMostPopulars.size());
                }
            }
        });
    }

    private void toggleLoading(){
        if (currentPage == 1){
            if (binding.includeContentScolling.getIsLoading() != null && binding.includeContentScolling.getIsLoading())
                binding.includeContentScolling.setIsLoading(false);
            else
                binding.includeContentScolling.setIsLoading(true);
        }else{
            if (binding.includeContentScolling.getIsLoadingMore() != null && binding.includeContentScolling.getIsLoadingMore())
                binding.includeContentScolling.setIsLoadingMore(false);
            else
                binding.includeContentScolling.setIsLoadingMore(true);

        }
    }

    @Override
    public void tvShowClicked(TvMostPopular tvMostPopular) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("id", tvMostPopular.getId());
        intent.putExtra("name", tvMostPopular.getName());
        intent.putExtra("picture",tvMostPopular.getUrlImage());
        startActivity(intent);
    }
}
package com.automatodev.antenado.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.automatodev.antenado.adapters.TvMostPopularAdapter;
import com.automatodev.antenado.databinding.ActivitySearchBinding;
import com.automatodev.antenado.listener.TvDataListener;
import com.automatodev.antenado.models.TvMostPopular;
import com.automatodev.antenado.viewModel.TvMostController;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity implements TvDataListener {

    private ActivitySearchBinding binding;
    private TvMostPopularAdapter adapter;
    private TvMostController controller;
    private List<TvMostPopular> tvlist = new ArrayList<>();
    private int currentPage = 1;
    private int totalPages = 1;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        showData();

    }

    private void showData(){
        binding.recyclerItemSearch.hasFixedSize();
        controller = new ViewModelProvider(this).get(TvMostController.class);
        adapter = new TvMostPopularAdapter("mainList",tvlist, this);
        binding.recyclerItemSearch.setAdapter(adapter);

        binding.txtSearchSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (timer != null)
                        timer.cancel();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().trim().isEmpty()){
                    timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            currentPage = 1;
                                            totalPages = 1;
                                            fetchSearchTvShows(s.toString());
                                        }
                                    });
                                }
                            },800);
                }else{
                    tvlist.clear();
                    adapter.notifyDataSetChanged();
                }

            }
        });

        binding.recyclerItemSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!binding.recyclerItemSearch.canScrollVertically(1)){
                    if (!binding.txtSearchSearch.getText().toString().isEmpty()){
                        if (currentPage < totalPages){
                            currentPage += 1;
                            fetchSearchTvShows(binding.txtSearchSearch.getText().toString());
                        }
                    }
                }
            }
        });
        binding.txtSearchSearch.requestFocus();
    }

    public void fetchSearchTvShows(String query){
        toggleLoading();
        controller.searchTvShow(query, currentPage).observe(this, tvDataSheet -> {
            toggleLoading();
            if (tvDataSheet != null){
                totalPages = tvDataSheet.getTotalPages();
                if (tvDataSheet.getTvMostPopulars() != null){
                    int oldCount = tvlist.size();
                    tvlist.addAll(tvDataSheet.getTvMostPopulars());
                    adapter.notifyItemRangeInserted(oldCount,tvlist.size());
                }
            }
        });
    }

    private void toggleLoading(){
        if (currentPage == 1){
            if (binding.getIsLoading() != null && binding.getIsLoading())
                binding.setIsLoading(false);
            else
                binding.setIsLoading(true);
        }else{
            if (binding.getIsLoadingMore() != null && binding.getIsLoadingMore())
                binding.setIsLoadingMore(false);
            else
                binding.setIsLoadingMore(true);

        }
    }

    @Override
    public void tvShowClicked(TvMostPopular tvMostPopular) {

    }

    @Override
    public void tvShowDelete(TvMostPopular tvMOstPopular, int position) {

    }

    public void actSearchMain(View view){
        onBackPressed();
    }
}